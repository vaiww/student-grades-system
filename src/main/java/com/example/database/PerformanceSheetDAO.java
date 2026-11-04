package com.example.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PerformanceSheetDAO {

    private final Connection connection;
    private final List<Student> students; // нужен для получения имени, группы и курса

    public PerformanceSheetDAO(Connection connection, List<Student> students) {
        this.connection = connection;
        this.students = students;
    }

    // Получение всех записей
    public List<PerformanceSheet> getAll() {
        List<PerformanceSheet> list = new ArrayList<>();
        String query = "SELECT * FROM performancesheet";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                int studentId = rs.getInt("IDs");
                Student s = students.stream()
                        .filter(student -> student.getId() == studentId)
                        .findFirst().orElse(null);

                String studentName = s != null ? s.getFullName() : "";
                String groupName = s != null ? s.getGroupName() : "";
                String specialityName = s != null ? s.getSpecialityName() : "";
                int course = s != null ? s.getCourse() : 0;

                PerformanceSheet p = new PerformanceSheet(
                        rs.getInt("IDp"),
                        studentId,
                        studentName,
                        groupName,
                        specialityName,
                        course,
                        rs.getInt("pSemester"),
                        rs.getDouble("pAverage")
                );
                list.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Вставка новой записи
    public void insert(PerformanceSheet p) {
        String query = "INSERT INTO performancesheet (IDs, pSemester, pAverage) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, p.getStudentId());
            ps.setInt(2, p.getSemester());
            ps.setDouble(3, p.getAverage());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    p.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Обновление существующей записи
    public void update(PerformanceSheet p) {
        String query = "UPDATE performancesheet SET IDs=?, pSemester=?, pAverage=? WHERE IDp=?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, p.getStudentId());
            ps.setInt(2, p.getSemester());
            ps.setDouble(3, p.getAverage());
            ps.setInt(4, p.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Удаление записи
    public void delete(int id) {
        String query = "DELETE FROM performancesheet WHERE IDp=?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
