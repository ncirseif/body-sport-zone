package tn.edu.esprit.entities;

public class Session {

    private int id;
    private int programId;
    private int duration;

    public Session() {}

    public Session(int programId, int duration) {
        this.programId = programId;
        this.duration = duration;
    }

    public Session(int id, int programId, int duration) {
        this.id = id;
        this.programId = programId;
        this.duration = duration;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProgramId() { return programId; }
    public void setProgramId(int programId) { this.programId = programId; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    @Override
    public String toString() {
        return "Session{" + "programId=" + programId + ", duration=" + duration + '}';
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
        Session other = (Session) obj;
        return this.id == other.id;
    }
}