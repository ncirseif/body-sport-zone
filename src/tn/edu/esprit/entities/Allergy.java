package tn.edu.esprit.entities;

public class Allergy {

    public static final String SEVERITY_LOW = "low";
    public static final String SEVERITY_MEDIUM = "medium";
    public static final String SEVERITY_HIGH = "high";

    private int id;
    private int userId;
    private String name;
    private String severity;

    public Allergy() {}

    public Allergy(int userId, String name, String severity) {
        this.userId = userId;
        this.name = name;
        this.severity = severity;
    }

    public Allergy(int id, int userId, String name, String severity) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.severity = severity;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getSeverityLabel() {
        if (SEVERITY_LOW.equals(severity)) return "Low";
        if (SEVERITY_MEDIUM.equals(severity)) return "Medium";
        if (SEVERITY_HIGH.equals(severity)) return "High";
        return "Unknown";
    }

    @Override
    public String toString() {
        return "Allergy{id=" + id + ", name=" + name + ", severity=" + severity + "}";
    }

    @Override
    public int hashCode() {
        return 17 * 7 + this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return this.id == ((Allergy) obj).id;
    }
}
