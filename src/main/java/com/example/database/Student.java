package com.example.database;

import java.time.LocalDate;
import java.time.Period;

public class Student {
    private int id;           // IDs
    private int groupId;      // IDg
    private int specialityId; // IDsp
    private int collegeId;    // IDc
    private int course;       // курс студента

    private Group group;
    private String surname;   // sSurname
    private String name;      // sName
    private String patronymic;// sPatronymic
    private String phone;     // sPhone
    private LocalDate birthday;// sBirthday

    private String groupName;        // имя группы
    private String specialityName;   // имя специальности

    public Student(int id,
                   int groupId,
                   int specialityId,
                   int collegeId,
                   String surname,
                   String name,
                   String patronymic,
                   String phone,
                   LocalDate birthday,
                   int course) {
        this.id = id;
        this.groupId = groupId;
        this.specialityId = specialityId;
        this.collegeId = collegeId;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.phone = phone;
        this.birthday = birthday;
        this.course = course;
    }

    // Конструктор с именами и курсом
    public Student(int id,
                   int groupId,
                   int specialityId,
                   int collegeId,
                   String surname,
                   String name,
                   String patronymic,
                   String phone,
                   LocalDate birthday,
                   String groupName,
                   String specialityName,
                   int course) {
        this(id, groupId, specialityId, collegeId, surname, name, patronymic, phone, birthday, course);
        this.groupName = groupName;
        this.specialityName = specialityName;
        this.course = course;
    }


    public int getId() { return id; }
    public int getGroupId() { return groupId; }
    public int getSpecialityId() { return specialityId; }
    public int getCollegeId() { return collegeId; }
    public int getCourse() { return course; }
    public void setCourse(int course) { this.course = course; }

    public String getSurname() { return surname; }
    public String getName() { return name; }
    public String getPatronymic() { return patronymic; }
    public String getPhone() { return phone; }
    public LocalDate getBirthday() { return birthday; }
    public Group getGroup() {
        return group;
    }
    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }

    public String getSpecialityName() { return specialityName; }
    public void setSpecialityName(String specialityName) { this.specialityName = specialityName; }

    public String getFullName() {
        if (patronymic == null || patronymic.isBlank()) return surname + " " + name;
        return surname + " " + name + " " + patronymic;
    }

    @Override
    public String toString() {
        return getFullName();
    }

    public int getAge() {
        if (birthday == null) return 0;
        return Period.between(birthday, LocalDate.now()).getYears();
    }

}
