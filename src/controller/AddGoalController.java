package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.edu.esprit.entities.Goal;
import tn.edu.esprit.services.ServiceGoal;

public class AddGoalController implements ChildController {

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

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

    @FXML
    private void initialize() {
        cbCategory.getItems().addAll("fitness", "nutrition", "health", "injury", "rehab", "general");
        cbCategory.setValue("general");
        cbStatus.getItems().addAll("pending", "in_progress", "completed", "cancelled");
        cbStatus.setValue("pending");
        cbPriority.getItems().addAll("low", "medium", "high");
        cbPriority.setValue("medium");
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
        String title = tfTitle.getText() == null ? "" : tfTitle.getText().trim();
        if (title.isEmpty()) return;
        try {
            int userId = Integer.parseInt(tfUserId.getText().trim());
            int progress = tfProgress.getText().isEmpty() ? 0 : Integer.parseInt(tfProgress.getText().trim());
            Goal g = new Goal(userId, title,
                tfDescription.getText(), cbCategory.getValue(),
                tfTargetDate.getText(), cbStatus.getValue(),
                cbPriority.getValue(), progress, tfNotes.getText());
            service.ajouter(g);
            if (onSuccess != null) onSuccess.run();
        } catch (NumberFormatException ex) {
            System.out.println("Invalid input: " + ex.getMessage());
        }
    }
}
