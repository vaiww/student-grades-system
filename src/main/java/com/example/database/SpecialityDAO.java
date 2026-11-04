package com.example.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;

public class SpecialityDAO {
    private final Connection connection;

    public SpecialityDAO(Connection connection) {
        this.connection = connection;
    }
    public List<Speciality> getAll() {
        List<Speciality> list = new ArrayList<>();
        String q = "SELECT IDsp, IDc, sName FROM speciality";
        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(q)) {

            while (rs.next()) {
                Integer cid = null;
                int c = rs.getInt("IDc");
                if (!rs.wasNull()) cid = c;
                list.add(new Speciality(rs.getInt("IDsp"), cid, rs.getString("sName")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
