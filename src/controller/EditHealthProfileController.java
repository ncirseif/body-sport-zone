package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import tn.edu.esprit.entities.HealthProfile;
import tn.edu.esprit.services.ServiceHealthProfile;

public class EditHealthProfileController implements ChildController {

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @FXML private TextField tfId;
    @FXML private TextField tfUserId;
    @FXML private TextField tfAge;
    @FXML private TextField tfHeight;
    @FXML private TextField tfWeight;
    @FXML private TextField tfBodyFat;
    @FXML private TextField tfMedicalHistory;

    private final ServiceHealthProfile service = new ServiceHealthProfile();
    private Runnable onSuccess;
    private HealthProfile healthProfile;

    public void setHealthProfile(HealthProfile hp) {
        this.healthProfile = hp;
        tfId.setText(String.valueOf(hp.getId()));
        tfId.setDisable(true);
        tfUserId.setText(String.valueOf(hp.getUserId()));
        tfAge.setText(String.valueOf(hp.getAge()));
        tfHeight.setText(String.valueOf(hp.getHeightCm()));
        tfWeight.setText(String.valueOf(hp.getWeightKg()));
        tfBodyFat.setText(String.valueOf(hp.getBodyFat()));
        tfMedicalHistory.setText(hp.getMedicalHistory() == null ? "" : hp.getMedicalHistory());
    }

    public void setOnSuccess(Runnable onSuccess) {
        this.onSuccess = onSuccess;
    }

    @FXML
    private void onCancel(ActionEvent e) {
        if (onSuccess != null) onSuccess.run();
    }

    @FXML
    private void onSave(ActionEvent e) {
        if (healthProfile == null) return;
        try {
            int userId = Integer.parseInt(tfUserId.getText().trim());
            int age = Integer.parseInt(tfAge.getText().trim());
            double height = Double.parseDouble(tfHeight.getText().trim());
            double weight = Double.parseDouble(tfWeight.getText().trim());
            double bodyFat = Double.parseDouble(tfBodyFat.getText().trim());
            String medical = tfMedicalHistory.getText() == null ? "" : tfMedicalHistory.getText().trim();
            HealthProfile updated = new HealthProfile(healthProfile.getId(), userId, age, height, weight, bodyFat, medical);
            service.modifier(updated);
            if (onSuccess != null) onSuccess.run();
        } catch (NumberFormatException ex) {
            System.out.println("Invalid number input: " + ex.getMessage());
        }
    }
}
