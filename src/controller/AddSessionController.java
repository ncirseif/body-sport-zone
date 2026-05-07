package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.edu.esprit.entities.Session;
import tn.edu.esprit.services.SessionService;

public class AddSessionController implements ChildController {

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @FXML private TextField tfProgram;
    @FXML private TextField tfDuration;
    @FXML private Button btnSave;

    private final SessionService service = new SessionService();
    private Runnable onSuccess;

    @FXML
    void onCancel(ActionEvent event) {
        if (onSuccess != null) onSuccess.run();
    }

    @FXML
    private void onSave(ActionEvent e) {
        try {
            int pid = Integer.parseInt(tfProgram.getText().trim());
            int duration = Integer.parseInt(tfDuration.getText().trim());
            service.ajouter(new Session(pid, duration));
            if (onSuccess != null) onSuccess.run();
        } catch (Exception ex) { }
    }

    public void setOnSuccess(Runnable onSuccess) {
        this.onSuccess = onSuccess;
    }

 
}
