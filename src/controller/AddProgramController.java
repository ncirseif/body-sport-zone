package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.edu.esprit.entities.Program;
import tn.edu.esprit.services.ProgramService;

public class AddProgramController implements ChildController {

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @FXML private TextField tfName;
    @FXML private TextField tfLevel;
    @FXML private Button btnSave;

    private final ProgramService service = new ProgramService();
    private Runnable onSuccess;

    @FXML
    void onCancel(ActionEvent event) {
        if (onSuccess != null) onSuccess.run();
    }

    @FXML
    private void onSave(ActionEvent e) {
        String name = tfName.getText() == null ? "" : tfName.getText().trim();
        String level = tfLevel.getText() == null ? "" : tfLevel.getText().trim();
        if (name.isEmpty() || level.isEmpty()) return;
        service.ajouter(new Program(name, level));
        if (onSuccess != null) onSuccess.run();
    }

    public void setOnSuccess(Runnable onSuccess) {
        this.onSuccess = onSuccess;
    }


}
