package com.example.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeacherDAO {

    private final Connection connection;

    public TeacherDAO(Connection connection) {
        this.connection = connection;
    }
    public Optional<Teacher> findCuratorByGroupId(int groupId) {

        String sql = """
        SELECT *
        FROM teacher
        WHERE curatorGroupId = ?
        LIMIT 1
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, groupId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Teacher t = new Teacher();
                t.setIdt(rs.getInt("IDt"));
                t.setSurname(rs.getString("tSurname"));
                t.setName(rs.getString("tName"));
                t.setPatronymic(rs.getString("tPatronymic"));
                t.setCurator(true);
                t.setCuratorGroupId(groupId);
                return Optional.of(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }


    // Получить всех преподавателей с предметами и группами

    public List<Teacher> getAll() {
        List<Teacher> list = new ArrayList<>();
        String sql = """
                SELECT t.IDt, t.tSurname, t.tName, t.tPatronymic, t.tPhone, t.tPosition,
                       t.tBirthday, t.room, t.curatorGroupId,
                       GROUP_CONCAT(DISTINCT ts.subject_id) AS subjectIds,
                       GROUP_CONCAT(DISTINCT tg.group_id) AS groupIds
                FROM teacher t
                LEFT JOIN teacher_subject ts ON t.IDt = ts.teacher_id
                LEFT JOIN teacher_group tg ON t.IDt = tg.teacher_id
                GROUP BY t.IDt
                ORDER BY t.tSurname, t.tName
                """;

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Teacher t = new Teacher(
                        rs.getInt("IDt"),
                        rs.getString("tSurname"),
                        rs.getString("tName"),
                        rs.getString("tPatronymic"),
                        rs.getString("tPhone"),
                        rs.getString("tPosition"),
                        rs.getDate("tBirthday") != null ? rs.getDate("tBirthday").toLocalDate() : null,
                        rs.getString("room"),
                        rs.getObject("curatorGroupId") != null ? rs.getInt("curatorGroupId") : null
                );


                String subjectIdsStr = rs.getString("subjectIds");
                if (subjectIdsStr != null && !subjectIdsStr.isEmpty()) {
                    List<Integer> ids = new ArrayList<>();
                    for (String s : subjectIdsStr.split(",")) ids.add(Integer.parseInt(s));
                    t.setSelectedSubjectIds(ids);
                }


                String groupIdsStr = rs.getString("groupIds");
                if (groupIdsStr != null && !groupIdsStr.isEmpty()) {
                    List<Integer> ids = new ArrayList<>();
                    for (String s : groupIdsStr.split(",")) ids.add(Integer.parseInt(s));
                    t.setSelectedGroupIds(ids);
                }

                list.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


// Получить все группы с именами кураторов

    public List<Group> getAllGroups() {
        List<Group> list = new ArrayList<>();
        // Используем LEFT JOIN, чтобы группы без куратора тоже подтягивались
        String sql = "SELECT g.IDg, g.IDsp, g.IDc, g.IDco, g.gName, g.IDcurator, tName, tSurname, tPatronymic AS curatorName " +
                "FROM `group` g " +
                "LEFT JOIN teacher t ON g.IDcurator = t.IDt " +
                "ORDER BY g.gName";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Group g = new Group(
                        rs.getInt("IDg"),
                        rs.getInt("IDsp"),
                        rs.getInt("IDc"),
                        rs.getInt("IDco"),
                        rs.getString("gName"),
                        rs.getObject("IDcurator") != null ? rs.getInt("IDcurator") : null,
                        rs.getString("curatorName")  // теперь колонка есть
                );
                list.add(g);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }



    // Получить все предметы

    public List<Subject> getAllSubjects() {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT * FROM `subject` ORDER BY `sbName`";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Subject s = new Subject(
                        rs.getInt("IDsb"),
                        rs.getString("sbName"),
                        rs.getString("sbDescription")
                );
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    // Добавить преподавателя

    // В TeacherDAO
    public void addTeacher(Teacher teacher) throws SQLException {
        String sql = "INSERT INTO teacher (tSurname, tName, tPatronymic, tPhone, tPosition, tBirthday, room, curatorGroupId) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, teacher.getSurname());
            ps.setString(2, teacher.getName());
            ps.setString(3, teacher.getPatronymic());
            ps.setString(4, teacher.getPhone());
            ps.setString(5, teacher.getPosition());
            ps.setObject(6, teacher.getBirthday());
            ps.setString(7, teacher.getRoom());
            ps.setObject(8, teacher.getCuratorGroupId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                teacher.setIdt(rs.getInt(1));
            }
        }

        // Сохраняем связи с группами
        for (Integer groupId : teacher.getSelectedGroupIds()) {
            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO teacher_group (teacher_id, group_id) VALUES (?, ?)")) {
                ps.setInt(1, teacher.getIdt());
                ps.setInt(2, groupId);
                ps.executeUpdate();
            }
        }

        // Сохраняем связи с предметами
        for (Integer subjectId : teacher.getSelectedSubjectIds()) {
            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO teacher_subject (teacher_id, subject_id) VALUES (?, ?)")) {
                ps.setInt(1, teacher.getIdt());
                ps.setInt(2, subjectId);
                ps.executeUpdate();
            }
        }
    }


    // Обновить преподавателя

    public void updateTeacher(Teacher teacher) throws SQLException {
        String sql = "UPDATE teacher SET tSurname=?, tName=?, tPatronymic=?, tPhone=?, tPosition=?, tBirthday=?, room=?, curatorGroupId=? WHERE IDt=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, teacher.getSurname());
            ps.setString(2, teacher.getName());
            ps.setString(3, teacher.getPatronymic());
            ps.setString(4, teacher.getPhone());
            ps.setString(5, teacher.getPosition());
            ps.setObject(6, teacher.getBirthday());
            ps.setString(7, teacher.getRoom());
            ps.setObject(8, teacher.getCuratorGroupId());
            ps.setInt(9, teacher.getIdt());
            ps.executeUpdate();
        }

        // Удаляем старые связи
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM teacher_group WHERE teacher_id=?")) {
            ps.setInt(1, teacher.getIdt());
            ps.executeUpdate();
        }
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM teacher_subject WHERE teacher_id=?")) {
            ps.setInt(1, teacher.getIdt());
            ps.executeUpdate();
        }

        // Сохраняем новые связи
        for (Integer groupId : teacher.getSelectedGroupIds()) {
            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO teacher_group (teacher_id, group_id) VALUES (?, ?)")) {
                ps.setInt(1, teacher.getIdt());
                ps.setInt(2, groupId);
                ps.executeUpdate();
            }
        }

        for (Integer subjectId : teacher.getSelectedSubjectIds()) {
            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO teacher_subject (teacher_id, subject_id) VALUES (?, ?)")) {
                ps.setInt(1, teacher.getIdt());
                ps.setInt(2, subjectId);
                ps.executeUpdate();
            }
        }
    }



    // Удалить преподавателя

    public void deleteTeacher(int teacherId) {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM teacher WHERE IDt=?")) {
            ps.setInt(1, teacherId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Обновление предметов преподавателя

    public void updateSubjects(int teacherId, List<Integer> subjectIds) {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM teacher_subject WHERE teacher_id=?")) {
            ps.setInt(1, teacherId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (subjectIds == null || subjectIds.isEmpty()) return;

        String sql = "INSERT INTO teacher_subject (teacher_id, subject_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (Integer id : subjectIds) {
                ps.setInt(1, teacherId);
                ps.setInt(2, id);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Обновление ведущих групп

    public void updateTeachingGroups(int teacherId, List<Integer> groupIds) {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM teacher_group WHERE teacher_id=?")) {
            ps.setInt(1, teacherId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (groupIds == null || groupIds.isEmpty()) return;

        String sql = "INSERT INTO teacher_group (teacher_id, group_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (Integer gid : groupIds) {
                ps.setInt(1, teacherId);
                ps.setInt(2, gid);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
