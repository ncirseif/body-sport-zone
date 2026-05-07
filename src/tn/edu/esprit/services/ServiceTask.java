package tn.edu.esprit.services;

import tn.edu.esprit.entities.Task;
import tn.edu.esprit.tools.DataSource;
import java.sql.*;
import java.util.*;

public class ServiceTask {

    Connection cnx = DataSource.getConnection();

    public void ajouter(Task t) {
        String sql = "INSERT INTO task(title,status) VALUES(?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, t.getTitle());
            ps.setString(2, t.getStatus());
            ps.executeUpdate();
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }

    public void modifier(Task t) {
        String sql = "UPDATE task SET title=?,status=? WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, t.getTitle());
            ps.setString(2, t.getStatus());
            ps.setInt(3, t.getId());
            ps.executeUpdate();
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM task WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }

    public Task getOne(int id) {
        String sql = "SELECT * FROM task WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
        return null;
    }

    public List<Task> getAll() {
        List<Task> list = new ArrayList<>();
        String sql = "SELECT * FROM task";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) list.add(map(rs));
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
        return list;
    }

    private Task map(ResultSet rs) throws SQLException {
        return new Task(rs.getInt("id"), rs.getString("title"), rs.getString("status"));
    }
}
