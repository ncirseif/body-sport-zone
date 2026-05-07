package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.edu.esprit.entities.Progress;
import tn.edu.esprit.services.ProgressService;

public class AddProgressController implements ChildController {

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @FXML private TextField tfUser;
    @FXML private TextField tfPerf;
    @FXML private Button btnSave;

    private final ProgressService service = new ProgressService();
    private Runnable onSuccess;

    @FXML
    void onCancel(ActionEvent event) {
        if (onSuccess != null) onSuccess.run();
    }

    @FXML
    private void onSave(ActionEvent e) {
        try {
            int userId = Integer.parseInt(tfUser.getText().trim());
            String perf = tfPerf.getText() == null ? "" : tfPerf.getText().trim();
            if (perf.isEmpty()) return;
            service.ajouter(new Progress(userId, perf));
            if (onSuccess != null) onSuccess.run();
        } catch (Exception ex) { }
    }

    public void setOnSuccess(Runnable onSuccess) {
        this.onSuccess = onSuccess;
    }

 
}
