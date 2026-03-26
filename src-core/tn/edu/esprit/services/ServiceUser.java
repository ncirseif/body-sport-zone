package tn.edu.esprit.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import tn.edu.esprit.entities.User;
import tn.edu.esprit.tools.DataSource;

public class ServiceUser {

    private final Connection cnx;

    public ServiceUser() {
        this.cnx = DataSource.getInstance().getConnection();
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer findUserIdByEmail(String email) {
        if (cnx == null) return null;
        String req = "SELECT id FROM users WHERE email = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return rs.getInt("id");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public User login(String email, String password) {
        if (cnx == null) return null;
        String req = "SELECT * FROM users WHERE email = ? AND is_active = 1";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    if (storedHash.equals(hashPassword(password))) {
                        return new User(
                                rs.getInt("id"),
                                rs.getString("email"),
                                rs.getString("password"),
                                rs.getString("nom"),
                                rs.getString("prenom"),
                                rs.getString("roles"),
                                rs.getBoolean("is_active"),
                                rs.getTimestamp("created_at")
                        );
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public boolean register(User u) {
        if (cnx == null) return false;
        String req = "INSERT INTO users (email, password, nom, prenom, roles, is_active, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setString(1, u.getEmail());
            pst.setString(2, hashPassword(u.getPassword()));
            pst.setString(3, u.getNom());
            pst.setString(4, u.getPrenom());
            pst.setString(5, u.getRoles());
            pst.setBoolean(6, true);
            pst.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            pst.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean updatePassword(int userId, String newPassword) {
        if (cnx == null) return false;
        String req = "UPDATE users SET password = ? WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setString(1, hashPassword(newPassword));
            pst.setInt(2, userId);
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
