package tn.edu.esprit.entities;

public class RehabTracking {

    private int id;
    private int rehabPlanId;
    private int userId;
    private String sessionDate;
    private String weekDay;
    private boolean exerciseDone;
    private String exerciseName;
    private int painLevel;
    private int difficultyLevel;
    private String notes;
    private int durationMinutes;
    private String createdAt;

    public RehabTracking() {}

    public RehabTracking(int rehabPlanId, int userId, String sessionDate, String weekDay,
                         boolean exerciseDone, String exerciseName, int painLevel,
                         int difficultyLevel, String notes, int durationMinutes) {
        this.rehabPlanId = rehabPlanId;
        this.userId = userId;
        this.sessionDate = sessionDate;
        this.weekDay = weekDay;
        this.exerciseDone = exerciseDone;
        this.exerciseName = exerciseName;
        this.painLevel = painLevel;
        this.difficultyLevel = difficultyLevel;
        this.notes = notes;
        this.durationMinutes = durationMinutes;
    }

    public RehabTracking(int id, int rehabPlanId, int userId, String sessionDate, String weekDay,
                         boolean exerciseDone, String exerciseName, int painLevel,
                         int difficultyLevel, String notes, int durationMinutes, String createdAt) {
        this.id = id;
        this.rehabPlanId = rehabPlanId;
        this.userId = userId;
        this.sessionDate = sessionDate;
        this.weekDay = weekDay;
        this.exerciseDone = exerciseDone;
        this.exerciseName = exerciseName;
        this.painLevel = painLevel;
        this.difficultyLevel = difficultyLevel;
        this.notes = notes;
        this.durationMinutes = durationMinutes;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getRehabPlanId() { return rehabPlanId; }
    public void setRehabPlanId(int rehabPlanId) { this.rehabPlanId = rehabPlanId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getSessionDate() { return sessionDate; }
    public void setSessionDate(String sessionDate) { this.sessionDate = sessionDate; }

    public String getWeekDay() { return weekDay; }
    public void setWeekDay(String weekDay) { this.weekDay = weekDay; }

    public boolean isExerciseDone() { return exerciseDone; }
    public void setExerciseDone(boolean exerciseDone) { this.exerciseDone = exerciseDone; }

    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }

    public int getPainLevel() { return painLevel; }
    public void setPainLevel(int painLevel) { this.painLevel = painLevel; }

    public int getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(int difficultyLevel) { this.difficultyLevel = difficultyLevel; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "RehabTracking{id=" + id + ", date=" + sessionDate + ", done=" + exerciseDone + "}";
    }

    @Override
    public int hashCode() {
        return 17 * 7 + this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return this.id == ((RehabTracking) obj).id;
    }
}
