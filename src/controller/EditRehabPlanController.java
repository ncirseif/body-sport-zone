package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tn.edu.esprit.entities.RehabPlan;
import tn.edu.esprit.services.ServiceRehabPlan;

public class EditRehabPlanController implements ChildController {

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @FXML private TextField tfId;
    @FXML private TextField tfInjuryId;
    @FXML private TextField tfPhase;
    @FXML private TextField tfDurationWeeks;
    @FXML private TextArea taExercises;
    @FXML private TextArea taDetailedPlan;

    private final ServiceRehabPlan service = new ServiceRehabPlan();
    private Runnable onSuccess;
    private RehabPlan rehabPlan;

    public void setRehabPlan(RehabPlan r) {
        this.rehabPlan = r;
        tfId.setText(String.valueOf(r.getId()));
        tfId.setDisable(true);
        tfInjuryId.setText(String.valueOf(r.getInjuryId()));
        tfPhase.setText(r.getPhase());
        tfDurationWeeks.setText(String.valueOf(r.getDurationWeeks()));
        taExercises.setText(r.getExercises() == null ? "" : r.getExercises());
        taDetailedPlan.setText(r.getDetailedPlan() == null ? "" : r.getDetailedPlan());
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
        if (rehabPlan == null) return;
        String phase = tfPhase.getText() == null ? "" : tfPhase.getText().trim();
        if (phase.isEmpty()) return;
        try {
            int injuryId = Integer.parseInt(tfInjuryId.getText().trim());
            int weeks = Integer.parseInt(tfDurationWeeks.getText().trim());
            RehabPlan updated = new RehabPlan(rehabPlan.getId(), injuryId, phase, weeks,
                taExercises.getText(), taDetailedPlan.getText(), rehabPlan.getCreatedAt());
            service.modifier(updated);
            if (onSuccess != null) onSuccess.run();
        } catch (NumberFormatException ex) {
            System.out.println("Invalid input: " + ex.getMessage());
        }
    }
}
