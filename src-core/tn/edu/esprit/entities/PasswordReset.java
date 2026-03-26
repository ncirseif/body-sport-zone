package tn.edu.esprit.entities;

import java.sql.Timestamp;

public class PasswordReset {
    private int id;
    private int userId;
    private String resetCode;
    private Timestamp expiresAt;
    private boolean used;

    public PasswordReset() {
    }

    public PasswordReset(int userId, String resetCode, Timestamp expiresAt) {
        this.userId = userId;
        this.resetCode = resetCode;
        this.expiresAt = expiresAt;
        this.used = false;
    }

    public PasswordReset(int id, int userId, String resetCode, Timestamp expiresAt, boolean used) {
        this.id = id;
        this.userId = userId;
        this.resetCode = resetCode;
        this.expiresAt = expiresAt;
        this.used = used;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getResetCode() {
        return resetCode;
    }

    public void setResetCode(String resetCode) {
        this.resetCode = resetCode;
    }

    public Timestamp getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Timestamp expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
