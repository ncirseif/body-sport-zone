package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import tn.edu.esprit.entities.HealthProfile;
import tn.edu.esprit.services.ServiceHealthProfile;

public class HealthProfilePanelController implements ChildController {

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @FXML private BorderPane root;
    @FXML private TableView<HealthProfile> table;
    @FXML private TableColumn<HealthProfile, Integer> colId;
    @FXML private TableColumn<HealthProfile, Integer> colUserId;
    @FXML private TableColumn<HealthProfile, Integer> colAge;
    @FXML private TableColumn<HealthProfile, Double> colHeight;
    @FXML private TableColumn<HealthProfile, Double> colWeight;
    @FXML private TableColumn<HealthProfile, Double> colBodyFat;

    private final ServiceHealthProfile service = new ServiceHealthProfile();
    private final ObservableList<HealthProfile> data = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colHeight.setCellValueFactory(new PropertyValueFactory<>("heightCm"));
        colWeight.setCellValueFactory(new PropertyValueFactory<>("weightKg"));
        colBodyFat.setCellValueFactory(new PropertyValueFactory<>("bodyFat"));
        table.setItems(data);
        loadData();
    }

    @FXML
    private void onAdd(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add_health_profile.fxml"));
        Node form = loader.load();
        AddHealthProfileController ctrl = loader.getController();
        ctrl.setOnSuccess(() -> { loadData(); root.setCenter(table); });
        root.setCenter(form);
    }

    @FXML
    private void onEdit(ActionEvent e) throws Exception {
        HealthProfile hp = table.getSelectionModel().getSelectedItem();
        if (hp == null) return;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/edit_health_profile.fxml"));
        Node form = loader.load();
        EditHealthProfileController ctrl = loader.getController();
        ctrl.setHealthProfile(hp);
        ctrl.setOnSuccess(() -> { loadData(); root.setCenter(table); });
        root.setCenter(form);
    }

    @FXML
    private void onDelete(ActionEvent e) {
        HealthProfile hp = table.getSelectionModel().getSelectedItem();
        if (hp == null) return;
        service.supprimer(hp.getId());
        loadData();
    }

    @FXML
    private void onRefresh(ActionEvent e) {
        loadData();
    }

    private void loadData() {
        data.setAll(service.getAll());
    }
}
