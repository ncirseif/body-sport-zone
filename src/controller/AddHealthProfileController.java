package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.edu.esprit.entities.HealthProfile;
import tn.edu.esprit.services.ServiceHealthProfile;

public class AddHealthProfileController implements ChildController {

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @FXML private TextField tfUserId;
    @FXML private TextField tfAge;
    @FXML private TextField tfHeight;
    @FXML private TextField tfWeight;
    @FXML private TextField tfBodyFat;
    @FXML private TextField tfMedicalHistory;

    private final ServiceHealthProfile service = new ServiceHealthProfile();
    private Runnable onSuccess;

    public void setOnSuccess(Runnable onSuccess) {
        this.onSuccess = onSuccess;
    }

    @FXML
    private void onCancel(ActionEvent e) {
        if (onSuccess != null) onSuccess.run();
    }

    @FXML
    private void onSave(ActionEvent e) {
        try {
            int userId = Integer.parseInt(tfUserId.getText().trim());
            int age = Integer.parseInt(tfAge.getText().trim());
            double height = Double.parseDouble(tfHeight.getText().trim());
            double weight = Double.parseDouble(tfWeight.getText().trim());
            double bodyFat = Double.parseDouble(tfBodyFat.getText().trim());
            String medical = tfMedicalHistory.getText() == null ? "" : tfMedicalHistory.getText().trim();
            service.ajouter(new HealthProfile(userId, age, height, weight, bodyFat, medical));
            if (onSuccess != null) onSuccess.run();
        } catch (NumberFormatException ex) {
            System.out.println("Invalid number input: " + ex.getMessage());
        }
    }
}
