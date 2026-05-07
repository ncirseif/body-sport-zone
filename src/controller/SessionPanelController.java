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
import tn.edu.esprit.entities.Session;
import tn.edu.esprit.services.SessionService;

public class SessionPanelController implements ChildController {

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @FXML private BorderPane root;
    @FXML private TableView<Session> table;
    @FXML private TableColumn<Session, Integer> colId;
    @FXML private TableColumn<Session, Integer> colProgramId;
    @FXML private TableColumn<Session, Integer> colDuration;
    @FXML private Button btnAdd;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;
    @FXML private Button btnRefresh;

    private final SessionService service = new SessionService();
    private final ObservableList<Session> data = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // La configuration est faite dans le FXML
        table.setItems(data);
        loadData();
    }

    @FXML
    private void onAdd(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add_session.fxml"));
        Node form = loader.load();
        AddSessionController controller = loader.getController();
        controller.setOnSuccess(() -> {
            loadData();
            root.setCenter(table);
        });
        root.setCenter(form);
    }

    @FXML
    private void onEdit(ActionEvent e) throws Exception {
        Session sess = table.getSelectionModel().getSelectedItem();
        if (sess == null) return;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/edit_session.fxml"));
        Node form = loader.load();
        EditSessionController controller = loader.getController();
        controller.setSession(sess);
        controller.setOnSuccess(() -> {
            loadData();
            root.setCenter(table);
        });
        root.setCenter(form);
    }

    @FXML
    private void onDelete(ActionEvent e) {
        Session sess = table.getSelectionModel().getSelectedItem();
        if (sess == null) return;
        service.supprimer(sess.getId());
        loadData();
    }

    @FXML
    private void onRefresh(ActionEvent e) {
        loadData();
    }

    private void loadData() {
        data.setAll(service.getAll(new Session()));
    }
}
