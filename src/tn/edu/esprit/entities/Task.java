package tn.edu.esprit.entities;

public class Task {

    private int id;
    private String title;
    private String status;

    public Task() {}

    public Task(String title, String status) {
        this.title = title;
        this.status = status;
    }

    public Task(int id, String title, String status) {
        this.id = id;
        this.title = title;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public int hashCode() {
        return 17 * 7 + this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return this.id == ((Task) obj).id;
    }
}
