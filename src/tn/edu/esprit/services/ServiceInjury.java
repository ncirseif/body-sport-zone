package tn.edu.esprit.services;

import tn.edu.esprit.entities.Injury;
import tn.edu.esprit.tools.DataSource;
import java.sql.*;
import java.util.*;

public class ServiceInjury {

    Connection cnx = DataSource.getConnection();

    public void ajouter(Injury inj) {
        String sql = "INSERT INTO injuries(user_id,type,location,severity,start_date) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, inj.getUserId());
            ps.setString(2, inj.getType());
            ps.setString(3, inj.getLocation());
            ps.setString(4, inj.getSeverity());
            ps.setString(5, inj.getStartDate());
            ps.executeUpdate();
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }

    public void modifier(Injury inj) {
        String sql = "UPDATE injuries SET user_id=?,type=?,location=?,severity=?,start_date=? WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, inj.getUserId());
            ps.setString(2, inj.getType());
            ps.setString(3, inj.getLocation());
            ps.setString(4, inj.getSeverity());
            ps.setString(5, inj.getStartDate());
            ps.setInt(6, inj.getId());
            ps.executeUpdate();
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM injuries WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }

    public Injury getOne(int id) {
        String sql = "SELECT * FROM injuries WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
        return null;
    }

    public List<Injury> getAll() {
        List<Injury> list = new ArrayList<>();
        String sql = "SELECT * FROM injuries";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) list.add(map(rs));
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
        return list;
    }

    public List<Injury> getByUserId(int userId) {
        List<Injury> list = new ArrayList<>();
        String sql = "SELECT * FROM injuries WHERE user_id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
        return list;
    }

    private Injury map(ResultSet rs) throws SQLException {
        return new Injury(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getString("type"),
            rs.getString("location"),
            rs.getString("severity"),
            rs.getString("start_date")
        );
    }
}
