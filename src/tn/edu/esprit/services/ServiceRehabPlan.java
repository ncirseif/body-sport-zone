package tn.edu.esprit.services;

import tn.edu.esprit.entities.RehabPlan;
import tn.edu.esprit.tools.DataSource;
import java.sql.*;
import java.util.*;

public class ServiceRehabPlan {

    Connection cnx = DataSource.getConnection();

    public void ajouter(RehabPlan r) {
        String sql = "INSERT INTO rehab_plans(injury_id,phase,duration_weeks,exercises,detailed_plan,created_at) VALUES(?,?,?,?,?,NOW())";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, r.getInjuryId());
            ps.setString(2, r.getPhase());
            ps.setInt(3, r.getDurationWeeks());
            ps.setString(4, r.getExercises());
            ps.setString(5, r.getDetailedPlan());
            ps.executeUpdate();
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }

    public void modifier(RehabPlan r) {
        String sql = "UPDATE rehab_plans SET injury_id=?,phase=?,duration_weeks=?,exercises=?,detailed_plan=? WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, r.getInjuryId());
            ps.setString(2, r.getPhase());
            ps.setInt(3, r.getDurationWeeks());
            ps.setString(4, r.getExercises());
            ps.setString(5, r.getDetailedPlan());
            ps.setInt(6, r.getId());
            ps.executeUpdate();
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM rehab_plans WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }

    public RehabPlan getOne(int id) {
        String sql = "SELECT * FROM rehab_plans WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
        return null;
    }

    public List<RehabPlan> getAll() {
        List<RehabPlan> list = new ArrayList<>();
        String sql = "SELECT * FROM rehab_plans ORDER BY created_at DESC";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) list.add(map(rs));
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
        return list;
    }

    public List<RehabPlan> getByInjuryId(int injuryId) {
        List<RehabPlan> list = new ArrayList<>();
        String sql = "SELECT * FROM rehab_plans WHERE injury_id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, injuryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
        return list;
    }

    private RehabPlan map(ResultSet rs) throws SQLException {
        return new RehabPlan(
            rs.getInt("id"),
            rs.getInt("injury_id"),
            rs.getString("phase"),
            rs.getInt("duration_weeks"),
            rs.getString("exercises"),
            rs.getString("detailed_plan"),
            rs.getString("created_at")
        );
    }
}
