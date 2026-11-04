package com.example.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;

public class CuratorDAO {
        private final Connection connection;

        public CuratorDAO(Connection connection) {
            this.connection = connection;
        }
    public List<Curator> getAll() {

        List<Curator> list = new ArrayList<>();

        String query = "SELECT * FROM curator";

        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {

                list.add(new Curator(
                        rs.getInt("IDc"),
                        rs.getInt("IDco"),
                        rs.getInt("IDg"),
                        rs.getString("cSurname"),
                        rs.getString("cName"),
                        rs.getString("cPatronymic")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
