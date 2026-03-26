package tn.edu.esprit.services;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import tn.edu.esprit.tools.DataSource;

public class ServicePasswordReset {

    private final Connection cnx;
    private final SecureRandom random = new SecureRandom();

    public ServicePasswordReset() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    public String createResetCode(int userId) {
        if (cnx == null) return null;
        Timestamp expiresAt = new Timestamp(System.currentTimeMillis() + 3600000);
        String req = "INSERT INTO password_resets (user_id, reset_code, expires_at, used) VALUES (?, ?, ?, ?)";
        for (int attempt = 0; attempt < 10; attempt++) {
            String code = String.format("%06d", random.nextInt(1000000));
            try (PreparedStatement pst = cnx.prepareStatement(req)) {
                pst.setInt(1, userId);
                pst.setString(2, code);
                pst.setTimestamp(3, expiresAt);
                pst.setBoolean(4, false);
                pst.executeUpdate();
                return code;
            } catch (SQLException ex) {
                if ("23000".equals(ex.getSQLState())) {
                    continue;
                }
                System.out.println(ex.getMessage());
                return null;
            }
        }
        return null;
    }

    public boolean validateResetCode(String code) {
        if (cnx == null) return false;
        String req = "SELECT 1 FROM password_resets WHERE reset_code = ? AND used = 0 AND expires_at > ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setString(1, code);
            pst.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public Integer getUserIdByResetCode(String code) {
        if (cnx == null) return null;
        String req = "SELECT user_id FROM password_resets WHERE reset_code = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setString(1, code);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return rs.getInt("user_id");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public boolean markAsUsed(String code) {
        if (cnx == null) return false;
        String req = "UPDATE password_resets SET used = 1 WHERE reset_code = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setString(1, code);
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
