package com.example.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;

public class SubjectDAO {
        private final Connection connection;

        public SubjectDAO(Connection connection) {
            this.connection = connection;
        }
    public List<Subject> getAll() {
        List<Subject> list = new ArrayList<>();
        String query = "SELECT * FROM subject";

        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                list.add(new Subject(
                        rs.getInt("IDsb"),
                        rs.getString("sbName"),
                        rs.getString("sbDescription")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(Subject s) {
        String query = "INSERT INTO subject (sbName, sbDescription) VALUES (?,?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, s.getName());
            ps.setString(2, s.getDescription());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Subject s) {
        String query = "UPDATE subject SET sbName=?, sbDescription=? WHERE IDsb=?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, s.getName());
            ps.setString(2, s.getDescription());
            ps.setInt(3, s.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM subject WHERE IDsb=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
