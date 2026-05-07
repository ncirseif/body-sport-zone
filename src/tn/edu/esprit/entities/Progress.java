package tn.edu.esprit.entities;

public class Progress {

    private int id;
    private int userId;
    private String performance;

    public Progress() {}

    public Progress(int userId, String performance) {
        this.userId = userId;
        this.performance = performance;
    }

    public Progress(int id, int userId, String performance) {
        this.id = id;
        this.userId = userId;
        this.performance = performance;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getPerformance() { return performance; }
    public void setPerformance(String performance) { this.performance = performance; }

    @Override
    public String toString() {
        return "Progress{" + "userId=" + userId + ", performance=" + performance + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Progress other = (Progress) obj;
        return this.id == other.id;
    }
}