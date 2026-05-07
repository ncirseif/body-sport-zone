package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.edu.esprit.entities.Goal;
import tn.edu.esprit.services.ServiceGoal;

public class EditGoalController implements ChildController {

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @FXML private TextField tfId;
    @FXML private TextField tfUserId;
    @FXML private TextField tfTitle;
    @FXML private TextField tfDescription;
    @FXML private ComboBox<String> cbCategory;
    @FXML private TextField tfTargetDate;
    @FXML private ComboBox<String> cbStatus;
    @FXML private ComboBox<String> cbPriority;
    @FXML private TextField tfProgress;
    @FXML private TextField tfNotes;

    private final ServiceGoal service = new ServiceGoal();
    private Runnable onSuccess;
    private Goal goal;

    @FXML
    private void initialize() {
        cbCategory.getItems().addAll("fitness", "nutrition", "health", "injury", "rehab", "general");
        cbStatus.getItems().addAll("pending", "in_progress", "completed", "cancelled");
        cbPriority.getItems().addAll("low", "medium", "high");
    }

    public void setGoal(Goal g) {
        this.goal = g;
        tfId.setText(String.valueOf(g.getId()));
        tfId.setDisable(true);
        tfUserId.setText(String.valueOf(g.getUserId()));
        tfTitle.setText(g.getTitle());
        tfDescription.setText(g.getDescription() == null ? "" : g.getDescription());
        cbCategory.setValue(g.getCategory());
        tfTargetDate.setText(g.getTargetDate() == null ? "" : g.getTargetDate());
        cbStatus.setValue(g.getStatus());
        cbPriority.setValue(g.getPriority());
        tfProgress.setText(String.valueOf(g.getProgress()));
        tfNotes.setText(g.getNotes() == null ? "" : g.getNotes());
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
        if (goal == null) return;
        String title = tfTitle.getText() == null ? "" : tfTitle.getText().trim();
        if (title.isEmpty()) return;
        try {
            int userId = Integer.parseInt(tfUserId.getText().trim());
            int progress = tfProgress.getText().isEmpty() ? 0 : Integer.parseInt(tfProgress.getText().trim());
            Goal updated = new Goal(goal.getId(), userId, title,
                tfDescription.getText(), cbCategory.getValue(),
                tfTargetDate.getText(), cbStatus.getValue(),
                cbPriority.getValue(), progress, goal.getCreatedAt(), tfNotes.getText());
            service.modifier(updated);
            if (onSuccess != null) onSuccess.run();
        } catch (NumberFormatException ex) {
            System.out.println("Invalid input: " + ex.getMessage());
        }
    }
}
