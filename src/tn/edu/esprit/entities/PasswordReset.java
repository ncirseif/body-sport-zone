package tn.edu.esprit.entities;

public class PasswordReset {
    private int id;
    private int userId;
    private String code;
    private String expiresAt;
    private boolean used;

    public PasswordReset() {}

    public PasswordReset(int userId, String code, String expiresAt) {
        this.userId = userId;
        this.code = code;
        this.expiresAt = expiresAt;
        this.used = false;
    }

    public int getId()              { return id; }
    public void setId(int id)       { this.id = id; }

    public int getUserId()               { return userId; }
    public void setUserId(int userId)    { this.userId = userId; }

    public String getCode()          { return code; }
    public void setCode(String code) { this.code = code; }

    public String getExpiresAt()               { return expiresAt; }
    public void setExpiresAt(String expiresAt) { this.expiresAt = expiresAt; }

    public boolean isUsed()           { return used; }
    public void setUsed(boolean used) { this.used = used; }
}
