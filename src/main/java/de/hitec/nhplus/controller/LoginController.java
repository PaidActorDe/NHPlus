package de.hitec.nhplus.controller;

import de.hitec.nhplus.datastorage.ConnectionBuilder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private Stage loginStage;
    private Runnable onLoginSuccess;

    public void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }

    public void setOnLoginSuccess(Runnable onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (authenticate(username, password)) {
            if (onLoginSuccess != null) {
                onLoginSuccess.run();
            }
            loginStage.close();
        } else {
            errorLabel.setText("Invalid username or password.");
        }
    }

    private boolean authenticate(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        Connection connection = ConnectionBuilder.getConnection(); // Nicht automatisch schließen!
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Login erfolgreich für Benutzer: " + username);
                return true;
            } else {
                System.out.println("Login fehlgeschlagen für Benutzer: " + username);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}