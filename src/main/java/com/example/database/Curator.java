package com.example.database;

public class Curator {

    private int collegeId;
    private int courseId;
    private int groupId;

    private String surname;
    private String name;
    private String patronymic;

    public Curator(int collegeId, int courseId, int groupId,
                   String surname, String name, String patronymic) {

        this.collegeId = collegeId;
        this.courseId = courseId;
        this.groupId = groupId;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
    }

    public int getCollegeId() { return collegeId; }
    public int getCourseId() { return courseId; }
    public int getGroupId() { return groupId; }

    public String getSurname() { return surname; }
    public String getName() { return name; }
    public String getPatronymic() { return patronymic; }

    public String getFullName() {
        return surname + " " + name + " " + patronymic;
    }
}
