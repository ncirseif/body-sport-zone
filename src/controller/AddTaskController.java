package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.edu.esprit.entities.Task;
import tn.edu.esprit.services.ServiceTask;

public class AddTaskController implements ChildController {

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @FXML private TextField tfTitle;
    @FXML private ComboBox<String> cbStatus;

    private final ServiceTask service = new ServiceTask();
    private Runnable onSuccess;

    @FXML
    private void initialize() {
        cbStatus.getItems().addAll("ongoing", "done", "finished");
        cbStatus.setValue("ongoing");
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
        service.ajouter(new Task(title, cbStatus.getValue()));
        if (onSuccess != null) onSuccess.run();
    }
}
