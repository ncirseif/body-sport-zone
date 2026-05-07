package tn.edu.esprit.entities;

public class Goal {

    private int id;
    private int userId;
    private String title;
    private String description;
    private String category;
    private String targetDate;
    private String status;
    private String priority;
    private int progress;
    private String createdAt;
    private String notes;

    public Goal() {}

    public Goal(int userId, String title, String description, String category,
                String targetDate, String status, String priority, int progress, String notes) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.targetDate = targetDate;
        this.status = status;
        this.priority = priority;
        this.progress = progress;
        this.notes = notes;
    }

    public Goal(int id, int userId, String title, String description, String category,
                String targetDate, String status, String priority, int progress, String createdAt, String notes) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.targetDate = targetDate;
        this.status = status;
        this.priority = priority;
        this.progress = progress;
        this.createdAt = createdAt;
        this.notes = notes;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getTargetDate() { return targetDate; }
    public void setTargetDate(String targetDate) { this.targetDate = targetDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public int getProgress() { return progress; }
    public void setProgress(int progress) { this.progress = Math.max(0, Math.min(100, progress)); }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public String toString() {
        return "Goal{id=" + id + ", title=" + title + ", status=" + status + "}";
    }

    @Override
    public int hashCode() {
        return 17 * 7 + this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return this.id == ((Goal) obj).id;
    }
}
