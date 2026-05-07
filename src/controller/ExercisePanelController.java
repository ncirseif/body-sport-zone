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
import tn.edu.esprit.entities.Exercise;
import tn.edu.esprit.services.ExerciseService;

public class ExercisePanelController implements ChildController {

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @FXML private BorderPane root;
    @FXML private TableView<Exercise> table;
    @FXML private TableColumn<Exercise, Integer> colId;
    @FXML private TableColumn<Exercise, String> colName;
    @FXML private TableColumn<Exercise, String> colMuscle;
    @FXML private Button btnAdd;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;
    @FXML private Button btnRefresh;

    private final ExerciseService service = new ExerciseService();
    private final ObservableList<Exercise> data = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colMuscle.setCellValueFactory(new PropertyValueFactory<>("muscleGroup"));
        table.setItems(data);
        loadData();
    }

    @FXML
    private void onAdd(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add_exercise.fxml"));
        Node form = loader.load();
        AddExerciseController controller = loader.getController();
        controller.setOnSuccess(() -> {
            loadData();
            root.setCenter(table);
        });
        root.setCenter(form);
    }

    @FXML
    private void onEdit(ActionEvent e) throws Exception {
        Exercise ex = table.getSelectionModel().getSelectedItem();
        if (ex == null) return;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/edit_exercise.fxml"));
        Node form = loader.load();
        EditExerciseController controller = loader.getController();
        controller.setExercise(ex);
        controller.setOnSuccess(() -> {
            loadData();
            root.setCenter(table);
        });
        root.setCenter(form);
    }

    @FXML
    private void onDelete(ActionEvent e) {
        Exercise ex = table.getSelectionModel().getSelectedItem();
        if (ex == null) return;
        service.supprimer(ex.getId());
        loadData();
    }

    @FXML
    private void onRefresh(ActionEvent e) {
        loadData();
    }

    private void loadData() {
        data.setAll(service.getAll(new Exercise()));
    }
}
