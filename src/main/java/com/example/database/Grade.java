package com.example.database;

import java.time.LocalDate;

public class Grade {

    private int id;            // IDgr
    private int teacherId;     // IDt
    private int studentId;     // IDp
    private String subject;    // grSubject
    private String description; // grDescription
    private int value;         // grValue
    private LocalDate date;    // grDate
    private int course;        // курс
    private int semester;      // семестр
    private Group group;       // группа

    // Дополнительные поля для отображения
    private String studentName;
    private String subjectName;
    private String teacherName;
    private String groupName;

    // ===== БАЗОВЫЙ КОНСТРУКТОР (ВНУТРЕННИЙ) =====
    private Grade(int id, int teacherId, int studentId,
                  String subject, String description,
                  int value, LocalDate date) {
        this.id = id;
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.subject = subject;
        this.description = description;
        this.value = value;
        this.date = date;
    }

    // ===== ОСНОВНОЙ КОНСТРУКТОР (для JOIN: студент + преподаватель) =====
    public Grade(int id, int teacherId, int studentId,
                 String subject, String description,
                 int value, LocalDate date,
                 String studentName, String teacherName,
                 int course, int semester, Group group) {
        this(id, teacherId, studentId, subject, description, value, date);
        this.studentName = studentName;
        this.teacherName = teacherName;
        this.course = course;
        this.semester = semester;
        this.group = group;
        this.groupName = group != null ? group.getName() : null;
    }

    // ===== GETTERS =====
    public int getId() { return id; }
    public int getTeacherId() { return teacherId; }
    public int getStudentId() { return studentId; }
    public String getSubject() { return subject; }
    public String getDescription() { return description; }
    public int getValue() { return value; }
    public LocalDate getDate() { return date; }
    public int getCourse() { return course; }
    public int getSemester() { return semester; }
    public Group getGroup() { return group; }
    public String getGroupName() { return groupName; }

    public String getStudentName() { return studentName; }
    public String getSubjectName() { return subjectName; }
    public String getTeacherName() { return teacherName; }

    // ===== SETTERS =====
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public void setSubject(String subject) { this.subject = subject; }
    public void setDescription(String description) { this.description = description; }
    public void setValue(int value) { this.value = value; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setCourse(int course) { this.course = course; }
    public void setSemester(int semester) { this.semester = semester; }
    public void setGroup(Group group) {
        this.group = group;
        this.groupName = group != null ? group.getName() : null;
    }

}
