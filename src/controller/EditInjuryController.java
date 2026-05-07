package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.edu.esprit.entities.Injury;
import tn.edu.esprit.services.ServiceInjury;

public class EditInjuryController implements ChildController {

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @FXML private TextField tfId;
    @FXML private TextField tfUserId;
    @FXML private TextField tfType;
    @FXML private TextField tfLocation;
    @FXML private ComboBox<String> cbSeverity;
    @FXML private TextField tfStartDate;

    private final ServiceInjury service = new ServiceInjury();
    private Runnable onSuccess;
    private Injury injury;

    @FXML
    private void initialize() {
        cbSeverity.getItems().addAll("mild", "moderate", "severe");
    }

    public void setInjury(Injury inj) {
        this.injury = inj;
        tfId.setText(String.valueOf(inj.getId()));
        tfId.setDisable(true);
        tfUserId.setText(String.valueOf(inj.getUserId()));
        tfType.setText(inj.getType());
        tfLocation.setText(inj.getLocation());
        cbSeverity.setValue(inj.getSeverity());
        tfStartDate.setText(inj.getStartDate() == null ? "" : inj.getStartDate());
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
        if (injury == null) return;
        String type = tfType.getText() == null ? "" : tfType.getText().trim();
        String location = tfLocation.getText() == null ? "" : tfLocation.getText().trim();
        if (type.isEmpty() || location.isEmpty()) return;
        try {
            int userId = Integer.parseInt(tfUserId.getText().trim());
            Injury updated = new Injury(injury.getId(), userId, type, location,
                cbSeverity.getValue(), tfStartDate.getText().trim());
            service.modifier(updated);
            if (onSuccess != null) onSuccess.run();
        } catch (NumberFormatException ex) {
            System.out.println("Invalid input: " + ex.getMessage());
        }
    }
}
