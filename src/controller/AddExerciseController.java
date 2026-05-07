package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.edu.esprit.entities.Exercise;
import tn.edu.esprit.services.ExerciseService;

public class AddExerciseController implements ChildController {

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @FXML private TextField tfName;
    @FXML private TextField tfMuscle;
    @FXML private Button btnSave;

    private final ExerciseService service = new ExerciseService();
    private Runnable onSuccess;

    @FXML
    private void onCancel(ActionEvent e) {
        if (onSuccess != null) onSuccess.run();
    }

    @FXML
    private void onSave(ActionEvent e) {
        String name = tfName.getText() == null ? "" : tfName.getText().trim();
        String mg = tfMuscle.getText() == null ? "" : tfMuscle.getText().trim();
        if (name.isEmpty() || mg.isEmpty()) return;
        service.ajouter(new Exercise(name, mg));
        if (onSuccess != null) onSuccess.run();
    }

    public void setOnSuccess(Runnable onSuccess) {
        this.onSuccess = onSuccess;
    }

 
}
