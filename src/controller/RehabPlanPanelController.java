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
import tn.edu.esprit.entities.RehabPlan;
import tn.edu.esprit.services.ServiceRehabPlan;

public class RehabPlanPanelController implements ChildController {

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @FXML private BorderPane root;
    @FXML private TableView<RehabPlan> table;
    @FXML private TableColumn<RehabPlan, Integer> colId;
    @FXML private TableColumn<RehabPlan, Integer> colInjuryId;
    @FXML private TableColumn<RehabPlan, String> colPhase;
    @FXML private TableColumn<RehabPlan, Integer> colDurationWeeks;
    @FXML private TableColumn<RehabPlan, String> colCreatedAt;

    private final ServiceRehabPlan service = new ServiceRehabPlan();
    private final ObservableList<RehabPlan> data = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colInjuryId.setCellValueFactory(new PropertyValueFactory<>("injuryId"));
        colPhase.setCellValueFactory(new PropertyValueFactory<>("phase"));
        colDurationWeeks.setCellValueFactory(new PropertyValueFactory<>("durationWeeks"));
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        table.setItems(data);
        loadData();
    }

    @FXML
    private void onAdd(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add_rehab_plan.fxml"));
        Node form = loader.load();
        AddRehabPlanController ctrl = loader.getController();
        ctrl.setOnSuccess(() -> { loadData(); root.setCenter(table); });
        root.setCenter(form);
    }

    @FXML
    private void onEdit(ActionEvent e) throws Exception {
        RehabPlan r = table.getSelectionModel().getSelectedItem();
        if (r == null) return;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/edit_rehab_plan.fxml"));
        Node form = loader.load();
        EditRehabPlanController ctrl = loader.getController();
        ctrl.setRehabPlan(r);
        ctrl.setOnSuccess(() -> { loadData(); root.setCenter(table); });
        root.setCenter(form);
    }

    @FXML
    private void onDelete(ActionEvent e) {
        RehabPlan r = table.getSelectionModel().getSelectedItem();
        if (r == null) return;
        service.supprimer(r.getId());
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
