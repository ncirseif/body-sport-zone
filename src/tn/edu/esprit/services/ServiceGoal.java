package tn.edu.esprit.services;

import tn.edu.esprit.entities.Goal;
import tn.edu.esprit.tools.DataSource;
import java.sql.*;
import java.util.*;

public class ServiceGoal {

    Connection cnx = DataSource.getConnection();

    public void ajouter(Goal g) {
        String sql = "INSERT INTO goal(user_id,title,description,category,target_date,status,priority,progress,created_at,notes) VALUES(?,?,?,?,?,?,?,?,NOW(),?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, g.getUserId());
            ps.setString(2, g.getTitle());
            ps.setString(3, g.getDescription());
            ps.setString(4, g.getCategory());
            ps.setString(5, g.getTargetDate());
            ps.setString(6, g.getStatus());
            ps.setString(7, g.getPriority());
            ps.setInt(8, g.getProgress());
            ps.setString(9, g.getNotes());
            ps.executeUpdate();
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }

    public void modifier(Goal g) {
        String sql = "UPDATE goal SET user_id=?,title=?,description=?,category=?,target_date=?,status=?,priority=?,progress=?,notes=? WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, g.getUserId());
            ps.setString(2, g.getTitle());
            ps.setString(3, g.getDescription());
            ps.setString(4, g.getCategory());
            ps.setString(5, g.getTargetDate());
            ps.setString(6, g.getStatus());
            ps.setString(7, g.getPriority());
            ps.setInt(8, g.getProgress());
            ps.setString(9, g.getNotes());
            ps.setInt(10, g.getId());
            ps.executeUpdate();
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM goal WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }

    public Goal getOne(int id) {
        String sql = "SELECT * FROM goal WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
        return null;
    }

    public List<Goal> getAll() {
        List<Goal> list = new ArrayList<>();
        String sql = "SELECT * FROM goal ORDER BY created_at DESC";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) list.add(map(rs));
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
        return list;
    }

    public List<Goal> getByUserId(int userId) {
        List<Goal> list = new ArrayList<>();
        String sql = "SELECT * FROM goal WHERE user_id=? ORDER BY created_at DESC";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
        return list;
    }

    private Goal map(ResultSet rs) throws SQLException {
        return new Goal(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getString("category"),
            rs.getString("target_date"),
            rs.getString("status"),
            rs.getString("priority"),
            rs.getInt("progress"),
            rs.getString("created_at"),
            rs.getString("notes")
        );
    }
}
