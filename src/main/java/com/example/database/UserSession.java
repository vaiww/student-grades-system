package com.example.database;

public class UserSession {

    private static UserSession instance;

    private String username;
    private String role;

    private UserSession(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public static void startSession(String username, String role) {
        instance = new UserSession(username, role);
    }

    public static UserSession getInstance() {
        return instance;
    }

    public static void clearSession() {
        instance = null;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
