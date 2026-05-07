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
import tn.edu.esprit.entities.Task;
import tn.edu.esprit.services.ServiceTask;

public class TaskPanelController implements ChildController {

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @FXML private BorderPane root;
    @FXML private TableView<Task> table;
    @FXML private TableColumn<Task, Integer> colId;
    @FXML private TableColumn<Task, String> colTitle;
    @FXML private TableColumn<Task, String> colStatus;

    private final ServiceTask service = new ServiceTask();
    private final ObservableList<Task> data = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        table.setItems(data);
        loadData();
    }

    @FXML
    private void onAdd(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add_task.fxml"));
        Node form = loader.load();
        AddTaskController ctrl = loader.getController();
        ctrl.setOnSuccess(() -> { loadData(); root.setCenter(table); });
        root.setCenter(form);
    }

    @FXML
    private void onEdit(ActionEvent e) throws Exception {
        Task t = table.getSelectionModel().getSelectedItem();
        if (t == null) return;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/edit_task.fxml"));
        Node form = loader.load();
        EditTaskController ctrl = loader.getController();
        ctrl.setTask(t);
        ctrl.setOnSuccess(() -> { loadData(); root.setCenter(table); });
        root.setCenter(form);
    }

    @FXML
    private void onDelete(ActionEvent e) {
        Task t = table.getSelectionModel().getSelectedItem();
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
