package tn.edu.esprit.services;

import tn.edu.esprit.entities.RehabTracking;
import tn.edu.esprit.tools.DataSource;
import java.sql.*;
import java.util.*;

public class ServiceRehabTracking {

    Connection cnx = DataSource.getConnection();

    public void ajouter(RehabTracking t) {
        String sql = "INSERT INTO rehab_tracking(rehab_plan_id,user_id,session_date,week_day,exercise_done,exercise_name,pain_level,difficulty_level,notes,duration_minutes,created_at) VALUES(?,?,?,?,?,?,?,?,?,?,NOW())";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, t.getRehabPlanId());
            ps.setInt(2, t.getUserId());
            ps.setString(3, t.getSessionDate());
            ps.setString(4, t.getWeekDay());
            ps.setBoolean(5, t.isExerciseDone());
            ps.setString(6, t.getExerciseName());
            ps.setInt(7, t.getPainLevel());
            ps.setInt(8, t.getDifficultyLevel());
            ps.setString(9, t.getNotes());
            ps.setInt(10, t.getDurationMinutes());
            ps.executeUpdate();
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }

    public void modifier(RehabTracking t) {
        String sql = "UPDATE rehab_tracking SET rehab_plan_id=?,user_id=?,session_date=?,week_day=?,exercise_done=?,exercise_name=?,pain_level=?,difficulty_level=?,notes=?,duration_minutes=? WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, t.getRehabPlanId());
            ps.setInt(2, t.getUserId());
            ps.setString(3, t.getSessionDate());
            ps.setString(4, t.getWeekDay());
            ps.setBoolean(5, t.isExerciseDone());
            ps.setString(6, t.getExerciseName());
            ps.setInt(7, t.getPainLevel());
            ps.setInt(8, t.getDifficultyLevel());
            ps.setString(9, t.getNotes());
            ps.setInt(10, t.getDurationMinutes());
            ps.setInt(11, t.getId());
            ps.executeUpdate();
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM rehab_tracking WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }

    public List<RehabTracking> getAll() {
        List<RehabTracking> list = new ArrayList<>();
        String sql = "SELECT * FROM rehab_tracking ORDER BY session_date DESC";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) list.add(map(rs));
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
        return list;
    }

    public List<RehabTracking> getByRehabPlanId(int planId) {
        List<RehabTracking> list = new ArrayList<>();
        String sql = "SELECT * FROM rehab_tracking WHERE rehab_plan_id=? ORDER BY session_date DESC";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, planId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
        return list;
    }

    private RehabTracking map(ResultSet rs) throws SQLException {
        return new RehabTracking(
            rs.getInt("id"),
            rs.getInt("rehab_plan_id"),
            rs.getInt("user_id"),
            rs.getString("session_date"),
            rs.getString("week_day"),
            rs.getBoolean("exercise_done"),
            rs.getString("exercise_name"),
            rs.getInt("pain_level"),
            rs.getInt("difficulty_level"),
            rs.getString("notes"),
            rs.getInt("duration_minutes"),
            rs.getString("created_at")
        );
    }
}
