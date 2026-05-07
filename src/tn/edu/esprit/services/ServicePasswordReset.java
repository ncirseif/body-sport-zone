package tn.edu.esprit.services;

import tn.edu.esprit.tools.DataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class ServicePasswordReset {

    private Connection cnx;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ServicePasswordReset() {
        cnx = DataSource.getConnection();
    }

    /**
     * Create a 6-digit reset code for the given user.
     * Invalidates any previous unused codes for that user.
     * Returns the new code, or null on failure.
     */
    public String createResetCode(int userId) {
        // Invalidate old codes
        try {
            PreparedStatement del = cnx.prepareStatement(
                "UPDATE password_reset SET used = 1 WHERE user_id = ? AND used = 0");
            del.setInt(1, userId);
            del.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Could not clear old codes: " + e.getMessage());
        }

        String code = String.format("%06d", new Random().nextInt(1_000_000));
        String expires = LocalDateTime.now().plusMinutes(15).format(FMT);

        String sql = "INSERT INTO password_reset (user_id, code, expires_at, used) VALUES (?, ?, ?, 0)";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, code);
            ps.setString(3, expires);
            ps.executeUpdate();
            return code;
        } catch (SQLException e) {
            System.err.println("createResetCode error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Returns true if the code exists, is not used, and has not expired.
     */
    public boolean validateResetCode(String code) {
        String sql = "SELECT expires_at FROM password_reset WHERE code = ? AND used = 0";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String expiresAt = rs.getString("expires_at");
                LocalDateTime expires = LocalDateTime.parse(expiresAt, FMT);
                return LocalDateTime.now().isBefore(expires);
            }
        } catch (SQLException e) {
            System.err.println("validateResetCode error: " + e.getMessage());
        }
        return false;
    }

    /**
     * Returns the user_id linked to this code, or null if not found.
     */
    public Integer getUserIdByResetCode(String code) {
        String sql = "SELECT user_id FROM password_reset WHERE code = ? AND used = 0";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("user_id");
        } catch (SQLException e) {
            System.err.println("getUserIdByResetCode error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Mark a code as used after a successful password reset.
     */
    public void markAsUsed(String code) {
        String sql = "UPDATE password_reset SET used = 1 WHERE code = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, code);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("markAsUsed error: " + e.getMessage());
        }
    }
}
