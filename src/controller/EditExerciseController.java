package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.edu.esprit.entities.Exercise;
import tn.edu.esprit.services.ExerciseService;

public class EditExerciseController implements ChildController {
    @FXML private TextField tfId;
    @FXML private TextField tfName;
    @FXML private TextField tfMuscle;
    @FXML private Button btnSave;

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    private final ExerciseService service = new ExerciseService();
    private Runnable onSuccess;
    private Exercise exercise;

    public void setExercise(Exercise e) {
        this.exercise = e;
        tfId.setText(String.valueOf(e.getId()));
        tfId.setDisable(true);
        tfName.setText(e.getName());
        tfMuscle.setText(e.getMuscleGroup());
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
        if (exercise == null) return;
        String name = tfName.getText() == null ? "" : tfName.getText().trim();
        String mg = tfMuscle.getText() == null ? "" : tfMuscle.getText().trim();
        if (name.isEmpty() || mg.isEmpty()) return;
        Exercise updated = new Exercise(exercise.getId(), name, mg);
        service.modifier(updated);
        if (onSuccess != null) onSuccess.run();
    }

 
}
