package com.example.database;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class StudentGroup {
    private final SimpleIntegerProperty id = new SimpleIntegerProperty();
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleStringProperty specialty = new SimpleStringProperty();

    public StudentGroup() {}

    public StudentGroup(int id, String name, String specialty) {
        this.id.set(id);
        this.name.set(name);
        this.specialty.set(specialty);
    }

    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public SimpleIntegerProperty idProperty(){ return id; }

    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }
    public SimpleStringProperty nameProperty(){ return name; }

    public String getSpecialty() { return specialty.get(); }
    public void setSpecialty(String specialty) { this.specialty.set(specialty); }
    public SimpleStringProperty specialtyProperty(){ return specialty; }

    @Override
    public String toString() { return getName(); }
}
