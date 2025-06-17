package de.hitec.nhplus.controller;

import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.NursesDao;
import de.hitec.nhplus.model.Nurses;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class AllNursesController {

    @FXML
    private TableView<Nurses> tableView;

    @FXML
    private TableColumn<Nurses, Integer> columnId;

    @FXML
    private TableColumn<Nurses, String> columnFirstName;

    @FXML
    private TableColumn<Nurses, String> columnLastName;

    @FXML
    private TableColumn<Nurses, String> columnPhoneNumber;

    private final ObservableList<Nurses> nurses = FXCollections.observableArrayList();

    public void initialize() {
        this.columnId.setCellValueFactory(new PropertyValueFactory<>("nid"));
        this.columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.columnLastName.setCellValueFactory(new PropertyValueFactory<>("surname"));
        this.columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone")); // Replace with phone number if available

        this.tableView.setItems(this.nurses);
        this.loadNurses();
    }

    private void loadNurses() {
        NursesDao dao = DaoFactory.getDaoFactory().createNursesDAO();
        try {
            this.nurses.addAll(dao.readAll());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}