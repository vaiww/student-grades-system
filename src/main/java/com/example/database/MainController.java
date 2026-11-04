package com.example.database;

import javafx.collections.ListChangeListener;
import javafx.scene.control.TextArea;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.*;
import com.example.database.Group;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.scene.image.ImageView;


import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class MainController {


    @FXML private javafx.scene.layout.BorderPane mainBorderPane;


    @FXML private TabPane tabPane;

    @FXML private TextField collegeNameField;
    @FXML private TextField collegeAddressField;
    @FXML private TextField collegePhoneField;

    @FXML private TextField studentSearchField;
    @FXML private TableView<Student> studentsTable;
    @FXML private TableColumn<Student, Integer> stuIdCol;
    @FXML private TableColumn<Student, String> stuLastNameCol;
    @FXML private TableColumn<Student, String> stuFirstNameCol;
    @FXML private TableColumn<Student, String> stuPatronymicCol;
    @FXML private TableColumn<Student, String> stuPhoneCol;
    @FXML private TableColumn<Student, LocalDate> stuBirthdayCol;
    @FXML private TableColumn<Student, Integer> stuAgeCol;
    @FXML private TableColumn<Student, String> stuGroupCol;
    @FXML private TableColumn<Student, String> stuSpecCol;
    @FXML
    private TableColumn<Student, Integer> stuCourseCol;

    @FXML private TextField groupSearchField;
    @FXML private TableView<Group> groupsTable;
    @FXML private TableColumn<Group, Integer> groupIdCol;
    @FXML private TableColumn<Group, String> groupNameCol;
    @FXML private TableColumn<Group, String> groupCuratorCol;

    @FXML private TextField subjectSearchField;
    @FXML private TableView<Subject> subjectTable;
    @FXML private TableColumn<Subject, Integer> subjectIdCol;
    @FXML private TableColumn<Subject, String> subjectNameCol;

    @FXML private TableView<Teacher> teachersTable;
    @FXML private TableColumn<Teacher, Integer> teacherIdCol;
    @FXML private TableColumn<Teacher, String> teacherLastNameCol;
    @FXML private TableColumn<Teacher, String> teacherFirstNameCol;
    @FXML private TableColumn<Teacher, String> teacherPatronymicCol;
    @FXML private TableColumn<Teacher, String> teacherPhoneCol;
    @FXML private TableColumn<Teacher, String> teacherPositionCol;
    @FXML private TableColumn<Teacher, LocalDate> teacherBirthdayCol;
    @FXML private TableColumn<Teacher, String> teacherRoomCol;
    @FXML private TableColumn<Teacher, String> teacherIsCuratorCol;
    @FXML private TableColumn<Teacher, String> teacherGroupCol;
    @FXML private TableColumn<Teacher, String> teacherSubjectCol;
    @FXML private TextField teacherSearchField;
    @FXML private TableView<Grade> gradesTable;

    @FXML private TableColumn<Grade, Integer> gradeIdCol;
    @FXML private TableColumn<Grade, String> gradeStudentCol;
    @FXML private TableColumn<Grade, String> gradeTeacherCol;
    @FXML private TableColumn<Grade, String> gradeSubjectCol;
    @FXML private TableColumn<Grade, String> gradeGroupCol;
    @FXML private TableColumn<Grade, String> gradeDescriptionCol;
    @FXML private TableColumn<Grade, Integer> gradeValueCol;
    @FXML private TableColumn<Grade, Integer> gradeCourseCol;
    @FXML private TableColumn<Grade, Integer> gradeSemesterCol;
    @FXML private TableColumn<Grade, LocalDate> gradeDateCol;


    @FXML private TextField gradeSearchField;


    @FXML private TextField performanceSearchField;
    @FXML private TableView<PerformanceSheet> performanceTable;
    @FXML private TableColumn<PerformanceSheet, Integer> psIdCol;
    @FXML private TableColumn<PerformanceSheet, String> psStudentCol;
    @FXML private TableColumn<PerformanceSheet, Integer> psSemesterCol;
    @FXML private TableColumn<PerformanceSheet, Double> psAverageCol;


    @FXML private TableColumn<PerformanceSheet, String> psGroupCol;
    @FXML private TableColumn<PerformanceSheet, String> psSpecialityCol;
    @FXML private TableColumn<PerformanceSheet, Integer> psCourseCol;



    @FXML private TextField scheduleSearchField;
    @FXML private TableView<Schedule> scheduleTable;
    @FXML private TableColumn<Schedule, Integer> schedIdCol;
    @FXML private TableColumn<Schedule, String> schedGroupCol;
    @FXML private TableColumn<Schedule, String> schedTeacherCol;
    @FXML private TableColumn<Schedule, String> schedSubjectCol;
    @FXML private TableColumn<Schedule, LocalDate> schedDateCol;
    @FXML private TableColumn<Schedule, String> schedTimeCol;
    @FXML private TableColumn<Schedule, String> schedRoomCol;

    @FXML private Label statusLabel;
    @FXML private Label userLabel;

    @FXML
    private TextArea collegeInfoArea;

    private CollegeDAO collegeDao;
    private College college;

    @FXML
    private ImageView collegeImageView;

    private final List<Image> collegeImages = new ArrayList<>();
    private int currentImageIndex = 0;

    @FXML
    private TableColumn<Subject, String> subjectDescCol;

    private static final String DARK =
            "/com/example/database/styles/dark-theme.css";
    private static final String LIGHT =
            "/com/example/database/styles/light-theme.css";


    private Stage primaryStage;  // основной Stage
    private TeacherDAO teacherDAO; // ссылка на DAO

    @FXML
    private TableColumn<Group, String> groupSpecCol;

    @FXML
    private Tab studentsTab;

    @FXML
    private Tab teachersTab;

    @FXML
    private Tab groupsTab;

    @FXML
    private Tab subjectsTab;


    @FXML
    private StackPane imageContainer;


    private int collegeId;


    // Data lists
    private final ObservableList<Student> students     = FXCollections.observableArrayList();
    private final ObservableList<Teacher> teachers     = FXCollections.observableArrayList();
    private final ObservableList<Group> groups         = FXCollections.observableArrayList();
    private final ObservableList<Subject> subjects     = FXCollections.observableArrayList();
    private final ObservableList<Grade> grades         = FXCollections.observableArrayList();
    private final ObservableList<PerformanceSheet> performances = FXCollections.observableArrayList();
    private final ObservableList<Schedule> schedules   = FXCollections.observableArrayList();

    private final Map<Integer, Group> groupMap = new HashMap<>();
    private final Map<Integer, Subject> subjectMap = new HashMap<>();

    // DB connection & DAOs
    private Connection dbConn;
    private StudentDAO studentDao;
    private TeacherDAO teacherDao;
    private GroupDAO groupDao;
    private SubjectDAO subjectDao;
    private GradeDAO gradeDao;
    private PerformanceSheetDAO performanceDao;
    private ScheduleDAO scheduleDao;
    private CuratorDAO curatorDao;
    private SpecialityDAO specialityDao;

    private void restrictTable(TableView<?> table) {
        table.setEditable(false);
        table.setSelectionModel(null); // нельзя выделять
    }


    private void makeReadOnly(TableView<?> table) {
        table.setEditable(false);
    }
    private void lockColumns(TableView<?> table) {
        table.getColumns().addListener((ListChangeListener<TableColumn<?, ?>>) change -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    throw new UnsupportedOperationException("Изменение колонок запрещено");
                }
            }
        });
    }

    public void setUser(String username, String role) {

        // Запускаем сессию
        if (UserSession.getInstance() == null) {
            UserSession.startSession(username, role);
        }

        System.out.println("Logged in as: " + username + " (" + role + ")");

        // Отображение текущего пользователя
        if (userLabel != null) {
            userLabel.setText("Пользователь: " + username + " | Роль: " + role);
        }

        // Ограничение доступа по ролям
        switch (role) {

            case "Студент" -> {
                restrictTable(studentsTable);
                restrictTable(gradesTable);
                restrictTable(scheduleTable);
                restrictTable(teachersTable);
                restrictTable(subjectTable);
                restrictTable(performanceTable);
                restrictTable(groupsTable);

                lockColumns(studentsTable);
                lockColumns(gradesTable);
                lockColumns(scheduleTable);
            }

            case "Преподаватель" -> {
                studentsTable.setDisable(true);


            }

            case "Администратор" -> {

            }

        }
    }


    private Connection connection;


    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    //Вычисляет средний балл студента за указанный семестр

    public double calculateAverage(int studentId, int semester, List<Grade> allGrades) {
        if (allGrades == null) return 0;

        // Берём оценки только данного студента
        List<Grade> studentGrades = allGrades.stream()
                .filter(g -> g.getStudentId() == studentId)
                .toList();

        if (studentGrades.isEmpty()) return 0.0;

        // Фильтруем по семестру с помощью getCurrentSemester
        return studentGrades.stream()
                .filter(g -> getCurrentSemester(g.getDate()) == semester)
                .mapToInt(Grade::getValue)
                .average()
                .orElse(0.0);
    }

    //Определяет семестр по дате оценки 1 семестр: январь-июнь, 2 семестр: июль-декабрь

    private int getCurrentSemester(LocalDate date) {
        if (date == null) return 1;
        int month = date.getMonthValue();
        return (month <= 6) ? 1 : 2;
    }



    //Перегруженный вариант для вызова по студенту без даты оценки. Возвращает последний известный семестр в табеле или 1

    private int getCurrentSemester(int studentId) {
        PerformanceSheet ps = performances.stream()
                .filter(p -> p.getStudentId() == studentId)
                .max((a, b) -> Integer.compare(a.getSemester(), b.getSemester()))
                .orElse(null);
        return ps != null ? ps.getSemester() : 1;
    }

    private void updateMaps() {
        groupMap.clear();
        for (Group g : groups) groupMap.put(g.getId(), g);
        subjectMap.clear();
        for (Subject s : subjects) subjectMap.put(s.getId(), s);
    }

    private void applyThemeToDialog(Dialog<?> dialog) {
        dialog.getDialogPane().getStylesheets().clear(); // очищаем старые CSS
        if (isDarkTheme) {
            String cssFile = "/com/example/database/styles/dark-theme.css";
            URL cssUrl = getClass().getResource(cssFile);
            if (cssUrl != null) {
                dialog.getDialogPane().getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.err.println("CSS file not found: " + cssFile);
            }
        }

    }


    private boolean isDarkTheme = false;

    private void applyTheme(BorderPane pane) {
        pane.getStylesheets().clear();
        String cssFile = isDarkTheme ? "/com/example/database/styles/dark-theme.css"
                : "/com/example/database/styles/light-theme.css";
        URL url = getClass().getResource(cssFile);
        if (url != null) {
            pane.getStylesheets().add(url.toExternalForm());
        }
    }





    private void logError(String message) {
        System.err.println(message);
    }

    private Scene scene;

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    private boolean darkThemeEnabled = false;

    private void loadCollege() {
        college = collegeDao.getCollege();
        if (college != null) {
            collegeNameField.setText(college.getName());
            collegeAddressField.setText(college.getAddress());
            collegePhoneField.setText(college.getPhone());
            collegeInfoArea.setText(college.getInfo());
        }
    }

    private void loadCollegeImages() {
        collegeImages.clear(); // Очистим коллекцию перед добавлением новых изображений

        // Получаем URL для папки с изображениями
        URL folderUrl = getClass().getResource("/images/");
        if (folderUrl == null) {
            System.err.println("Папка с изображениями не найдена.");
            return;
        }


        try {

            Enumeration<URL> resources = getClass().getClassLoader().getResources("images");
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();

                String path = resource.getPath();

                // Находим файлы в папке и добавляем их
                File folder = new File(path);
                if (folder.exists() && folder.isDirectory()) {
                    File[] files = folder.listFiles((d, name) -> name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg"));
                    if (files != null) {
                        for (File f : files) {

                            Image img = new Image(f.toURI().toString());
                            collegeImages.add(img);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке ресурсов из папки: " + e.getMessage());
        }

        // Если изображения есть, устанавливаем первое изображение в ImageView
        if (!collegeImages.isEmpty()) {
            currentImageIndex = 0;
            collegeImageView.setImage(collegeImages.get(0));
        } else {
            System.err.println("Изображения не найдены в папке.");
        }





        if (!collegeImages.isEmpty()) {
            currentImageIndex = 0;
            collegeImageView.setImage(collegeImages.get(0));
        } else {

            collegeImageView.setImage(new Image("/com/example/database/images/default-image.png"));
        }
    }




    @FXML
    private void changeCollegeImage() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Добавить фото колледжа");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Изображения", "*.png", "*.jpg", "*.jpeg")
        );

        File file = fc.showOpenDialog(collegeImageView.getScene().getWindow());
        if (file == null) return;

        File targetDir = new File("images/college");
        targetDir.mkdirs();

        File dest = new File(targetDir, file.getName());
        try {
            Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            errorHandler("Ошибка загрузки изображения", e);
            return;
        }

        Image img = new Image(dest.toURI().toString());
        collegeImages.add(img);
        currentImageIndex = collegeImages.size() - 1;
        collegeImageView.setImage(img);
    }





    @FXML
    private void nextCollegeImage() {
        if (collegeImages.isEmpty()) return;

        currentImageIndex = (currentImageIndex + 1) % collegeImages.size();
        collegeImageView.setImage(collegeImages.get(currentImageIndex));
    }

    @FXML
    private void prevCollegeImage() {
        if (collegeImages.isEmpty()) return;

        currentImageIndex =
                (currentImageIndex - 1 + collegeImages.size()) % collegeImages.size();
        collegeImageView.setImage(collegeImages.get(currentImageIndex));
    }

    @FXML
    public void initialize() {
        try {
            // создаём DAO и подключаемся к базе данных
            collegeDao = new CollegeDAO(Database.getConnection());


            College c = collegeDao.getCollege();
            if (c != null) {
                collegeId = c.getId();
                collegeNameField.setText(c.getName());
                collegeAddressField.setText(c.getAddress());
                collegePhoneField.setText(c.getPhone());
                collegeInfoArea.setText(c.getInfo());
            }


            loadCollegeImages();

        } catch (SQLException e) {
            errorHandler("Ошибка подключения к базе данных.", e);
        }
            mainBorderPane.setStyle("-fx-background-color: transparent;");
            isDarkTheme = false;
            applyTheme(mainBorderPane);


        // Настройка колонок преподавателей
        teacherGroupCol.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getGroupsNames(groupMap))
        );

        teacherSubjectCol.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getSubjectsNames(subjectMap))
        );


        // Подключение к базе и инициализация DAO
        try {
            dbConn = Database.getConnection();
        } catch (SQLException e) {
            errorHandler("Ошибка подключения к базе данных.", e);
            return;
        }

        // DAO для студентов
        studentDao = new StudentDAO(dbConn);
        students.addAll(studentDao.getAll()); // добавляем студентов в уже существующий final ObservableList


        performanceDao = new PerformanceSheetDAO(dbConn, students);


        teacherDao = new TeacherDAO(dbConn);
        groupDao = new GroupDAO(dbConn);
        subjectDao = new SubjectDAO(dbConn);
        gradeDao = new GradeDAO(dbConn);
        scheduleDao = new ScheduleDAO(dbConn);
        collegeDao = new CollegeDAO(dbConn);
        curatorDao = new CuratorDAO(dbConn);
        specialityDao = new SpecialityDAO(dbConn);

        // Загрузка всех данных синхронно
        reloadDataSync();
        updateMaps();

        // Заполнение карт для удобства отображения в таблицах
        groupMap.clear();
        for (Group g : groups) groupMap.put(g.getId(), g);
        subjectMap.clear();
        for (Subject s : subjects) subjectMap.put(s.getId(), s);

        // Привязка данных к таблицам
        studentsTable.setItems(students);
        teachersTable.setItems(teachers);
        groupsTable.setItems(groups);
        subjectTable.setItems(subjects);
        gradesTable.setItems(grades);
        performanceTable.setItems(performances);
        scheduleTable.setItems(schedules);

        // Прямое связывание колонок
        bindColumns();

        // Двойной клик для редактирования
        studentsTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) editStudent(null);
        });
        teachersTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) openEditTeacherDialog();
        });
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void setTeacherDAO(TeacherDAO dao) {
        this.teacherDAO = dao;
    }

    private void reloadDataSync() {
        try {
            students.setAll(studentDao.getAll());
            teachers.setAll(teacherDao.getAll());
            groups.setAll(groupDao.getAll());
            subjects.setAll(subjectDao.getAll());
            grades.setAll(gradeDao.getAll());
            performances.setAll(performanceDao.getAll());

            // Передаем teachers и groups в ScheduleDAO
            schedules.setAll(scheduleDao.getAll(teachers, groups));
        } catch (Exception e) {
            errorHandler("Ошибка при загрузке данных.", e);
        }
    }


    private void bindColumns() {

        stuIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        stuLastNameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        stuFirstNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        stuPatronymicCol.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        stuPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        stuBirthdayCol.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        stuAgeCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getAge()).asObject());
        stuGroupCol.setCellValueFactory(new PropertyValueFactory<>("groupName"));
        stuSpecCol.setCellValueFactory(new PropertyValueFactory<>("specialityName"));
        stuCourseCol.setCellValueFactory(
                data -> new SimpleIntegerProperty(data.getValue().getCourse()).asObject()
        );


        groupIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        groupNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        groupSpecCol.setCellValueFactory(new PropertyValueFactory<>("specName"));
        groupCuratorCol.setCellValueFactory(new PropertyValueFactory<>("curator"));

        groupsTable.setItems(groups);



        subjectIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        subjectNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        subjectDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));



        teacherIdCol.setCellValueFactory(new PropertyValueFactory<>("idt"));
        teacherLastNameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        teacherFirstNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        teacherPatronymicCol.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        teacherPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        teacherPositionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        teacherBirthdayCol.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        teacherRoomCol.setCellValueFactory(new PropertyValueFactory<>("room"));
        teacherIsCuratorCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().isCurator() ? "Да" : "Нет"));
        teacherGroupCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getGroupsNames(groupMap))
        );
        teacherSubjectCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSubjectsNames(subjectMap))
        );


        gradeIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        gradeStudentCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        gradeTeacherCol.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        gradeSubjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        gradeGroupCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getGroupName() != null
                        ? cellData.getValue().getGroupName() : "")
        );
        gradeDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        gradeValueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        gradeCourseCol.setCellValueFactory(new PropertyValueFactory<>("course"));
        gradeSemesterCol.setCellValueFactory(new PropertyValueFactory<>("semester"));
        gradeDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));




        psIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));

// ФИО студента
        psStudentCol.setCellValueFactory(c -> new SimpleStringProperty(
                students.stream()
                        .filter(s -> s.getId() == c.getValue().getStudentId())
                        .map(Student::getFullName)
                        .findFirst()
                        .orElse("")
        ));

// Группа студента
        psGroupCol.setCellValueFactory(c -> new SimpleStringProperty(
                students.stream()
                        .filter(s -> s.getId() == c.getValue().getStudentId())
                        .map(Student::getGroupName)
                        .findFirst()
                        .orElse("")
        ));

// Специальность студента
        psSpecialityCol.setCellValueFactory(c -> new SimpleStringProperty(
                students.stream()
                        .filter(s -> s.getId() == c.getValue().getStudentId())
                        .map(Student::getSpecialityName)
                        .findFirst()
                        .orElse("")
        ));

// Курс студента
        psCourseCol.setCellValueFactory(c -> new SimpleIntegerProperty(
                students.stream()
                        .filter(s -> s.getId() == c.getValue().getStudentId())
                        .mapToInt(Student::getCourse)
                        .findFirst()
                        .orElse(0)
        ).asObject());

// Семестр табеля
        psSemesterCol.setCellValueFactory(new PropertyValueFactory<>("semester"));

// Средний балл
        psAverageCol.setCellValueFactory(c -> {
            double avg = c.getValue().getAverage();
            return new SimpleDoubleProperty(avg).asObject();
        });


        schedIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        schedGroupCol.setCellValueFactory(new PropertyValueFactory<>("groupName"));
        schedTeacherCol.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        schedSubjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        schedDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        schedTimeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        schedRoomCol.setCellValueFactory(new PropertyValueFactory<>("room"));
    }



    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        statusLabel.setText(message);
    }

    private void errorHandler(String message, Exception ex) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Ошибка");
        a.setContentText(message + (ex != null ? ("\n" + ex.getMessage()) : ""));
        a.showAndWait();
        if (ex != null) ex.printStackTrace();
    }

    private void warnUser(String message) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Внимание");
        a.setContentText(message);
        a.showAndWait();
    }



    @FXML
    private void onExit(ActionEvent event) {
        Stage stage = (Stage) mainBorderPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void enableDarkTheme() {
        isDarkTheme = true;
        applyTheme(mainBorderPane);
    }

    @FXML
    private void enableLightTheme() {
        isDarkTheme = false;
        applyTheme(mainBorderPane);
    }




    @FXML
    private void onAbout(ActionEvent event) {
        Alert about = new Alert(Alert.AlertType.INFORMATION);
        about.setTitle("О программе");
        about.setHeaderText("Учебная программа по учету студентов");

        TextArea textArea = new TextArea(
                "Это учебная программа для учета студентов, преподавателей и учебных дисциплин.\n" +
                        "Версия: 1.0\n" +
                        "Автор: Халдеева Виктория\n" +
                        "Контакт: victoria280507@email.com"
        );
        textArea.setEditable(false);
        textArea.setWrapText(true);


        VBox content = new VBox(textArea);
        content.setSpacing(10);
        about.getDialogPane().setContent(content);

        about.showAndWait();
    }


    @FXML
    private void onExport(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить экспорт");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try {
                exportData(file);
                showSuccess("Данные успешно экспортированы.");
            } catch (Exception e) {
                showError("Ошибка при экспорте: " + e.getMessage());
            }
        }
    }

    @FXML
    private void onImport(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл для импорта");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            try {
                importData(file);
                showSuccess("Данные успешно импортированы.");
            } catch (Exception e) {
                showError("Ошибка при импорте: " + e.getMessage());
            }
        }
    }

    private void exportData(File file) throws SQLException, IOException {
        String query = "SELECT id, name, age, email FROM students";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query);
             BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {


            writer.write("id,name,age,email");
            writer.newLine();

            while (rs.next()) {
                writer.write(rs.getInt("id") + "," +
                        rs.getString("name") + "," +
                        rs.getInt("age") + "," +
                        rs.getString("email"));
                writer.newLine();
            }
        }
    }

    private void importData(File file) throws SQLException, IOException {
        String insertQuery = "INSERT INTO students (name, age, email) VALUES (?, ?, ?)";
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {

            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) { // если id не нужен при вставке
                    pstmt.setString(1, data[0]);
                    pstmt.setInt(2, Integer.parseInt(data[1]));
                    pstmt.setString(3, data[2]);
                    pstmt.addBatch();
                }
            }
            pstmt.executeBatch();
        }
    }


    @FXML
    private void onGenerateReport(ActionEvent event) {
        StringBuilder report = new StringBuilder();


        report.append("=== Students ===\n");
        for (TableColumn<Student, ?> col : studentsTable.getColumns()) {
            report.append(col.getText()).append("\t");
        }
        report.append("\n");
        for (Student s : studentsTable.getItems()) {
            for (TableColumn<Student, ?> col : studentsTable.getColumns()) {
                Object val = col.getCellData(s);
                report.append(val != null ? val.toString() : "").append("\t");
            }
            report.append("\n");
        }
        report.append("\n");


        report.append("=== Teachers ===\n");
        for (TableColumn<Teacher, ?> col : teachersTable.getColumns()) report.append(col.getText()).append("\t");
        report.append("\n");
        for (Teacher t : teachersTable.getItems()) {
            for (TableColumn<Teacher, ?> col : teachersTable.getColumns()) {
                Object val = col.getCellData(t);
                report.append(val != null ? val.toString() : "").append("\t");
            }
            report.append("\n");
        }
        report.append("\n");


        report.append("=== Groups ===\n");
        for (TableColumn<Group, ?> col : groupsTable.getColumns()) report.append(col.getText()).append("\t");
        report.append("\n");
        for (Group g : groupsTable.getItems()) {
            for (TableColumn<Group, ?> col : groupsTable.getColumns()) {
                Object val = col.getCellData(g);
                report.append(val != null ? val.toString() : "").append("\t");
            }
            report.append("\n");
        }
        report.append("\n");


        report.append("=== Subjects ===\n");
        for (TableColumn<Subject, ?> col : subjectTable.getColumns()) report.append(col.getText()).append("\t");
        report.append("\n");
        for (Subject s : subjectTable.getItems()) {
            for (TableColumn<Subject, ?> col : subjectTable.getColumns()) {
                Object val = col.getCellData(s);
                report.append(val != null ? val.toString() : "").append("\t");
            }
            report.append("\n");
        }
        report.append("\n");


        report.append("=== Grades ===\n");
        for (TableColumn<Grade, ?> col : gradesTable.getColumns()) report.append(col.getText()).append("\t");
        report.append("\n");
        for (Grade g : gradesTable.getItems()) {
            for (TableColumn<Grade, ?> col : gradesTable.getColumns()) {
                Object val = col.getCellData(g);
                report.append(val != null ? val.toString() : "").append("\t");
            }
            report.append("\n");
        }
        report.append("\n");


        report.append("=== Performance ===\n");
        for (TableColumn<PerformanceSheet, ?> col : performanceTable.getColumns()) report.append(col.getText()).append("\t");
        report.append("\n");
        for (PerformanceSheet p : performanceTable.getItems()) {
            for (TableColumn<PerformanceSheet, ?> col : performanceTable.getColumns()) {
                Object val = col.getCellData(p);
                report.append(val != null ? val.toString() : "").append("\t");
            }
            report.append("\n");
        }
        report.append("\n");


        report.append("=== Schedule ===\n");
        for (TableColumn<Schedule, ?> col : scheduleTable.getColumns()) report.append(col.getText()).append("\t");
        report.append("\n");
        for (Schedule s : scheduleTable.getItems()) {
            for (TableColumn<Schedule, ?> col : scheduleTable.getColumns()) {
                Object val = col.getCellData(s);
                report.append(val != null ? val.toString() : "").append("\t");
            }
            report.append("\n");
        }


        try {
            Path path = Paths.get("full_report.txt");
            Files.writeString(path, report.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            showSuccess("Отчёт сформирован: " + path.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            showError("Ошибка при формировании отчёта: " + e.getMessage());
        }
    }



    @FXML
    private void addStudent(ActionEvent event) {

        Dialog<Student> d = new Dialog<>();
        d.setTitle("Добавить студента");
        d.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane g = new GridPane();
        g.setHgap(10);
        g.setVgap(10);

        TextField fSurname = new TextField();
        TextField fName = new TextField();
        TextField fPat = new TextField();
        TextField fPhone = new TextField();
        DatePicker dp = new DatePicker();

        ComboBox<Group> cbGroup = new ComboBox<>(groups);
        ComboBox<Speciality> cbSpec =
                new ComboBox<>(FXCollections.observableArrayList(specialityDao.getAll()));

        ComboBox<Integer> cbCourse = new ComboBox<>(
                FXCollections.observableArrayList(1, 2, 3, 4, 5, 6)
        );

        g.add(new Label("Фамилия:"), 0, 0); g.add(fSurname, 1, 0);
        g.add(new Label("Имя:"), 0, 1);     g.add(fName, 1, 1);
        g.add(new Label("Отчество:"), 0, 2);g.add(fPat, 1, 2);
        g.add(new Label("Телефон:"), 0, 3); g.add(fPhone, 1, 3);
        g.add(new Label("Дата рождения:"), 0, 4); g.add(dp, 1, 4);
        g.add(new Label("Группа:"), 0, 5);  g.add(cbGroup, 1, 5);
        g.add(new Label("Специальность:"), 0, 6); g.add(cbSpec, 1, 6);
        g.add(new Label("Курс:"), 0, 7); g.add(cbCourse, 1, 7);

        d.getDialogPane().setContent(g);
        applyThemeToDialog(d);

        d.setResultConverter(btn -> {
            if (btn != ButtonType.OK) return null;

            if (cbGroup.getValue() == null) {
                warnUser("Выберите группу");
                return null;
            }
            if (cbSpec.getValue() == null) {
                warnUser("Выберите специальность");
                return null;
            }
            if (dp.getValue() == null) {
                warnUser("Укажите дату рождения");
                return null;
            }
            if (cbCourse.getValue() == null) {
                warnUser("Выберите курс");
                return null;
            }

            return new Student(
                    0,
                    cbGroup.getValue().getId(),
                    cbSpec.getValue().getId(),
                    collegeId,
                    fSurname.getText(),
                    fName.getText(),
                    fPat.getText(),
                    fPhone.getText(),
                    dp.getValue(),
                    cbCourse.getValue()
            );
        });

        d.showAndWait().ifPresent(s -> {
            try {
                studentDao.insert(s);
                reloadDataSync();
                showSuccess("Студент добавлен.");
            } catch (Exception e) {
                errorHandler("Ошибка при добавлении студента.", e);
            }
        });
    }


    @FXML
    private void editStudent(ActionEvent event) {

        Student sel = studentsTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            warnUser("Выберите студента.");
            return;
        }

        Dialog<Student> d = new Dialog<>();
        d.setTitle("Редактировать студента");
        d.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane g = new GridPane();
        g.setHgap(10);
        g.setVgap(10);

        TextField fSurname = new TextField(sel.getSurname());
        TextField fName = new TextField(sel.getName());
        TextField fPat = new TextField(sel.getPatronymic());
        TextField fPhone = new TextField(sel.getPhone());
        DatePicker dp = new DatePicker(sel.getBirthday());

        ComboBox<Group> cbGroup = new ComboBox<>(groups);
        ComboBox<Speciality> cbSpec =
                new ComboBox<>(FXCollections.observableArrayList(specialityDao.getAll()));

        ComboBox<Integer> cbCourse = new ComboBox<>(
                FXCollections.observableArrayList(1, 2, 3, 4, 5, 6)
        );
        cbCourse.setValue(sel.getCourse());

        groups.stream()
                .filter(gp -> gp.getId() == sel.getGroupId())
                .findFirst().ifPresent(cbGroup::setValue);

        cbSpec.getItems().stream()
                .filter(sp -> sp.getId() == sel.getSpecialityId())
                .findFirst().ifPresent(cbSpec::setValue);

        g.add(new Label("Фамилия:"), 0, 0); g.add(fSurname, 1, 0);
        g.add(new Label("Имя:"), 0, 1);     g.add(fName, 1, 1);
        g.add(new Label("Отчество:"), 0, 2);g.add(fPat, 1, 2);
        g.add(new Label("Телефон:"), 0, 3); g.add(fPhone, 1, 3);
        g.add(new Label("Дата рождения:"), 0, 4); g.add(dp, 1, 4);
        g.add(new Label("Группа:"), 0, 5);  g.add(cbGroup, 1, 5);
        g.add(new Label("Специальность:"), 0, 6); g.add(cbSpec, 1, 6);
        g.add(new Label("Курс:"), 0, 7); g.add(cbCourse, 1, 7);

        d.getDialogPane().setContent(g);
        applyThemeToDialog(d);

        d.setResultConverter(btn -> {
            if (btn != ButtonType.OK) return null;

            return new Student(
                    sel.getId(),
                    cbGroup.getValue() != null ? cbGroup.getValue().getId() : sel.getGroupId(),
                    cbSpec.getValue() != null ? cbSpec.getValue().getId() : sel.getSpecialityId(),
                    collegeId,
                    fSurname.getText(),
                    fName.getText(),
                    fPat.getText(),
                    fPhone.getText(),
                    dp.getValue(),
                    cbCourse.getValue() != null ? cbCourse.getValue() : sel.getCourse()
            );
        });

        d.showAndWait().ifPresent(s -> {
            try {
                studentDao.update(s);
                reloadDataSync();
                showSuccess("Студент обновлён.");
            } catch (Exception e) {
                errorHandler("Ошибка при изменении студента.", e);
            }
        });
    }


    @FXML
    private void deleteStudent(ActionEvent event) {
        Student sel = studentsTable.getSelectionModel().getSelectedItem();
        if (sel == null) { warnUser("Выберите студента."); return; }

        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Удаление");
        a.setContentText("Удалить выбранного студента?");
        Optional<ButtonType> r = a.showAndWait();
        if (r.isPresent() && r.get() == ButtonType.OK) {
            try {
                studentDao.delete(sel.getId());
                reloadDataSync();
                showSuccess("Студент удалён.");
            } catch (Exception e) {
                errorHandler("Ошибка при удалении студента.", e);
            }
        }
    }

    @FXML
    private void searchStudents(javafx.scene.input.KeyEvent keyEvent) {
        String f = studentSearchField.getText() == null ? "" : studentSearchField.getText().toLowerCase();
        studentsTable.setItems(students.filtered(st ->
                (st.getSurname() != null && st.getSurname().toLowerCase().contains(f)) ||
                        (st.getName() != null && st.getName().toLowerCase().contains(f)) ||
                        (st.getGroupName() != null && st.getGroupName().toLowerCase().contains(f))
        ));
    }

    /* =================== Teachers =================== */

    @FXML
    private void openAddTeacherDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/database/Teacher-dialog.fxml")
            );
            Parent pane = loader.load();

            TeacherDialogController controller = loader.getController();
            controller.setTeacherDAO(teacherDao);
            controller.setDialogStage(new Stage());
            controller.setTeacher(new Teacher());

            Stage stage = controller.getDialogStage();
            stage.setTitle("Добавить преподавателя");
            stage.setScene(new Scene(pane));
            stage.setMinWidth(600);
            stage.setMinHeight(500);
            stage.initOwner(teachersTable.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();

            Teacher result = controller.getResult();
            if (result != null) {
                try {
                    teacherDao.addTeacher(result);

                    // 🔥 синхронизация группы
                    if (result.getCuratorGroupId() != null) {
                        Group g = groupDao.findById(result.getCuratorGroupId());
                        if (g != null) {
                            groupDao.syncGroupCurator(g);
                        }
                    }

                    reloadDataSync();
                    updateMaps();
                    teachersTable.refresh();

                    showSuccess("Преподаватель добавлен.");
                } catch (SQLException ex) {
                    errorHandler("Ошибка при добавлении преподавателя в базу.", ex);
                }
            }

        } catch (IOException e) {
            errorHandler("Ошибка при открытии формы добавления преподавателя.", e);
        }
    }


    @FXML
    private void openEditTeacherDialog() {
        Teacher sel = teachersTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            warnUser("Выберите преподавателя.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/database/Teacher-dialog.fxml")
            );
            Parent pane = loader.load();

            TeacherDialogController controller = loader.getController();
            controller.setTeacherDAO(teacherDao);
            controller.setDialogStage(new Stage());
            controller.setTeacher(sel);

            Stage stage = controller.getDialogStage();
            stage.setTitle("Редактировать преподавателя");
            stage.setScene(new Scene(pane));
            stage.setMinWidth(600);
            stage.setMinHeight(500);
            stage.initOwner(teachersTable.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();

            Teacher result = controller.getResult();
            if (result != null) {
                try {
                    // обновляем преподавателя
                    teacherDao.updateTeacher(result);

                    // синхронизация всех групп

                    for (Group g : groupDao.getAll()) {
                        groupDao.syncGroupCurator(g);
                    }

                    // обновляем интерфейс
                    reloadDataSync();
                    updateMaps();
                    teachersTable.refresh();

                    showSuccess("Преподаватель обновлён.");
                } catch (SQLException ex) {
                    errorHandler("Ошибка при обновлении преподавателя в базе.", ex);
                }
            }

        } catch (IOException e) {
            errorHandler("Ошибка при открытии формы редактирования преподавателя.", e);
        }
    }



    @FXML
    private void deleteTeacher(ActionEvent event) {
        Teacher sel = teachersTable.getSelectionModel().getSelectedItem();
        if (sel == null) { warnUser("Выберите преподавателя."); return; }

        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Удаление");
        a.setContentText("Удалить выбранного преподавателя?");
        Optional<ButtonType> r = a.showAndWait();
        if (r.isPresent() && r.get() == ButtonType.OK) {
            try {
                teacherDao.deleteTeacher(sel.getIdt());
                reloadDataSync();
                showSuccess("Преподаватель удалён.");
            } catch (Exception e) {
                errorHandler("Ошибка при удалении преподавателя.", e);
            }
        }
    }

    @FXML
    private void searchTeachers(javafx.scene.input.KeyEvent keyEvent) {
        String f = teacherSearchField.getText() == null ? "" : teacherSearchField.getText().toLowerCase();
        teachersTable.setItems(teachers.filtered(t ->
                (t.getSurname() != null && t.getSurname().toLowerCase().contains(f)) ||
                        (t.getName() != null && t.getName().toLowerCase().contains(f)) ||
                        (t.getPosition() != null && t.getPosition().toLowerCase().contains(f))
        ));
    }

    /* =================== Groups CRUD & search =================== */
    private void assignCuratorAutomatically(Group group) {

        teacherDao.getAll().stream()
                .filter(Teacher::isCurator)
                .filter(t -> t.getCuratorGroupId() != null)
                .filter(t -> t.getCuratorGroupId().equals(group.getId()))
                .findFirst()
                .ifPresent(curator -> {
                    group.setCuratorId(curator.getIdt());
                    group.setCurator(curator.getFullName());
                    try {
                        groupDao.update(group);
                    } catch (Exception e) {
                        errorHandler("Ошибка при назначении куратора.", e);
                    }
                });
    }

    @FXML
    private void addGroup(ActionEvent event) {

        Dialog<Group> dialog = new Dialog<>();
        dialog.setTitle("Добавить группу");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField tfName = new TextField();

        ComboBox<Speciality> cbSpec =
                new ComboBox<>(FXCollections.observableArrayList(specialityDao.getAll()));

        List<Teacher> curators = teacherDao.getAll().stream()
                .filter(Teacher::isCurator)
                .toList();

        ComboBox<Teacher> cbCurator =
                new ComboBox<>(FXCollections.observableArrayList(curators));

        grid.add(new Label("Название:"), 0, 0);
        grid.add(tfName, 1, 0);
        grid.add(new Label("Специальность:"), 0, 1);
        grid.add(cbSpec, 1, 1);
        grid.add(new Label("Куратор:"), 0, 2);
        grid.add(cbCurator, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn != ButtonType.OK) return null;

            if (cbSpec.getValue() == null) {
                warnUser("Выберите специальность");
                return null;
            }

            return new Group(
                    0,
                    cbSpec.getValue().getId(),
                    collegeId,
                    1,
                    tfName.getText(),
                    null,
                    null
            );
        });

        dialog.showAndWait().ifPresent(g -> {
            try {
                // сначала сохраняем группу
                groupDao.insert(g);

                // автоматически подтягиваем куратора
                groupDao.syncGroupCurator(g);

                reloadDataSync();
                showSuccess("Группа добавлена.");
            } catch (Exception e) {
                errorHandler("Ошибка при добавлении группы.", e);
            }
        });
    }

    @FXML
    private void editGroup(ActionEvent event) {

        Group sel = groupsTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            warnUser("Выберите группу.");
            return;
        }

        Dialog<Group> dialog = new Dialog<>();
        dialog.setTitle("Редактировать группу");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField tfName = new TextField(sel.getName());

        ComboBox<Speciality> cbSpec =
                new ComboBox<>(FXCollections.observableArrayList(specialityDao.getAll()));

        cbSpec.getItems().stream()
                .filter(s -> s.getId() == sel.getSpecId())
                .findFirst().ifPresent(cbSpec::setValue);

        List<Teacher> curators = teacherDao.getAll().stream()
                .filter(Teacher::isCurator)
                .toList();

        ComboBox<Teacher> cbCurator =
                new ComboBox<>(FXCollections.observableArrayList(curators));

        grid.add(new Label("Название:"), 0, 0); grid.add(tfName, 1, 0);
        grid.add(new Label("Специальность:"), 0, 1); grid.add(cbSpec, 1, 1);
        grid.add(new Label("Куратор:"), 0, 2); grid.add(cbCurator, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn != ButtonType.OK) return null;

            try {
                sel.setName(tfName.getText());
                sel.setSpecId(cbSpec.getValue().getId());

                sel.setCuratorId(null);
                sel.setCurator(null);
                groupDao.update(sel);

                Teacher curator = cbCurator.getValue();
                if (curator != null) {
                    sel.setCuratorId(curator.getIdt());
                    sel.setCurator(curator.getFullName());
                    groupDao.update(sel);
                }

            } catch (Exception e) {
                errorHandler("Ошибка при обновлении группы.", e);
            }
            return sel;
        });

        dialog.showAndWait().ifPresent(g -> {
            try {
                groupDao.syncGroupCurator(g);

                reloadDataSync();
                showSuccess("Группа обновлена.");
            } catch (Exception e) {
                errorHandler("Ошибка при обновлении группы.", e);
            }
        });
}



        @FXML
    private void deleteGroup(ActionEvent event) {
        Group sel = groupsTable.getSelectionModel().getSelectedItem();
        if (sel == null) { warnUser("Выберите группу."); return; }
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Удалить группу?", ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> r = a.showAndWait();
        if (r.isPresent() && r.get() == ButtonType.OK) {
            try {
                groupDao.delete(sel.getId());
                reloadDataSync();
                showSuccess("Группа удалена.");
            } catch (Exception e) {
                errorHandler("Ошибка при удалении группы.", e);
            }
        }
    }

    @FXML
    private void searchGroups(javafx.scene.input.KeyEvent keyEvent) {
        String f = groupSearchField.getText() == null ? "" : groupSearchField.getText().toLowerCase();
        groupsTable.setItems(groups.filtered(g ->
                (g.getName() != null && g.getName().toLowerCase().contains(f)) ||
                        (g.getCurator() != null && g.getCurator().toLowerCase().contains(f))
        ));
    }

    /* =================== Subjects =================== */

    @FXML
    private void addSubject(ActionEvent event) {
        Dialog<Subject> d = new Dialog<>();
        d.setTitle("Добавить дисциплину");
        d.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        GridPane g = new GridPane(); g.setHgap(10); g.setVgap(10);
        TextField fName = new TextField();
        TextField fDesc = new TextField();
        g.add(new Label("Название:"),0,0); g.add(fName,1,0);
        g.add(new Label("Описание:"),0,1); g.add(fDesc,1,1);
        d.getDialogPane().setContent(g);
        d.setResultConverter(btn -> {
            if (btn == ButtonType.OK) return new Subject(0, fName.getText(), fDesc.getText());
            return null;
        });

        Optional<Subject> r = d.showAndWait();
        r.ifPresent(s -> {
            try {
                subjectDao.insert(s);
                reloadDataSync();
                showSuccess("Дисциплина добавлена.");
            } catch (Exception e) {
                errorHandler("Ошибка при добавлении дисциплины.", e);
            }
        });
    }

    @FXML
    private void editSubject(ActionEvent event) {
        Subject sel = subjectTable.getSelectionModel().getSelectedItem();
        if (sel == null) { warnUser("Выберите дисциплину."); return; }

        Dialog<Subject> d = new Dialog<>();
        d.setTitle("Редактировать дисциплину");
        d.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        GridPane g = new GridPane(); g.setHgap(10); g.setVgap(10);
        TextField fName = new TextField(sel.getName());
        TextField fDesc = new TextField(sel.getDescription());
        g.add(new Label("Название:"),0,0); g.add(fName,1,0);
        g.add(new Label("Описание:"),0,1); g.add(fDesc,1,1);
        d.getDialogPane().setContent(g);
        d.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                sel.setName(fName.getText());
                sel.setDescription(fDesc.getText());
                return sel;
            }
            return null;
        });

        Optional<Subject> r = d.showAndWait();
        r.ifPresent(s -> {
            try {
                subjectDao.update(s);
                reloadDataSync();
                showSuccess("Дисциплина обновлена.");
            } catch (Exception e) {
                errorHandler("Ошибка при обновлении дисциплины.", e);
            }
        });
    }

    @FXML
    private void deleteSubject(ActionEvent event) {
        Subject sel = subjectTable.getSelectionModel().getSelectedItem();
        if (sel == null) { warnUser("Выберите дисциплину."); return; }
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Удалить дисциплину?", ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> r = a.showAndWait();
        if (r.isPresent() && r.get() == ButtonType.OK) {
            try {
                subjectDao.delete(sel.getId());
                reloadDataSync();
                showSuccess("Дисциплина удалена.");
            } catch (Exception e) {
                errorHandler("Ошибка при удалении дисциплины.", e);
            }
        }
    }

    @FXML
    private void searchSubjects(javafx.scene.input.KeyEvent keyEvent) {
        String f = subjectSearchField.getText() == null ? "" : subjectSearchField.getText().toLowerCase();
        subjectTable.setItems(subjects.filtered(s -> s.getName() != null && s.getName().toLowerCase().contains(f)));
    }

    /* =================== Grades =================== */

    @FXML
    private void addGrade(ActionEvent event) {
        Dialog<Grade> d = new Dialog<>();
        d.setTitle("Добавить оценку");
        d.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane g = new GridPane();
        g.setHgap(10);
        g.setVgap(10);

        ComboBox<Student> cbStudent = new ComboBox<>(students);
        ComboBox<Teacher> cbTeacher = new ComboBox<>(teachers);
        ComboBox<Subject> cbSubject = new ComboBox<>(subjects);
        ComboBox<Group> cbGroup = new ComboBox<>(groups);
        cbStudent.valueProperty().addListener((obs, oldStudent, newStudent) -> {
            if (newStudent != null) {

                // ВАРИАНТ 1: если у Student есть Group
                if (newStudent.getGroup() != null) {
                    cbGroup.setValue(newStudent.getGroup());
                }

                // ВАРИАНТ 2: если есть только ID группы
                else {
                    groups.stream()
                            .filter(gr -> gr.getId() == newStudent.getGroupId())
                            .findFirst()
                            .ifPresent(cbGroup::setValue);
                }

            } else {
                cbGroup.setValue(null);
            }
        });
        TextArea taDescription = new TextArea();
        taDescription.setPrefRowCount(3);

        Spinner<Integer> spValue = new Spinner<>(1, 10, 5);
        DatePicker dp = new DatePicker(LocalDate.now());

        Spinner<Integer> spCourse = new Spinner<>(1, 6, 1);
        Spinner<Integer> spSemester = new Spinner<>(1, 12, 1);

        g.add(new Label("Студент:"), 0, 0); g.add(cbStudent, 1, 0);
        g.add(new Label("Преподаватель:"), 0, 1); g.add(cbTeacher, 1, 1);
        g.add(new Label("Предмет:"), 0, 2); g.add(cbSubject, 1, 2);
        g.add(new Label("Группа:"), 0, 3); g.add(cbGroup, 1, 3);
        g.add(new Label("Описание:"), 0, 4); g.add(taDescription, 1, 4);
        g.add(new Label("Оценка:"), 0, 5); g.add(spValue, 1, 5);
        g.add(new Label("Дата:"), 0, 6); g.add(dp, 1, 6);
        g.add(new Label("Курс:"), 0, 7); g.add(spCourse, 1, 7);
        g.add(new Label("Семестр:"), 0, 8); g.add(spSemester, 1, 8);

        d.getDialogPane().setContent(g);

        d.setResultConverter(btn -> {
            if (btn == ButtonType.OK
                    && cbStudent.getValue() != null
                    && cbTeacher.getValue() != null
                    && cbSubject.getValue() != null
                    && cbGroup.getValue() != null) {

                return new Grade(
                        0,
                        cbTeacher.getValue().getIdt(),
                        cbStudent.getValue().getId(),
                        cbSubject.getValue().getName(),
                        taDescription.getText(),
                        spValue.getValue(),
                        dp.getValue(),
                        cbStudent.getValue().getFullName(),
                        cbTeacher.getValue().getFullName(),
                        spCourse.getValue(),
                        spSemester.getValue(),
                        cbGroup.getValue() // сохраняем выбранную группу
                );
            }
            return null;
        });

        d.showAndWait().ifPresent(gr -> {
            try {
                gradeDao.insert(gr);
                updatePerformanceSheet(gr.getStudentId(), getCurrentSemester(gr.getStudentId()));
                reloadDataSync();
                showSuccess("Оценка добавлена.");
            } catch (Exception e) {
                errorHandler("Ошибка при добавлении оценки.", e);
            }
        });
    }

    @FXML
    private void editGrade(ActionEvent event) {
        Grade sel = gradesTable.getSelectionModel().getSelectedItem();
        if (sel == null) { warnUser("Выберите оценку."); return; }

        Dialog<Grade> d = new Dialog<>();
        d.setTitle("Редактировать оценку");
        d.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane g = new GridPane();
        g.setHgap(10);
        g.setVgap(10);

        ComboBox<Student> cbStudent = new ComboBox<>(students);
        ComboBox<Teacher> cbTeacher = new ComboBox<>(teachers);
        ComboBox<Subject> cbSubject = new ComboBox<>(subjects);
        ComboBox<Group> cbGroup = new ComboBox<>(groups);
        cbStudent.valueProperty().addListener((obs, oldStudent, newStudent) -> {
            if (newStudent != null) {

                // ВАРИАНТ 1: если у Student есть Group
                if (newStudent.getGroup() != null) {
                    cbGroup.setValue(newStudent.getGroup());
                }

                // ВАРИАНТ 2: если есть только ID группы
                else {
                    groups.stream()
                            .filter(gr -> gr.getId() == newStudent.getGroupId())
                            .findFirst()
                            .ifPresent(cbGroup::setValue);
                }

            } else {
                cbGroup.setValue(null);
            }
        });
        TextArea taDescription = new TextArea();
        taDescription.setPrefRowCount(3);

        Spinner<Integer> spValue = new Spinner<>(1, 10, sel.getValue());
        DatePicker dp = new DatePicker(sel.getDate());
        Spinner<Integer> spCourse = new Spinner<>(1, 6, sel.getCourse());
        Spinner<Integer> spSemester = new Spinner<>(1, 12, sel.getSemester());

        // Устанавливаем текущие значения
        students.stream().filter(s -> s.getId() == sel.getStudentId()).findFirst().ifPresent(cbStudent::setValue);
        teachers.stream().filter(t -> t.getIdt() == sel.getTeacherId()).findFirst().ifPresent(cbTeacher::setValue);
        subjects.stream().filter(s -> s.getName().equals(sel.getSubject())).findFirst().ifPresent(cbSubject::setValue);
        if (sel.getGroup() != null) {
            groups.stream().filter(gr -> gr.getId() == sel.getGroup().getId()).findFirst().ifPresent(cbGroup::setValue);
        }
        taDescription.setText(sel.getDescription());

        g.add(new Label("Студент:"), 0, 0); g.add(cbStudent, 1, 0);
        g.add(new Label("Преподаватель:"), 0, 1); g.add(cbTeacher, 1, 1);
        g.add(new Label("Предмет:"), 0, 2); g.add(cbSubject, 1, 2);
        g.add(new Label("Группа:"), 0, 3); g.add(cbGroup, 1, 3);
        g.add(new Label("Описание:"), 0, 4); g.add(taDescription, 1, 4);
        g.add(new Label("Оценка:"), 0, 5); g.add(spValue, 1, 5);
        g.add(new Label("Дата:"), 0, 6); g.add(dp, 1, 6);
        g.add(new Label("Курс:"), 0, 7); g.add(spCourse, 1, 7);
        g.add(new Label("Семестр:"), 0, 8); g.add(spSemester, 1, 8);

        d.getDialogPane().setContent(g);

        d.setResultConverter(btn -> {
            if (btn == ButtonType.OK
                    && cbStudent.getValue() != null
                    && cbTeacher.getValue() != null
                    && cbSubject.getValue() != null
                    && cbGroup.getValue() != null) {

                sel.setStudentId(cbStudent.getValue().getId());
                sel.setTeacherId(cbTeacher.getValue().getIdt());
                sel.setSubject(cbSubject.getValue().getName());
                sel.setDescription(taDescription.getText());
                sel.setValue(spValue.getValue());
                sel.setDate(dp.getValue());
                sel.setCourse(spCourse.getValue());
                sel.setSemester(spSemester.getValue());
                sel.setGroup(cbGroup.getValue());

                return sel;
            }
            return null;
        });

        d.showAndWait().ifPresent(gd -> {
            try {
                gradeDao.update(gd);
                updatePerformanceSheet(gd.getStudentId(), getCurrentSemester(gd.getStudentId()));
                reloadDataSync();
                showSuccess("Оценка обновлена.");
            } catch (Exception e) {
                errorHandler("Ошибка при обновлении оценки.", e);
            }
        });
    }

    @FXML
    private void deleteGrade(ActionEvent event) {
        Grade sel = gradesTable.getSelectionModel().getSelectedItem();
        if (sel == null) { warnUser("Выберите оценку."); return; }
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Удалить оценку?", ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> r = a.showAndWait();
        if (r.isPresent() && r.get() == ButtonType.OK) {
            try {
                gradeDao.delete(sel.getId());
                reloadDataSync();
                showSuccess("Оценка удалена.");
            } catch (Exception e) {
                errorHandler("Ошибка при удалении оценки.", e);
            }
        }
    }

    @FXML
    private void searchGrades(javafx.scene.input.KeyEvent keyEvent) {
        String f = gradeSearchField.getText() == null ? "" : gradeSearchField.getText().toLowerCase();
        gradesTable.setItems(grades.filtered(g ->
                (g.getStudentName() != null && g.getStudentName().toLowerCase().contains(f)) ||
                        (g.getSubjectName() != null && g.getSubjectName().toLowerCase().contains(f))
        ));
    }

    /* =================== Performance =================== */

    @FXML
    private void addPerformance(ActionEvent event) {
        Dialog<PerformanceSheet> d = new Dialog<>();
        d.setTitle("Добавить табель");
        d.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane g = new GridPane();
        g.setHgap(10);
        g.setVgap(10);

        // Выбор студента
        ComboBox<Student> cbStudent = new ComboBox<>(students);
        cbStudent.setPromptText("Выберите студента");

        // Семестр
        Spinner<Integer> spSem = new Spinner<>(1, 12, 1);

        g.add(new Label("Студент:"), 0, 0);
        g.add(cbStudent, 1, 0);
        g.add(new Label("Семестр:"), 0, 1);
        g.add(spSem, 1, 1);

        d.getDialogPane().setContent(g);

        d.setResultConverter(btn -> {
            if (btn == ButtonType.OK && cbStudent.getValue() != null) {
                Student selectedStudent = cbStudent.getValue();
                PerformanceSheet sheet = new PerformanceSheet(
                        0,
                        selectedStudent.getId(),
                        selectedStudent.getFullName(),
                        selectedStudent.getGroupName(),
                        selectedStudent.getSpecialityName(),
                        selectedStudent.getCourse(),
                        spSem.getValue(),
                        0 // средний балл будет рассчитан автоматически
                );

                // Рассчитываем и обновляем средний балл
                updatePerformanceSheet(sheet.getStudentId(), sheet.getSemester());
                return sheet;
            }
            return null;
        });

        Optional<PerformanceSheet> result = d.showAndWait();
        result.ifPresent(p -> showSuccess("Табель добавлен."));
    }

    @FXML
    private void editPerformance(ActionEvent event) {
        PerformanceSheet sel = performanceTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            warnUser("Выберите табель.");
            return;
        }

        Dialog<PerformanceSheet> d = new Dialog<>();
        d.setTitle("Редактировать табель");
        d.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane g = new GridPane();
        g.setHgap(10);
        g.setVgap(10);

        // Студент (не редактируем)
        ComboBox<Student> cbStudent = new ComboBox<>(students);
        cbStudent.setValue(students.stream()
                .filter(student -> student.getId() == sel.getStudentId())
                .findFirst().orElse(null));
        cbStudent.setDisable(true);

        // Семестр
        Spinner<Integer> spSem = new Spinner<>(1, 12, sel.getSemester());

        // Средний балл
        TextField tfAvg = new TextField(String.format("%.2f", sel.getAverage()));
        tfAvg.setEditable(false);

        g.add(new Label("Студент:"), 0, 0);
        g.add(cbStudent, 1, 0);
        g.add(new Label("Семестр:"), 0, 1);
        g.add(spSem, 1, 1);
        g.add(new Label("Средний балл:"), 0, 2);
        g.add(tfAvg, 1, 2);

        d.getDialogPane().setContent(g);

        d.setResultConverter(btn -> {
            if (btn == ButtonType.OK && cbStudent.getValue() != null) {
                sel.setSemester(spSem.getValue());
                // Пересчёт среднего балла
                updatePerformanceSheet(cbStudent.getValue().getId(), spSem.getValue());
                return sel;
            }
            return null;
        });

        Optional<PerformanceSheet> result = d.showAndWait();
        result.ifPresent(p -> showSuccess("Табель обновлён."));
    }

    public void updatePerformanceSheet(int studentId, int semester) {
        List<Grade> allGrades = gradeDao.getAll(); // все оценки
        double avg = gradeDao.calculateAverage(studentId, semester, allGrades);

        Student student = students.stream()
                .filter(s -> s.getId() == studentId)
                .findFirst()
                .orElse(null);

        if (student == null) return;

        PerformanceSheet existing = performances.stream()
                .filter(p -> p.getStudentId() == studentId && p.getSemester() == semester)
                .findFirst()
                .orElse(null);

        if (existing != null) {
            existing.setAverage(avg);
            performanceDao.update(existing); // обновляем запись в базе
        } else {
            PerformanceSheet newSheet = new PerformanceSheet(
                    0,
                    studentId,
                    student.getFullName(),
                    student.getGroupName(),
                    student.getSpecialityName(),
                    student.getCourse(),
                    semester,
                    avg
            );
            performances.add(newSheet);
            performanceDao.insert(newSheet); // вставка в базу
        }

        reloadDataSync();
    }

    @FXML
    private void deletePerformance(ActionEvent event) {
        PerformanceSheet sel = performanceTable.getSelectionModel().getSelectedItem();
        if (sel == null) { warnUser("Выберите табель."); return; }
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Удалить табель?", ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> r = a.showAndWait();
        if (r.isPresent() && r.get() == ButtonType.OK) {
            try {
                performanceDao.delete(sel.getId());
                reloadDataSync();
                showSuccess("Табель удалён.");
            } catch (Exception e) {
                errorHandler("Ошибка при удалении табеля.", e);
            }
        }
    }

    @FXML
    private void searchPerformance(javafx.scene.input.KeyEvent keyEvent) {
        String f = performanceSearchField.getText() == null ? "" : performanceSearchField.getText().toLowerCase();
        performanceTable.setItems(performances.filtered(p -> p.getStudentName() != null && p.getStudentName().toLowerCase().contains(f)));
    }

    /* =================== Schedule =================== */

    @FXML
    private void addSchedule(ActionEvent event) {
        Dialog<Schedule> d = new Dialog<>();
        d.setTitle("Добавить расписание");
        d.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane g = new GridPane();
        g.setHgap(10);
        g.setVgap(10);

        ComboBox<Group> cbGroup = new ComboBox<>(groups);
        ComboBox<Teacher> cbTeacher = new ComboBox<>(teachers);
        ComboBox<Subject> cbSubject = new ComboBox<>(subjects);
        DatePicker dp = new DatePicker(LocalDate.now());
        TextField fTime = new TextField();
        TextField fRoom = new TextField();

        g.add(new Label("Группа:"), 0, 0); g.add(cbGroup, 1, 0);
        g.add(new Label("Преподаватель:"), 0, 1); g.add(cbTeacher, 1, 1);
        g.add(new Label("Предмет:"), 0, 2); g.add(cbSubject, 1, 2);
        g.add(new Label("Дата:"), 0, 3); g.add(dp, 1, 3);
        g.add(new Label("Время:"), 0, 4); g.add(fTime, 1, 4);
        g.add(new Label("Аудитория:"), 0, 5); g.add(fRoom, 1, 5);

        d.getDialogPane().setContent(g);

        d.setResultConverter(btn -> {
            if (btn == ButtonType.OK
                    && cbGroup.getValue() != null
                    && cbTeacher.getValue() != null
                    && cbSubject.getValue() != null) {

                Group selectedGroup = cbGroup.getValue();
                Teacher selectedTeacher = cbTeacher.getValue();
                Subject selectedSubject = cbSubject.getValue();

                // Проверка корректности ID
                if (selectedGroup.getId() <= 0) {
                    warnUser("Выберите корректную группу!");
                    return null;
                }
                if (selectedTeacher.getIdt() <= 0) {
                    warnUser("Выберите корректного преподавателя!");
                    return null;
                }

                return new Schedule(
                        0,                          // id (будет сгенерирован в БД)
                        selectedTeacher.getIdt(),   // teacherId
                        selectedGroup.getId(),      // studentGroupId
                        selectedSubject.getName(),  // subject
                        fTime.getText(),            // time
                        dp.getValue(),              // date
                        fRoom.getText()             // room
                );
            }
            return null;
        });

        Optional<Schedule> r = d.showAndWait();
        r.ifPresent(s -> {
            try {
                scheduleDao.insert(s);
                reloadDataSync();
                showSuccess("Запись расписания добавлена.");
            } catch (Exception e) {
                errorHandler("Ошибка при добавлении записи расписания.", e);
            }
        });
    }



    @FXML
    private void editSchedule(ActionEvent event) {
        Schedule sel = scheduleTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            warnUser("Выберите запись расписания.");
            return;
        }

        Dialog<Schedule> d = new Dialog<>();
        d.setTitle("Редактировать запись расписания");
        d.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane g = new GridPane();
        g.setHgap(10);
        g.setVgap(10);

        ComboBox<Group> cbGroup = new ComboBox<>(groups);
        ComboBox<Teacher> cbTeacher = new ComboBox<>(teachers);
        ComboBox<Subject> cbSubject = new ComboBox<>(subjects);
        DatePicker dp = new DatePicker(sel.getDate());
        TextField fTime = new TextField(sel.getTime());
        TextField fRoom = new TextField(sel.getRoom());


        cbGroup.setValue(groups.stream().filter(gr -> gr.getId() == sel.getStudentGroupId()).findFirst().orElse(null));
        cbTeacher.setValue(teachers.stream().filter(t -> t.getIdt() == sel.getTeacherId()).findFirst().orElse(null));
        cbSubject.setValue(subjects.stream().filter(sb -> sb.getName().equals(sel.getSubject())).findFirst().orElse(null));

        g.add(new Label("Группа:"), 0, 0); g.add(cbGroup, 1, 0);
        g.add(new Label("Преподаватель:"), 0, 1); g.add(cbTeacher, 1, 1);
        g.add(new Label("Предмет:"), 0, 2); g.add(cbSubject, 1, 2);
        g.add(new Label("Дата:"), 0, 3); g.add(dp, 1, 3);
        g.add(new Label("Время:"), 0, 4); g.add(fTime, 1, 4);
        g.add(new Label("Аудитория:"), 0, 5); g.add(fRoom, 1, 5);

        d.getDialogPane().setContent(g);

        d.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {

                Group selectedGroup = cbGroup.getValue();
                Teacher selectedTeacher = cbTeacher.getValue();
                Subject selectedSubject = cbSubject.getValue();

                if (selectedGroup == null || selectedTeacher == null || selectedSubject == null) {
                    warnUser("Выберите корректные группу, преподавателя и предмет.");
                    return null;
                }

                sel.setStudentGroupId(selectedGroup.getId());
                sel.setTeacherId(selectedTeacher.getIdt());
                sel.setSubject(selectedSubject.getName());
                sel.setDate(dp.getValue());
                sel.setTime(fTime.getText());
                sel.setRoom(fRoom.getText());

                return sel;
            }
            return null;
        });

        Optional<Schedule> r = d.showAndWait();
        r.ifPresent(s -> {
            try {
                scheduleDao.update(s);
                reloadDataSync();
                showSuccess("Запись расписания обновлена.");
            } catch (Exception e) {
                errorHandler("Ошибка при обновлении записи расписания.", e);
            }
        });
    }


    @FXML
    private void deleteSchedule(ActionEvent event) {
        Schedule sel = scheduleTable.getSelectionModel().getSelectedItem();
        if (sel == null) { warnUser("Выберите запись расписания."); return; }
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Удалить запись расписания?", ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> r = a.showAndWait();
        if (r.isPresent() && r.get() == ButtonType.OK) {
            try {
                scheduleDao.delete(sel.getId());
                reloadDataSync();
                showSuccess("Запись расписания удалена.");
            } catch (Exception e) {
                errorHandler("Ошибка при удалении записи расписания.", e);
            }
        }
    }

    @FXML
    private void searchSchedule(javafx.scene.input.KeyEvent keyEvent) {
        String f = scheduleSearchField.getText() == null ? "" : scheduleSearchField.getText().toLowerCase();
        scheduleTable.setItems(schedules.filtered(s ->
                (s.getGroupName() != null && s.getGroupName().toLowerCase().contains(f)) ||
                        (s.getSubject() != null && s.getSubject().toLowerCase().contains(f)) ||
                        (s.getTeacherName() != null && s.getTeacherName().toLowerCase().contains(f))
        ));
    }

    /* =================== College save  =================== */

    @FXML
    private void saveCollege(ActionEvent event) {
        try {
            College c = collegeDao.getCollege();

            if (c == null) {
                showSuccess("Нет записи колледжа для обновления.");
                return;
            }

            c.setName(collegeNameField.getText());
            c.setAddress(collegeAddressField.getText());
            c.setPhone(collegePhoneField.getText());
            c.setInfo(collegeInfoArea.getText()); // ← ВАЖНО

            collegeDao.update(c);
            showSuccess("Информация колледжа сохранена.");

        } catch (Exception e) {
            errorHandler("Ошибка при сохранении информации колледжа.", e);
        }
    }


    /* =================== Print  =================== */

    @FXML
    private void printStudents(ActionEvent event) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(studentsTable.getScene().getWindow())) {
            boolean success = job.printPage(studentsTable);
            if (success) {
                job.endJob();
                showSuccess("Список студентов распечатан.");
            } else {
                showError("Не удалось распечатать студентов.");
            }
        }
    }
    @FXML
    private void printTeachers(ActionEvent event) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(teachersTable.getScene().getWindow())) {
            boolean success = job.printPage(teachersTable);
            if (success) job.endJob();
        }
    }

    @FXML
    private void printGroups(ActionEvent event) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(groupsTable.getScene().getWindow())) {
            boolean success = job.printPage(groupsTable);
            if (success) job.endJob();
        }
    }

    @FXML
    private void printSubjects(ActionEvent event) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(subjectTable.getScene().getWindow())) {
            boolean success = job.printPage(subjectTable);
            if (success) job.endJob();
        }
    }

    @FXML
    private void printGrades(ActionEvent event) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(gradesTable.getScene().getWindow())) {
            boolean success = job.printPage(gradesTable);
            if (success) job.endJob();
        }
    }

    @FXML
    private void printPerformance(ActionEvent event) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(performanceTable.getScene().getWindow())) {
            boolean success = job.printPage(performanceTable);
            if (success) job.endJob();
        }
    }

    @FXML
    private void printSchedule(ActionEvent event) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(scheduleTable.getScene().getWindow())) {
            boolean success = job.printPage(scheduleTable);
            if (success) job.endJob();
        }
    }


    /* =================== Cleanup =================== */

    public void cleanup() {
        try {
            if (dbConn != null && !dbConn.isClosed()) dbConn.close();
        } catch (SQLException e) {
            errorHandler("Ошибка при закрытии соединения с БД.", e);
        }
    }
}
