package com.example.database;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {
    private final Connection connection;

    public ScheduleDAO(Connection connection) {
        this.connection = connection;
    }

    // Получить все записи с подстановкой имен преподавателя и группы
    public List<Schedule> getAll(List<Teacher> teachers, List<Group> groups) {
        List<Schedule> list = new ArrayList<>();
        String query = "SELECT * FROM schedule";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                int teacherId = rs.getInt("IDt");
                int groupId = rs.getInt("IDg");

                String teacherName = teachers.stream()
                        .filter(t -> t.getIdt() == teacherId)
                        .map(t -> t.getFullName())
                        .findFirst()
                        .orElse("");

                String groupName = groups.stream()
                        .filter(g -> g.getId() == groupId)
                        .map(g -> g.getName())
                        .findFirst()
                        .orElse("");

                Schedule s = new Schedule(
                        rs.getInt("IDr"),
                        teacherId,
                        groupId,
                        rs.getString("rSubject"),
                        rs.getString("rTime"),
                        rs.getDate("rDate") != null ? rs.getDate("rDate").toLocalDate() : null,
                        rs.getString("rRoom"),
                        teacherName,
                        groupName
                );
                list.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(Schedule s) throws SQLException {
        if (s.getTeacherId() <= 0) throw new IllegalArgumentException("ID преподавателя не задан.");
        if (s.getStudentGroupId() <= 0) throw new IllegalArgumentException("ID группы не задан.");

        String query = "INSERT INTO schedule (IDt, IDg, rSubject, rTime, rDate, rRoom) VALUES (?,?,?,?,?,?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, s.getTeacherId());
            ps.setInt(2, s.getStudentGroupId());
            ps.setString(3, s.getSubject());
            ps.setString(4, s.getTime());
            ps.setDate(5, s.getDate() != null ? Date.valueOf(s.getDate()) : null);
            ps.setString(6, s.getRoom());
            ps.executeUpdate();
        }
    }

    public void update(Schedule s) throws SQLException {
        String query = "UPDATE schedule SET IDt=?, IDg=?, rSubject=?, rTime=?, rDate=?, rRoom=? WHERE IDr=?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, s.getTeacherId());
            ps.setInt(2, s.getStudentGroupId());
            ps.setString(3, s.getSubject());
            ps.setString(4, s.getTime());
            ps.setDate(5, s.getDate() != null ? Date.valueOf(s.getDate()) : null);
            ps.setString(6, s.getRoom());
            ps.setInt(7, s.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM schedule WHERE IDr=?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
