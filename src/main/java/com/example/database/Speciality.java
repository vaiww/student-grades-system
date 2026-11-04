package com.example.database;

public class Speciality {
    private int id;   // IDsp
    private Integer collegeId; // IDc
    private String name; // sName

    public Speciality(int id, Integer collegeId, String name) {
        this.id = id;
        this.collegeId = collegeId;
        this.name = name;
    }

    public int getId() { return id; }
    public Integer getCollegeId() { return collegeId; }
    public String getName() { return name; }

    @Override
    public String toString() { return name; } // чтобы ComboBox показывал имя
}
