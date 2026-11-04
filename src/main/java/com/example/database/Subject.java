package com.example.database;

public class Subject {
    private int id;
    private String name;
    private String description;

    public Subject(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    @Override
    public String toString() {
        return name != null ? name : "";
    }
    // ===== GETTERS для таблицы =====
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    // ===== SETTERS =====
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
}
