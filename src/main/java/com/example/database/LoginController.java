package com.example.database;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;

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
    private ChoiceBox<String> roleChoiceBox;

    @FXML
    private Label errorLabel;

    @FXML
    public void initialize() {
        roleChoiceBox.setItems(FXCollections.observableArrayList(
                "Администратор",
                "Преподаватель",
                "Студент"
        ));
    }

    // ВХОД В СИСТЕМУ
    @FXML
    private void handleLogin(ActionEvent event) {

        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String role = roleChoiceBox.getValue();

        if (username.isEmpty() || password.isEmpty() || role == null) {
            errorLabel.setText("Пожалуйста, заполните все поля!");
            return;
        }

        if (checkLogin(username, password, role)) {
            openMainWindow(event, username, role);
        } else {
            errorLabel.setText("Неверный логин, пароль или роль!");
        }
    }


    private boolean checkLogin(String username, String password, String role) {

        String sql = """
                SELECT id
                FROM users
                WHERE username = ? AND password = ? AND role = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Ошибка подключения к базе данных!");
            return false;
        }
    }

    //ПЕРЕХОД К РЕГИСТРАЦИИ
    @FXML
    private void handleRegister(ActionEvent event) {
        try {
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("Register.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Регистрация");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Ошибка при открытии регистрации!");
        }
    }

    //ВОССТАНОВЛЕНИЕ ПАРОЛЯ (ЗАГЛУШКА)
    @FXML
    private void handleForgotPassword(ActionEvent event) {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Восстановление пароля");
        dialog.setHeaderText("Введите email для восстановления:");
        dialog.setContentText("Email:");

        dialog.showAndWait().ifPresent(email -> {
            errorLabel.setText(
                    "Если email существует, инструкция отправлена."
            );
            System.out.println("Запрос восстановления для: " + email);
        });
    }


    private void openMainWindow(ActionEvent event, String username, String role) {
        try {

            UserSession.startSession(username, role);
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("main-view.fxml"));
            Scene scene = new Scene(loader.load());

            MainController controller = loader.getController();
            controller.setUser(username, role);

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Система управления — " + role + ": " + username);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Ошибка при открытии главного окна!");
        }
    }

}
