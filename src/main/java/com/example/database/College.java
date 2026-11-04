package com.example.database;

public class College {

    private int id;
    private String name;
    private String address;
    private String phone;
    private String info; // ← НОВОЕ ПОЛЕ

    public College(int id, String name, String address, String phone, String info) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.info = info;
    }

    // ===== GETTERS =====
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getInfo() {   // ← ВОТ ОН
        return info;
    }

    // ===== SETTERS =====
    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setInfo(String info) {   // ← И ОН
        this.info = info;
    }
}
