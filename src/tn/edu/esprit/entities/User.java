package tn.edu.esprit.entities;

public class User {
    private int id;
    private String email;
    private String password;
    private String nom;
    private String prenom;
    private String roles;

    public User() {}

    public User(String email, String password, String nom, String prenom, String roles) {
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.roles = roles;
    }

    public User(int id, String email, String password, String nom, String prenom, String roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.roles = roles;
    }

    public int getId()           { return id; }
    public void setId(int id)    { this.id = id; }

    public String getEmail()              { return email; }
    public void setEmail(String email)    { this.email = email; }

    public String getPassword()                { return password; }
    public void setPassword(String password)   { this.password = password; }

    public String getNom()           { return nom; }
    public void setNom(String nom)   { this.nom = nom; }

    public String getPrenom()              { return prenom; }
    public void setPrenom(String prenom)   { this.prenom = prenom; }

    public String getRoles()             { return roles; }
    public void setRoles(String roles)   { this.roles = roles; }

    @Override
    public String toString() {
        return "User{id=" + id + ", email=" + email + ", nom=" + nom + ", prenom=" + prenom + "}";
    }
}
