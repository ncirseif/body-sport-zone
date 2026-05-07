package tn.edu.esprit.services;

import tn.edu.esprit.entities.User;
import tn.edu.esprit.tools.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUser {

    private Connection cnx;

    public ServiceUser() {
        cnx = DataSource.getConnection();
    }

    /**
     * Register a new user. Returns true on success.
     */
    public boolean register(User user) {
        String sql = "INSERT INTO user (email, password, nom, prenom, roles) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getNom());
            ps.setString(4, user.getPrenom());
            ps.setString(5, user.getRoles() != null ? user.getRoles() : "[\"ROLE_USER\"]");
            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) user.setId(rs.getInt(1));
                System.out.println("User registered: " + user.getEmail());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Register error: " + e.getMessage());
        }
        return false;
    }

    /**
     * Login: returns User if credentials match, null otherwise.
     */
    public User login(String email, String password) {
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("roles")
                );
            }
        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Find user ID by email. Returns null if not found.
     */
    public Integer findUserIdByEmail(String email) {
        String sql = "SELECT id FROM user WHERE email = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (SQLException e) {
            System.err.println("findUserIdByEmail error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Update password for a user. Returns true on success.
     */
    public boolean updatePassword(int userId, String newPassword) {
        String sql = "UPDATE user SET password = ? WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, newPassword);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("updatePassword error: " + e.getMessage());
        }
        return false;
    }

    /**
     * Get all users.
     */
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM user";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(new User(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("roles")
                ));
            }
        } catch (SQLException e) {
            System.err.println("getAll users error: " + e.getMessage());
        }
        return list;
    }
}
