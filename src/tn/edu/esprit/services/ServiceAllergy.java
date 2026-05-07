package tn.edu.esprit.services;

import tn.edu.esprit.entities.Allergy;
import tn.edu.esprit.tools.DataSource;
import java.sql.*;
import java.util.*;

public class ServiceAllergy {

    Connection cnx = DataSource.getConnection();

    public void ajouter(Allergy a) {
        String sql = "INSERT INTO allergies(user_id,name,severity) VALUES(?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, a.getUserId());
            ps.setString(2, a.getName());
            ps.setString(3, a.getSeverity());
            ps.executeUpdate();
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }

    public void modifier(Allergy a) {
        String sql = "UPDATE allergies SET user_id=?,name=?,severity=? WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, a.getUserId());
            ps.setString(2, a.getName());
            ps.setString(3, a.getSeverity());
            ps.setInt(4, a.getId());
            ps.executeUpdate();
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM allergies WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }

    public Allergy getOne(int id) {
        String sql = "SELECT * FROM allergies WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
        return null;
    }

    public List<Allergy> getAll() {
        List<Allergy> list = new ArrayList<>();
        String sql = "SELECT * FROM allergies";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) list.add(map(rs));
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
        return list;
    }

    public List<Allergy> getByUserId(int userId) {
        List<Allergy> list = new ArrayList<>();
        String sql = "SELECT * FROM allergies WHERE user_id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
        return list;
    }

    private Allergy map(ResultSet rs) throws SQLException {
        return new Allergy(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getString("name"),
            rs.getString("severity")
        );
    }
}
