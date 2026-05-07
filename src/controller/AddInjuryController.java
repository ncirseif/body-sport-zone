package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.edu.esprit.entities.Injury;
import tn.edu.esprit.services.ServiceInjury;

public class AddInjuryController implements ChildController {

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @FXML private TextField tfUserId;
    @FXML private TextField tfType;
    @FXML private TextField tfLocation;
    @FXML private ComboBox<String> cbSeverity;
    @FXML private TextField tfStartDate;

    private final ServiceInjury service = new ServiceInjury();
    private Runnable onSuccess;

    @FXML
    private void initialize() {
        cbSeverity.getItems().addAll("mild", "moderate", "severe");
        cbSeverity.setValue("mild");
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
        String type = tfType.getText() == null ? "" : tfType.getText().trim();
        String location = tfLocation.getText() == null ? "" : tfLocation.getText().trim();
        String date = tfStartDate.getText() == null ? "" : tfStartDate.getText().trim();
        if (type.isEmpty() || location.isEmpty() || date.isEmpty()) return;
        try {
            int userId = Integer.parseInt(tfUserId.getText().trim());
            service.ajouter(new Injury(userId, type, location, cbSeverity.getValue(), date));
            if (onSuccess != null) onSuccess.run();
        } catch (NumberFormatException ex) {
            System.out.println("Invalid input: " + ex.getMessage());
        }
    }
}
