package com.example.database;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private final Connection connection;

    public StudentDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Student> getAll() {
        List<Student> list = new ArrayList<>();

        String query = """
            SELECT s.IDs, s.IDg, s.IDsp, s.IDc, s.sSurname, s.sName, s.sPatronymic, s.sPhone, s.sBirthday,
                   g.gName AS group_name,
                   sp.sName AS speciality_name,
                   s.course
            FROM students s
            LEFT JOIN `group` g ON s.IDg = g.IDg
            LEFT JOIN speciality sp ON s.IDsp = sp.IDsp
        """;

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Date d = rs.getDate("sBirthday");
                LocalDate birthday = d != null ? d.toLocalDate() : null;

                Student s = new Student(
                        rs.getInt("IDs"),
                        rs.getInt("IDg"),
                        rs.getInt("IDsp"),
                        rs.getInt("IDc"),
                        rs.getString("sSurname"),
                        rs.getString("sName"),
                        rs.getString("sPatronymic"),
                        rs.getString("sPhone"),
                        birthday,
                        rs.getString("group_name"),
                        rs.getString("speciality_name"),
                        rs.getInt("course")
                );

                list.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insert(Student s) {
        String sql = """
            INSERT INTO students
            (IDg, IDsp, IDc, sSurname, sName, sPatronymic, sPhone, sBirthday, course)
            VALUES (?,?,?,?,?,?,?,?,?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, s.getGroupId());
            ps.setInt(2, s.getSpecialityId());
            ps.setInt(3, s.getCollegeId());
            ps.setString(4, s.getSurname());
            ps.setString(5, s.getName());
            ps.setString(6, s.getPatronymic());
            ps.setString(7, s.getPhone());

            if (s.getBirthday() != null)
                ps.setDate(8, Date.valueOf(s.getBirthday()));
            else
                ps.setNull(8, Types.DATE);

            ps.setInt(9, s.getCourse());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Student s) {
        String sql = """
            UPDATE students
            SET IDg=?, IDsp=?, IDc=?, sSurname=?, sName=?, sPatronymic=?, sPhone=?, sBirthday=?, course=?
            WHERE IDs=?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, s.getGroupId());
            ps.setInt(2, s.getSpecialityId());
            ps.setInt(3, s.getCollegeId());
            ps.setString(4, s.getSurname());
            ps.setString(5, s.getName());
            ps.setString(6, s.getPatronymic());
            ps.setString(7, s.getPhone());

            if (s.getBirthday() != null)
                ps.setDate(8, Date.valueOf(s.getBirthday()));
            else
                ps.setNull(8, Types.DATE);

            ps.setInt(9, s.getCourse());
            ps.setInt(10, s.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (PreparedStatement ps =
                     connection.prepareStatement("DELETE FROM students WHERE IDs=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
