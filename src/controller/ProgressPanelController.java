package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import tn.edu.esprit.entities.Progress;
import tn.edu.esprit.services.ProgressService;

public class ProgressPanelController implements ChildController {

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @FXML private BorderPane root;
    @FXML private TableView<Progress> table;
    @FXML private TableColumn<Progress, Integer> colId;
    @FXML private TableColumn<Progress, Integer> colUserId;
    @FXML private TableColumn<Progress, String> colPerformance;
    @FXML private Button btnAdd;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;
    @FXML private Button btnRefresh;

    private final ProgressService service = new ProgressService();
    private final ObservableList<Progress> data = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // La configuration est faite dans le FXML
        table.setItems(data);
        loadData();
    }

    @FXML
    private void onAdd(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add_progress.fxml"));
        Node form = loader.load();
        AddProgressController controller = loader.getController();
        controller.setOnSuccess(() -> {
            loadData();
            root.setCenter(table);
        });
        root.setCenter(form);
    }

    @FXML
    private void onEdit(ActionEvent e) throws Exception {
        Progress p = table.getSelectionModel().getSelectedItem();
        if (p == null) return;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/edit_progress.fxml"));
        Node form = loader.load();
        EditProgressController controller = loader.getController();
        controller.setProgress(p);
        controller.setOnSuccess(() -> {
            loadData();
            root.setCenter(table);
        });
        root.setCenter(form);
    }

    @FXML
    private void onDelete(ActionEvent e) {
        Progress p = table.getSelectionModel().getSelectedItem();
        if (p == null) return;
        service.supprimer(p.getId());
        loadData();
    }

    @FXML
    private void onRefresh(ActionEvent e) {
        loadData();
    }

    private void loadData() {
        data.setAll(service.getAll(new Progress()));
    }
}
