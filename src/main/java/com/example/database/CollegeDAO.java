package com.example.database;

import java.sql.*;

public class CollegeDAO {

    private final Connection connection;

    public CollegeDAO(Connection connection) {
        this.connection = connection;
    }


    public College getCollege() {

        String sql = """
            SELECT IDc, uName, uAddress, uPhone, uInfo
            FROM college
            LIMIT 1
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return new College(
                        rs.getInt("IDc"),
                        rs.getString("uName"),
                        rs.getString("uAddress"),
                        rs.getString("uPhone"),
                        rs.getString("uInfo")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void update(College c) {

        String sql = """
            UPDATE college
            SET uName = ?,
                uAddress = ?,
                uPhone = ?,
                uInfo = ?
            WHERE IDc = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            ps.setString(2, c.getAddress());
            ps.setString(3, c.getPhone());
            ps.setString(4, c.getInfo());
            ps.setInt(5, c.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
