package com.example.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GroupDAO {

    private final Connection connection;
    private final TeacherDAO teacherDAO;

    public GroupDAO(Connection connection) {
        this.connection = connection;
        this.teacherDAO = new TeacherDAO(connection);
    }
    public Group findById(int id) {
        String sql = "SELECT * FROM `group` WHERE IDg=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Group(
                        rs.getInt("IDg"),
                        rs.getInt("IDsp"),
                        rs.getInt("IDc"),
                        rs.getInt("IDco"),
                        rs.getString("gName"),
                        rs.getObject("IDcurator") != null ? rs.getInt("IDcurator") : null,
                        null
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Синхронизация куратора группы
    public void syncGroupCurator(Group group) throws SQLException {

        Optional<Teacher> curatorOpt =
                teacherDAO.findCuratorByGroupId(group.getId());

        if (curatorOpt.isPresent()) {
            Teacher t = curatorOpt.get();
            group.setCuratorId(t.getIdt());
            group.setCurator(t.getFullName());
        } else {
            group.setCuratorId(null);
            group.setCurator(null);
        }

        update(group);
    }

    // Получить все группы с куратором и специальностью

    public List<Group> getAll() {
        List<Group> list = new ArrayList<>();
        String sql = """
            SELECT g.IDg, g.IDsp, g.IDc, g.IDco, g.gName, g.IDcurator,
                   CONCAT(t.tSurname, ' ', t.tName, ' ', t.tPatronymic) AS curatorFIO,
                   sp.sName AS specName
            FROM `group` g
            LEFT JOIN teacher t ON g.IDcurator = t.IDt
            LEFT JOIN speciality sp ON g.IDsp = sp.IDsp
            ORDER BY g.gName
        """;

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
                        rs.getString("curatorFIO")
                );
                g.setSpecName(rs.getString("specName"));
                list.add(g);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insert(Group g) {
        String sql = "INSERT INTO `group` (IDsp, IDc, IDco, gName, IDcurator) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, g.getSpecId());
            ps.setInt(2, g.getCollegeId());

            if (g.getCourseId() <= 0) ps.setNull(3, Types.INTEGER);
            else ps.setInt(3, g.getCourseId());

            ps.setString(4, g.getName());

            if (g.getCuratorId() == null || g.getCuratorId() == 0)
                ps.setNull(5, Types.INTEGER);
            else
                ps.setInt(5, g.getCuratorId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Group g) {
        String sql = "UPDATE `group` SET IDsp=?, IDc=?, IDco=?, gName=?, IDcurator=? WHERE IDg=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, g.getSpecId());
            ps.setInt(2, g.getCollegeId());

            if (g.getCourseId() <= 0) ps.setNull(3, Types.INTEGER);
            else ps.setInt(3, g.getCourseId());

            ps.setString(4, g.getName());

            if (g.getCuratorId() == null || g.getCuratorId() == 0)
                ps.setNull(5, Types.INTEGER);
            else
                ps.setInt(5, g.getCuratorId());

            ps.setInt(6, g.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM `group` WHERE IDg=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
