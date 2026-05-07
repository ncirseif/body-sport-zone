package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tn.edu.esprit.entities.RehabPlan;
import tn.edu.esprit.services.ServiceRehabPlan;

public class AddRehabPlanController implements ChildController {

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @FXML private TextField tfInjuryId;
    @FXML private TextField tfPhase;
    @FXML private TextField tfDurationWeeks;
    @FXML private TextArea taExercises;
    @FXML private TextArea taDetailedPlan;

    private final ServiceRehabPlan service = new ServiceRehabPlan();
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
        String phase = tfPhase.getText() == null ? "" : tfPhase.getText().trim();
        String exercises = taExercises.getText() == null ? "" : taExercises.getText().trim();
        if (phase.isEmpty() || exercises.isEmpty()) return;
        try {
            int injuryId = Integer.parseInt(tfInjuryId.getText().trim());
            int weeks = Integer.parseInt(tfDurationWeeks.getText().trim());
            String plan = taDetailedPlan.getText() == null ? "" : taDetailedPlan.getText().trim();
            service.ajouter(new RehabPlan(injuryId, phase, weeks, exercises, plan));
            if (onSuccess != null) onSuccess.run();
        } catch (NumberFormatException ex) {
            System.out.println("Invalid input: " + ex.getMessage());
        }
    }
}
