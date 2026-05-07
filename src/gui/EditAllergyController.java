package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.edu.esprit.entities.Allergy;
import tn.edu.esprit.services.ServiceAllergy;

public class EditAllergyController {

    @FXML private TextField tfId;
    @FXML private TextField tfUserId;
    @FXML private TextField tfName;
    @FXML private ComboBox<String> cbSeverity;

    private final ServiceAllergy service = new ServiceAllergy();
    private Runnable onSuccess;
    private Allergy allergy;

    @FXML
    private void initialize() {
        cbSeverity.getItems().addAll("low", "medium", "high");
    }

    public void setAllergy(Allergy a) {
        this.allergy = a;
        tfId.setText(String.valueOf(a.getId()));
        tfId.setDisable(true);
        tfUserId.setText(String.valueOf(a.getUserId()));
        tfName.setText(a.getName());
        cbSeverity.setValue(a.getSeverity());
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
        if (allergy == null) return;
        String name = tfName.getText() == null ? "" : tfName.getText().trim();
        if (name.isEmpty()) return;
        try {
            int userId = Integer.parseInt(tfUserId.getText().trim());
            service.modifier(new Allergy(allergy.getId(), userId, name, cbSeverity.getValue()));
            if (onSuccess != null) onSuccess.run();
        } catch (NumberFormatException ex) {
            System.out.println("Invalid user ID: " + ex.getMessage());
        }
    }
}
