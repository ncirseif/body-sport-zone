package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tn.edu.esprit.entities.RehabTracking;
import tn.edu.esprit.services.ServiceRehabTracking;

public class AddRehabTrackingController implements ChildController {

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @FXML private TextField tfPlanId;
    @FXML private TextField tfUserId;
    @FXML private TextField tfSessionDate;
    @FXML private ComboBox<String> cbWeekDay;
    @FXML private CheckBox cbExerciseDone;
    @FXML private TextField tfExerciseName;
    @FXML private TextField tfPainLevel;
    @FXML private TextField tfDifficultyLevel;
    @FXML private TextArea taNotes;
    @FXML private TextField tfDurationMinutes;

    private final ServiceRehabTracking service = new ServiceRehabTracking();
    private Runnable onSuccess;

    @FXML
    private void initialize() {
        cbWeekDay.getItems().addAll("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday");
        cbWeekDay.setValue("monday");
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
        String date = tfSessionDate.getText() == null ? "" : tfSessionDate.getText().trim();
        if (date.isEmpty()) return;
        try {
            int planId = Integer.parseInt(tfPlanId.getText().trim());
            int userId = Integer.parseInt(tfUserId.getText().trim());
            int pain = tfPainLevel.getText().isEmpty() ? 0 : Integer.parseInt(tfPainLevel.getText().trim());
            int diff = tfDifficultyLevel.getText().isEmpty() ? 0 : Integer.parseInt(tfDifficultyLevel.getText().trim());
            int dur = tfDurationMinutes.getText().isEmpty() ? 0 : Integer.parseInt(tfDurationMinutes.getText().trim());
            RehabTracking t = new RehabTracking(planId, userId, date, cbWeekDay.getValue(),
                cbExerciseDone.isSelected(), tfExerciseName.getText(),
                pain, diff, taNotes.getText(), dur);
            service.ajouter(t);
            if (onSuccess != null) onSuccess.run();
        } catch (NumberFormatException ex) {
            System.out.println("Invalid input: " + ex.getMessage());
        }
    }
}
