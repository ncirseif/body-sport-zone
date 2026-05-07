package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.edu.esprit.entities.Progress;
import tn.edu.esprit.services.ProgressService;

public class EditProgressController implements ChildController {
    @FXML private TextField tfId;
    @FXML private TextField tfUser;
    @FXML private TextField tfPerf;
    @FXML private Button btnSave;

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    private final ProgressService service = new ProgressService();
    private Runnable onSuccess;
    private Progress progress;

    public void setProgress(Progress p) {
        this.progress = p;
        tfId.setText(String.valueOf(p.getId()));
        tfId.setDisable(true);
        tfUser.setText(String.valueOf(p.getUserId()));
        tfPerf.setText(p.getPerformance());
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
        if (progress == null) return;
        try {
            int userId = Integer.parseInt(tfUser.getText().trim());
            String perf = tfPerf.getText() == null ? "" : tfPerf.getText().trim();
            if (perf.isEmpty()) return;
            Progress updated = new Progress(progress.getId(), userId, perf);
            service.modifier(updated);
            if (onSuccess != null) onSuccess.run();
        } catch (Exception ex) { }
    }

 
}
