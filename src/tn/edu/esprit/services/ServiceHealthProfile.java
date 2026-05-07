package tn.edu.esprit.services;

import tn.edu.esprit.entities.HealthProfile;
import tn.edu.esprit.tools.DataSource;
import java.sql.*;
import java.util.*;

public class ServiceHealthProfile {

    Connection cnx = DataSource.getConnection();

    public void ajouter(HealthProfile h) {
        String sql = "INSERT INTO health_profiles(user_id,age,height_cm,weight_kg,body_fat,medical_history) VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, h.getUserId());
            ps.setInt(2, h.getAge());
            ps.setDouble(3, h.getHeightCm());
            ps.setDouble(4, h.getWeightKg());
            ps.setDouble(5, h.getBodyFat());
            ps.setString(6, h.getMedicalHistory());
            ps.executeUpdate();
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }

    public void modifier(HealthProfile h) {
        String sql = "UPDATE health_profiles SET user_id=?,age=?,height_cm=?,weight_kg=?,body_fat=?,medical_history=? WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, h.getUserId());
            ps.setInt(2, h.getAge());
            ps.setDouble(3, h.getHeightCm());
            ps.setDouble(4, h.getWeightKg());
            ps.setDouble(5, h.getBodyFat());
            ps.setString(6, h.getMedicalHistory());
            ps.setInt(7, h.getId());
            ps.executeUpdate();
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM health_profiles WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }

    public HealthProfile getOne(int id) {
        String sql = "SELECT * FROM health_profiles WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
        return null;
    }

    public List<HealthProfile> getAll() {
        List<HealthProfile> list = new ArrayList<>();
        String sql = "SELECT * FROM health_profiles";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) list.add(map(rs));
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
        return list;
    }

    public List<HealthProfile> getByUserId(int userId) {
        List<HealthProfile> list = new ArrayList<>();
        String sql = "SELECT * FROM health_profiles WHERE user_id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
        return list;
    }

    private HealthProfile map(ResultSet rs) throws SQLException {
        return new HealthProfile(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getInt("age"),
            rs.getDouble("height_cm"),
            rs.getDouble("weight_kg"),
            rs.getDouble("body_fat"),
            rs.getString("medical_history")
        );
    }
}
