package com.example.database;

public class PerformanceSheet {
    private int id;
    private int studentId;
    private String studentName;
    private String groupName;       // имя группы
    private String specialityName;
    private int course;
    private int semester;
    private double average;

    // Основной конструктор
    public PerformanceSheet(int id, int studentId, String studentName,
                            String groupName, String specialityName,
                            int course, int semester, double average) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.groupName = groupName;
        this.specialityName = specialityName;
        this.course = course;
        this.semester = semester;
        this.average = average;
    }

    // Дополнительный конструктор для упрощённого создания
    public PerformanceSheet(int id, int studentId, int semester, double average, String studentName) {
        this.id = id;
        this.studentId = studentId;
        this.semester = semester;
        this.average = average;
        this.studentName = studentName;
        this.groupName = "";
        this.specialityName = "";
        this.course = 0;
    }


    public int getId() { return id; }
    public int getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public String getGroupName() { return groupName; }
    public String getSpecialityName() { return specialityName; }
    public int getCourse() { return course; }
    public int getSemester() { return semester; }
    public double getAverage() { return average; }


    public void setId(int id) { this.id = id; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }
    public void setSpecialityName(String specialityName) { this.specialityName = specialityName; }
    public void setCourse(int course) { this.course = course; }
    public void setSemester(int semester) { this.semester = semester; }
    public void setAverage(double average) { this.average = average; }
}
