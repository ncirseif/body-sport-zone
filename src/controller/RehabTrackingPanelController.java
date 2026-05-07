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
import tn.edu.esprit.entities.RehabTracking;
import tn.edu.esprit.services.ServiceRehabTracking;

public class RehabTrackingPanelController implements ChildController {

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @FXML private BorderPane root;
    @FXML private TableView<RehabTracking> table;
    @FXML private TableColumn<RehabTracking, Integer> colId;
    @FXML private TableColumn<RehabTracking, Integer> colPlanId;
    @FXML private TableColumn<RehabTracking, String> colDate;
    @FXML private TableColumn<RehabTracking, String> colWeekDay;
    @FXML private TableColumn<RehabTracking, Boolean> colDone;
    @FXML private TableColumn<RehabTracking, Integer> colPain;
    @FXML private TableColumn<RehabTracking, Integer> colDuration;

    private final ServiceRehabTracking service = new ServiceRehabTracking();
    private final ObservableList<RehabTracking> data = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPlanId.setCellValueFactory(new PropertyValueFactory<>("rehabPlanId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        colWeekDay.setCellValueFactory(new PropertyValueFactory<>("weekDay"));
        colDone.setCellValueFactory(new PropertyValueFactory<>("exerciseDone"));
        colPain.setCellValueFactory(new PropertyValueFactory<>("painLevel"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("durationMinutes"));
        table.setItems(data);
        loadData();
    }

    @FXML
    private void onAdd(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add_rehab_tracking.fxml"));
        Node form = loader.load();
        AddRehabTrackingController ctrl = loader.getController();
        ctrl.setOnSuccess(() -> { loadData(); root.setCenter(table); });
        root.setCenter(form);
    }

    @FXML
    private void onDelete(ActionEvent e) {
        RehabTracking t = table.getSelectionModel().getSelectedItem();
        if (t == null) return;
        service.supprimer(t.getId());
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
