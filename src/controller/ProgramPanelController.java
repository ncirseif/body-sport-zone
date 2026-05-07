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
import tn.edu.esprit.entities.Program;
import tn.edu.esprit.services.ProgramService;

public class ProgramPanelController implements ChildController {

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @FXML private BorderPane root;
    @FXML private TableView<Program> table;
    @FXML private TableColumn<Program, Integer> colId;
    @FXML private TableColumn<Program, String> colName;
    @FXML private TableColumn<Program, String> colLevel;
    @FXML private Button btnAdd;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;
    @FXML private Button btnRefresh;

    private final ProgramService service = new ProgramService();
    private final ObservableList<Program> data = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        table.setItems(data);
        loadData();
    }

    @FXML
    private void onAdd(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add_program.fxml"));
        Node form = loader.load();
        AddProgramController controller = loader.getController();
        controller.setOnSuccess(() -> {
            loadData();
            root.setCenter(table);
        });
        root.setCenter(form);
    }

    @FXML
    private void onEdit(ActionEvent e) throws Exception {
        Program p = table.getSelectionModel().getSelectedItem();
        if (p == null) return;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/edit_program.fxml"));
        Node form = loader.load();
        EditProgramController controller = loader.getController();
        controller.setProgram(p);
        controller.setOnSuccess(() -> {
            loadData();
            root.setCenter(table);
        });
        root.setCenter(form);
    }

    @FXML
    private void onDelete(ActionEvent e) {
        Program p = table.getSelectionModel().getSelectedItem();
        if (p == null) return;
        service.supprimer(p.getId());
        loadData();
    }

    @FXML
    private void onRefresh(ActionEvent e) {
        loadData();
    }

    private void loadData() {
        data.setAll(service.getAll(new Program()));
    }
}
