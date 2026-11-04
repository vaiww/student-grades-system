package com.example.database;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private ChoiceBox<String> roleChoiceBox;
    @FXML private Label errorLabel;

    @FXML
    public void initialize() {
        roleChoiceBox.getItems().addAll(
                "Администратор",
                "Преподаватель",
                "Студент"
        );
    }

    @FXML
    private void handleRegister(ActionEvent event) {

        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String role = roleChoiceBox.getValue();

        if (username.isEmpty() || password.isEmpty()
                || confirmPassword.isEmpty() || role == null) {
            errorLabel.setText("Заполните все поля!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            errorLabel.setText("Пароли не совпадают!");
            return;
        }

        if (password.length() < 6) {
            errorLabel.setText("Пароль минимум 6 символов!");
            return;
        }

        if (userExists(username)) {
            errorLabel.setText("Пользователь уже существует!");
            return;
        }

        if (saveUser(username, password, role)) {
            errorLabel.setStyle("-fx-text-fill: green;");
            errorLabel.setText("Регистрация успешна!");
            clearFields();
        } else {
            errorLabel.setText("Ошибка регистрации!");
        }
    }

    private boolean userExists(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, username);
            return ps.executeQuery().next();

        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    private boolean saveUser(String username, String password, String role) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        roleChoiceBox.setValue(null);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Вход");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
