package com.example.database;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {

    private final Connection connection;

    public GradeDAO(Connection connection) {
        this.connection = connection;
    }

    public double calculateAverage(int studentId, int semester, List<Grade> allGrades) {
        if (allGrades == null) return 0;
        return allGrades.stream()
                .filter(g -> g.getStudentId() == studentId && g.getSemester() == semester)
                .mapToInt(Grade::getValue)
                .average()
                .orElse(0.0);
    }


    public List<Grade> getAll() {
        List<Grade> list = new ArrayList<>();
        String sql = """
            SELECT 
                g.IDgr, g.IDt, g.IDp, g.grSubject, g.grDescription, g.grValue, g.grDate,
                g.course, g.semester,
                s.IDg AS studentGroupId,
                CONCAT(s.sSurname, ' ', s.sName, ' ', s.sPatronymic) AS studentName,
                CONCAT(t.tsurname, ' ', t.tname, ' ', t.tpatronymic) AS teacherName,
                gr.IDsp, gr.IDc, gr.IDco, gr.gName, gr.IDcurator
            FROM grades g
            JOIN students s ON g.IDp = s.IDs
            JOIN teacher t ON g.IDt = t.idt
            LEFT JOIN `group` gr ON s.IDg = gr.IDg
            ORDER BY g.grDate DESC
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LocalDate date = rs.getDate("grDate") != null
                        ? rs.getDate("grDate").toLocalDate()
                        : null;


                Group group = null;
                if (rs.getInt("studentGroupId") != 0) {
                    group = new Group(
                            rs.getInt("studentGroupId"),
                            rs.getInt("IDsp"),
                            rs.getInt("IDc"),
                            rs.getInt("IDco"),
                            rs.getString("gName"),
                            rs.getInt("IDcurator"),
                            null // дополнительное поле, если нужно
                    );
                }


                Grade grade = new Grade(
                        rs.getInt("IDgr"),
                        rs.getInt("IDt"),
                        rs.getInt("IDp"),
                        rs.getString("grSubject"),
                        rs.getString("grDescription"),
                        rs.getInt("grValue"),
                        date,
                        rs.getString("studentName"),
                        rs.getString("teacherName"),
                        rs.getInt("course"),
                        rs.getInt("semester"),
                        group
                );

                list.add(grade);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public void insert(Grade g) {
        String sql = """
            INSERT INTO grades
            (IDt, IDp, grSubject, grDescription, grValue, grDate, course, semester)
            VALUES (?,?,?,?,?,?,?,?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, g.getTeacherId());
            ps.setInt(2, g.getStudentId());
            ps.setString(3, g.getSubject());
            ps.setString(4, g.getDescription());
            ps.setInt(5, g.getValue());
            ps.setDate(6, Date.valueOf(g.getDate()));
            ps.setInt(7, g.getCourse());
            ps.setInt(8, g.getSemester());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Grade g) {
        String sql = """
            UPDATE grades
            SET IDt=?, IDp=?, grSubject=?, grDescription=?, grValue=?, grDate=?, course=?, semester=?
            WHERE IDgr=?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, g.getTeacherId());
            ps.setInt(2, g.getStudentId());
            ps.setString(3, g.getSubject());
            ps.setString(4, g.getDescription());
            ps.setInt(5, g.getValue());
            ps.setDate(6, Date.valueOf(g.getDate()));
            ps.setInt(7, g.getCourse());
            ps.setInt(8, g.getSemester());
            ps.setInt(9, g.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void delete(int id) {
        String sql = "DELETE FROM grades WHERE IDgr=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
