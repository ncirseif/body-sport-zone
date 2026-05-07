package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.edu.esprit.entities.Program;
import tn.edu.esprit.services.ProgramService;

public class EditProgramController implements ChildController {

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @FXML private TextField tfId;
    @FXML private TextField tfName;
    @FXML private TextField tfLevel;
    @FXML private Button btnSave;

    private final ProgramService service = new ProgramService();
    private Runnable onSuccess;
    private Program program;

    public void setProgram(Program p) {
        this.program = p;
        tfId.setText(String.valueOf(p.getId()));
        tfId.setDisable(true);
        tfName.setText(p.getName());
        tfLevel.setText(p.getLevel());
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
        if (program == null) return;
        String name = tfName.getText() == null ? "" : tfName.getText().trim();
        String level = tfLevel.getText() == null ? "" : tfLevel.getText().trim();
        if (name.isEmpty() || level.isEmpty()) return;
        Program updated = new Program(program.getId(), name, level);
        service.modifier(updated);
        if (onSuccess != null) onSuccess.run();
    }

 
}
