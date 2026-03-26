package tn.edu.esprit.entities;

import java.sql.Timestamp;

public class User {
    private int id;
    private String email;
    private String password;
    private String nom;
    private String prenom;
    private String roles;
    private boolean isActive;
    private Timestamp createdAt;

    public User() {
    }

    public User(String email, String password, String nom, String prenom, String roles) {
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.roles = roles;
        this.isActive = true;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public User(int id, String email, String password, String nom, String prenom, String roles, boolean isActive, Timestamp createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.roles = roles;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getRoles() { return roles; }
    public void setRoles(String roles) { this.roles = roles; }
    public boolean isIsActive() { return isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
