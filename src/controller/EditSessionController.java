package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.edu.esprit.entities.Session;
import tn.edu.esprit.services.SessionService;

public class EditSessionController implements ChildController {
    @FXML private TextField tfId;
    @FXML private TextField tfProgram;
    @FXML private TextField tfDuration;
    @FXML private Button btnSave;

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    private final SessionService service = new SessionService();
    private Runnable onSuccess;
    private Session session;

    public void setSession(Session s) {
        this.session = s;
        tfId.setText(String.valueOf(s.getId()));
        tfId.setDisable(true);
        tfProgram.setText(String.valueOf(s.getProgramId()));
        tfDuration.setText(String.valueOf(s.getDuration()));
    }

    public void setOnSuccess(Runnable onSuccess) {
        this.onSuccess = onSuccess;
    }

    @FXML
    void onCancel(ActionEvent event) {
        if (onSuccess != null) onSuccess.run();
    }

    @FXML
    void onSave(ActionEvent event) {
        if (session == null) return;
        try {
            int pid = Integer.parseInt(tfProgram.getText().trim());
            int duration = Integer.parseInt(tfDuration.getText().trim());
            Session updated = new Session(session.getId(), pid, duration);
            service.modifier(updated);
            if (onSuccess != null) onSuccess.run();
        } catch (Exception ex) { }
    }

 
}
