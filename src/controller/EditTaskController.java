package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.edu.esprit.entities.Task;
import tn.edu.esprit.services.ServiceTask;

public class EditTaskController implements ChildController {

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @FXML private TextField tfId;
    @FXML private TextField tfTitle;
    @FXML private ComboBox<String> cbStatus;

    private final ServiceTask service = new ServiceTask();
    private Runnable onSuccess;
    private Task task;

    @FXML
    private void initialize() {
        cbStatus.getItems().addAll("ongoing", "done", "finished");
    }

    public void setTask(Task t) {
        this.task = t;
        tfId.setText(String.valueOf(t.getId()));
        tfId.setDisable(true);
        tfTitle.setText(t.getTitle());
        cbStatus.setValue(t.getStatus());
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
        if (task == null) return;
        String title = tfTitle.getText() == null ? "" : tfTitle.getText().trim();
        if (title.isEmpty()) return;
        service.modifier(new Task(task.getId(), title, cbStatus.getValue()));
        if (onSuccess != null) onSuccess.run();
    }
}
