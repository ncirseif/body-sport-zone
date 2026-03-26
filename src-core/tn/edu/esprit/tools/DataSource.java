package tn.edu.esprit.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private Connection cnx;
    private static DataSource instance;

    private String url = "jdbc:mysql://localhost:3306/esprit";
    private String user = "root";
    private String password = "";

    private DataSource() {
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e1) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException e2) {
                    System.err.println("MySQL JDBC Driver not found. Add mysql-connector jar to the build path.");
                    return;
                }
            }
            cnx = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            System.err.println("Database connection failed: " + ex.getMessage());
        }
    }

    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }

    public Connection getConnection() {
        return cnx;
    }
}
