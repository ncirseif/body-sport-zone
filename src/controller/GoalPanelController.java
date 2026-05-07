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
import tn.edu.esprit.entities.Goal;
import tn.edu.esprit.services.ServiceGoal;

public class GoalPanelController implements ChildController {

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @FXML private BorderPane root;
    @FXML private TableView<Goal> table;
    @FXML private TableColumn<Goal, Integer> colId;
    @FXML private TableColumn<Goal, String> colTitle;
    @FXML private TableColumn<Goal, String> colCategory;
    @FXML private TableColumn<Goal, String> colStatus;
    @FXML private TableColumn<Goal, String> colPriority;
    @FXML private TableColumn<Goal, Integer> colProgress;

    private final ServiceGoal service = new ServiceGoal();
    private final ObservableList<Goal> data = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colPriority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        colProgress.setCellValueFactory(new PropertyValueFactory<>("progress"));
        table.setItems(data);
        loadData();
    }

    @FXML
    private void onAdd(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add_goal.fxml"));
        Node form = loader.load();
        AddGoalController ctrl = loader.getController();
        ctrl.setOnSuccess(() -> { loadData(); root.setCenter(table); });
        root.setCenter(form);
    }

    @FXML
    private void onEdit(ActionEvent e) throws Exception {
        Goal g = table.getSelectionModel().getSelectedItem();
        if (g == null) return;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/edit_goal.fxml"));
        Node form = loader.load();
        EditGoalController ctrl = loader.getController();
        ctrl.setGoal(g);
        ctrl.setOnSuccess(() -> { loadData(); root.setCenter(table); });
        root.setCenter(form);
    }

    @FXML
    private void onDelete(ActionEvent e) {
        Goal g = table.getSelectionModel().getSelectedItem();
        if (g == null) return;
        service.supprimer(g.getId());
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
