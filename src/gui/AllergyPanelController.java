package gui;

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
import tn.edu.esprit.entities.Allergy;
import tn.edu.esprit.services.ServiceAllergy;

public class AllergyPanelController {

    @FXML private BorderPane root;
    @FXML private TableView<Allergy> table;
    @FXML private TableColumn<Allergy, Integer> colId;
    @FXML private TableColumn<Allergy, Integer> colUserId;
    @FXML private TableColumn<Allergy, String> colName;
    @FXML private TableColumn<Allergy, String> colSeverity;

    private final ServiceAllergy service = new ServiceAllergy();
    private final ObservableList<Allergy> data = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSeverity.setCellValueFactory(new PropertyValueFactory<>("severity"));
        table.setItems(data);
        loadData();
    }

    @FXML
    private void onAdd(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddAllergyView.fxml"));
        Node form = loader.load();
        AddAllergyController ctrl = loader.getController();
        ctrl.setOnSuccess(() -> { loadData(); root.setCenter(table); });
        root.setCenter(form);
    }

    @FXML
    private void onEdit(ActionEvent e) throws Exception {
        Allergy a = table.getSelectionModel().getSelectedItem();
        if (a == null) return;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditAllergyView.fxml"));
        Node form = loader.load();
        EditAllergyController ctrl = loader.getController();
        ctrl.setAllergy(a);
        ctrl.setOnSuccess(() -> { loadData(); root.setCenter(table); });
        root.setCenter(form);
    }

    @FXML
    private void onDelete(ActionEvent e) {
        Allergy a = table.getSelectionModel().getSelectedItem();
        if (a == null) return;
        service.supprimer(a.getId());
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
