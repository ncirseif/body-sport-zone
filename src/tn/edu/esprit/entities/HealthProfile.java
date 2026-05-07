package tn.edu.esprit.entities;

public class HealthProfile {

    private int id;
    private int userId;
    private int age;
    private double heightCm;
    private double weightKg;
    private double bodyFat;
    private String medicalHistory;

    public HealthProfile() {}

    public HealthProfile(int userId, int age, double heightCm, double weightKg, double bodyFat, String medicalHistory) {
        this.userId = userId;
        this.age = age;
        this.heightCm = heightCm;
        this.weightKg = weightKg;
        this.bodyFat = bodyFat;
        this.medicalHistory = medicalHistory;
    }

    public HealthProfile(int id, int userId, int age, double heightCm, double weightKg, double bodyFat, String medicalHistory) {
        this.id = id;
        this.userId = userId;
        this.age = age;
        this.heightCm = heightCm;
        this.weightKg = weightKg;
        this.bodyFat = bodyFat;
        this.medicalHistory = medicalHistory;
    }

    public double getBmi() {
        if (heightCm <= 0 || weightKg <= 0) return 0;
        double h = heightCm / 100.0;
        return Math.round((weightKg / (h * h)) * 100.0) / 100.0;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public double getHeightCm() { return heightCm; }
    public void setHeightCm(double heightCm) { this.heightCm = heightCm; }

    public double getWeightKg() { return weightKg; }
    public void setWeightKg(double weightKg) { this.weightKg = weightKg; }

    public double getBodyFat() { return bodyFat; }
    public void setBodyFat(double bodyFat) { this.bodyFat = bodyFat; }

    public String getMedicalHistory() { return medicalHistory; }
    public void setMedicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; }

    @Override
    public String toString() {
        return "HealthProfile{id=" + id + ", age=" + age + ", bmi=" + getBmi() + "}";
    }

    @Override
    public int hashCode() {
        return 17 * 7 + this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return this.id == ((HealthProfile) obj).id;
    }
}
