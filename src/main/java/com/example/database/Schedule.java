package com.example.database;

import java.time.LocalDate;

public class Schedule {

    private int id;
    private int teacherId;
    private int studentGroupId;
    private String subject;
    private String time;
    private String room;
    private LocalDate date;

    // Для TableView
    private String teacherName;
    private String groupName;

    public Schedule(int id, int teacherId, int studentGroupId, String subject,
                    String time, LocalDate date, String room) {
        this.id = id;
        this.teacherId = teacherId;
        this.studentGroupId = studentGroupId;
        this.subject = subject;
        this.time = time;
        this.date = date;
        this.room = room;
    }

    // Конструктор с именами
    public Schedule(int id, int teacherId, int studentGroupId, String subject,
                    String time, LocalDate date, String room, String teacherName, String groupName) {
        this(id, teacherId, studentGroupId, subject, time, date, room);
        this.teacherName = teacherName;
        this.groupName = groupName;
    }

    //GET / SET
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTeacherId() { return teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }

    public int getStudentGroupId() { return studentGroupId; }
    public void setStudentGroupId(int studentGroupId) { this.studentGroupId = studentGroupId; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }
}
