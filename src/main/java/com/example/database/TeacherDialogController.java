package com.example.database;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Rectangle2D;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class TeacherDialogController {

    @FXML private CheckBox isCuratorCheckBox;
    @FXML private TextField surnameField;
    @FXML private TextField nameField;
    @FXML private TextField patronymicField;
    @FXML private TextField phoneField;
    @FXML private TextField positionField;
    @FXML private DatePicker birthdayPicker;
    @FXML private TextField roomField;
    @FXML private ComboBox<Group> curatorGroupCombo;

    @FXML private ListView<Subject> subjectsListView;
    @FXML private ListView<Group> groupsListView;

    private Teacher teacher;
    private TeacherDAO teacherDAO;
    private Stage dialogStage;
    private boolean saved = false;
    private List<Group> allGroups;
    private List<Subject> allSubjects;

    public Stage getDialogStage() { return dialogStage; }

    public Teacher getResult() { return saved ? teacher : null; }

    public void setTeacherDAO(TeacherDAO dao) {
        this.teacherDAO = dao;
        loadGroups();
        loadSubjects();
    }

    public void setDialogStage(Stage stage) { this.dialogStage = stage; }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher != null ? teacher : new Teacher();


        surnameField.setText(this.teacher.getSurname());
        nameField.setText(this.teacher.getName());
        patronymicField.setText(this.teacher.getPatronymic());
        phoneField.setText(this.teacher.getPhone());
        positionField.setText(this.teacher.getPosition());
        birthdayPicker.setValue(this.teacher.getBirthday());
        roomField.setText(this.teacher.getRoom());

        isCuratorCheckBox.setSelected(this.teacher.isCurator());

        if (allGroups != null && !allGroups.isEmpty()) {
            // Выбираем кураторскую группу по curatorGroupId
            if (this.teacher.getCuratorGroupId() != null) {
                allGroups.stream()
                        .filter(g -> g.getId() == this.teacher.getCuratorGroupId())
                        .findFirst()
                        .ifPresent(curatorGroupCombo.getSelectionModel()::select);
            }

            // Выбираем группы преподавателя
            groupsListView.getSelectionModel().clearSelection();
            for (Group g : allGroups) {
                if (this.teacher.getSelectedGroupIds().contains(g.getId())) {
                    groupsListView.getSelectionModel().select(g);
                }
            }
        }

        if (allSubjects != null && !allSubjects.isEmpty()) {
            subjectsListView.getSelectionModel().clearSelection();
            for (Subject s : allSubjects) {
                if (this.teacher.getSelectedSubjectIds().contains(s.getId())) {
                    subjectsListView.getSelectionModel().select(s);
                }
            }
        }

        // Включаем/отключаем ComboBox куратора
        curatorGroupCombo.setDisable(!isCuratorCheckBox.isSelected());
    }

    private void loadSubjects() {
        allSubjects = teacherDAO.getAllSubjects();
        subjectsListView.setItems(FXCollections.observableArrayList(allSubjects));
        subjectsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void loadGroups() {
        allGroups = teacherDAO.getAllGroups();
        groupsListView.setItems(FXCollections.observableArrayList(allGroups));
        groupsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        curatorGroupCombo.setItems(FXCollections.observableArrayList(allGroups));
        curatorGroupCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(Group g) { return g != null ? g.getName() : ""; }
            @Override
            public Group fromString(String s) { return null; }
        });
    }

    @FXML
    private void onSave() {
        teacher.setSurname(surnameField.getText());
        teacher.setName(nameField.getText());
        teacher.setPatronymic(patronymicField.getText());
        teacher.setPhone(phoneField.getText());
        teacher.setPosition(positionField.getText());
        teacher.setBirthday(birthdayPicker.getValue());
        teacher.setRoom(roomField.getText());
        teacher.setCurator(isCuratorCheckBox.isSelected());

        Group selectedCurator = curatorGroupCombo.getSelectionModel().getSelectedItem();
        teacher.setCuratorGroupId(isCuratorCheckBox.isSelected() && selectedCurator != null
                ? selectedCurator.getId()
                : null);

        teacher.setSelectedSubjectIds(subjectsListView.getSelectionModel().getSelectedItems()
                .stream().map(Subject::getId).collect(Collectors.toList()));

        teacher.setSelectedGroupIds(groupsListView.getSelectionModel().getSelectedItems()
                .stream().map(Group::getId).collect(Collectors.toList()));

        try {
            if (teacher.getIdt() == 0) teacherDAO.addTeacher(teacher);
            else teacherDAO.updateTeacher(teacher);
            saved = true;
            dialogStage.close();
        } catch (SQLException e) {
            showError("Ошибка сохранения преподавателя", e);
        }
    }

    @FXML
    private void onCancel() {
        dialogStage.close();
    }

    @FXML
    public void initialize() {
        subjectsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        groupsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        isCuratorCheckBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            curatorGroupCombo.setDisable(!isNowSelected);
        });
        curatorGroupCombo.setDisable(!isCuratorCheckBox.isSelected());
    }

    public static void openDialog(Stage owner, TeacherDAO dao, Teacher teacher) {
        try {
            FXMLLoader loader = new FXMLLoader(TeacherDialogController.class.getResource("Teacher-dialog.fxml"));
            Parent root = loader.load();
            TeacherDialogController controller = loader.getController();
            controller.setTeacherDAO(dao);
            controller.setTeacher(teacher);

            Stage dialogStage = new Stage();
            controller.setDialogStage(dialogStage);

            dialogStage.initOwner(owner);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle(teacher == null ? "Добавить преподавателя" : "Редактировать преподавателя");

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            dialogStage.setWidth(Math.min(800, screenBounds.getWidth() * 0.9));
            dialogStage.setHeight(Math.min(600, screenBounds.getHeight() * 0.9));
            dialogStage.setMaxWidth(screenBounds.getWidth() * 0.95);
            dialogStage.setMaxHeight(screenBounds.getHeight() * 0.95);

            dialogStage.showAndWait();

        } catch (IOException e) {
            showErrorStatic("Ошибка при открытии диалога", e);
        }
    }

    private void showError(String message, Exception e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(message);
        alert.setContentText(e.getMessage());
        alert.initOwner(dialogStage);
        alert.showAndWait();
    }

    private static void showErrorStatic(String message, Exception e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(message);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}
