package tn.edu.esprit.entities;

public class RehabPlan {

    private int id;
    private int injuryId;
    private String phase;
    private int durationWeeks;
    private String exercises;
    private String detailedPlan;
    private String createdAt;

    public RehabPlan() {}

    public RehabPlan(int injuryId, String phase, int durationWeeks, String exercises, String detailedPlan) {
        this.injuryId = injuryId;
        this.phase = phase;
        this.durationWeeks = durationWeeks;
        this.exercises = exercises;
        this.detailedPlan = detailedPlan;
    }

    public RehabPlan(int id, int injuryId, String phase, int durationWeeks, String exercises, String detailedPlan, String createdAt) {
        this.id = id;
        this.injuryId = injuryId;
        this.phase = phase;
        this.durationWeeks = durationWeeks;
        this.exercises = exercises;
        this.detailedPlan = detailedPlan;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getInjuryId() { return injuryId; }
    public void setInjuryId(int injuryId) { this.injuryId = injuryId; }

    public String getPhase() { return phase; }
    public void setPhase(String phase) { this.phase = phase; }

    public int getDurationWeeks() { return durationWeeks; }
    public void setDurationWeeks(int durationWeeks) { this.durationWeeks = durationWeeks; }

    public String getExercises() { return exercises; }
    public void setExercises(String exercises) { this.exercises = exercises; }

    public String getDetailedPlan() { return detailedPlan; }
    public void setDetailedPlan(String detailedPlan) { this.detailedPlan = detailedPlan; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return phase + " (" + durationWeeks + " weeks)";
    }

    @Override
    public int hashCode() {
        return 17 * 7 + this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return this.id == ((RehabPlan) obj).id;
    }
}
