package de.hitec.nhplus.controller;

import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.NursesDao;
import de.hitec.nhplus.model.Nurses;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class AllNursesController {

    @FXML
    private TableView<Nurses> tableView;

    @FXML
    private TableColumn<Nurses, Long> columnId;

    @FXML
    private TableColumn<Nurses, String> columnFirstName;

    @FXML
    private TableColumn<Nurses, String> columnLastName;

    @FXML
    private TableColumn<Nurses, String> columnPhoneNumber;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField phoneField;

    private final ObservableList<Nurses> nurses = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        this.columnId.setCellValueFactory(new PropertyValueFactory<>("nid"));
        this.columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.columnLastName.setCellValueFactory(new PropertyValueFactory<>("surname"));
        this.columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone"));

        this.tableView.setItems(this.nurses);
        this.loadNurses();
    }

    private void loadNurses() {
        NursesDao dao = DaoFactory.getDaoFactory().createNursesDAO();
        try {
            this.nurses.clear();
            this.nurses.addAll(dao.readAll());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        phoneField.clear();
    }

    @FXML
    private void onAddClicked() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String phone = phoneField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty()) {
            showAlert("Fehler", "Bitte alle Felder ausfüllen.");
            return;
        }

        Nurses newNurse = new Nurses(lastName, firstName, 0, phone);  // nid = 0, wird vom DB gesetzt

        NursesDao dao = DaoFactory.getDaoFactory().createNursesDAO();
        try {
            long newId = dao.createAndReturnId(newNurse);
            newNurse.setNid(newId);
            nurses.add(newNurse);
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Fehler", "Konnte Pflegekraft nicht hinzufügen.");
        }
    }

    @FXML
    private void onEditClicked() {
        Nurses selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Hinweis", "Bitte zuerst eine Pflegekraft auswählen.");
            return;
        }

        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String phone = phoneField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty()) {
            showAlert("Fehler", "Bitte alle Felder ausfüllen.");
            return;
        }

        selected.setFirstName(firstName);
        selected.setSurname(lastName);
        selected.setPhone(phone);

        NursesDao dao = DaoFactory.getDaoFactory().createNursesDAO();
        try {
            dao.update(selected);
            tableView.refresh();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Fehler", "Konnte Pflegekraft nicht bearbeiten.");
        }
    }

    @FXML
    private void onDeleteClicked() {
        Nurses selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Hinweis", "Bitte zuerst eine Pflegekraft auswählen.");
            return;
        }

        NursesDao dao = DaoFactory.getDaoFactory().createNursesDAO();
        try {
            dao.deleteById(selected.getNid());
            nurses.remove(selected);
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Fehler", "Konnte Pflegekraft nicht löschen.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
