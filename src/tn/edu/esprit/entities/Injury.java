package tn.edu.esprit.entities;

public class Injury {

    private int id;
    private int userId;
    private String type;
    private String location;
    private String severity;
    private String startDate;

    public Injury() {}

    public Injury(int userId, String type, String location, String severity, String startDate) {
        this.userId = userId;
        this.type = type;
        this.location = location;
        this.severity = severity;
        this.startDate = startDate;
    }

    public Injury(int id, int userId, String type, String location, String severity, String startDate) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.location = location;
        this.severity = severity;
        this.startDate = startDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    @Override
    public String toString() {
        return type + " (" + location + ")";
    }

    @Override
    public int hashCode() {
        return 17 * 7 + this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return this.id == ((Injury) obj).id;
    }
}
