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
import tn.edu.esprit.entities.Injury;
import tn.edu.esprit.services.ServiceInjury;

public class InjuryPanelController implements ChildController {

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
    }

    @FXML private BorderPane root;
    @FXML private TableView<Injury> table;
    @FXML private TableColumn<Injury, Integer> colId;
    @FXML private TableColumn<Injury, Integer> colUserId;
    @FXML private TableColumn<Injury, String> colType;
    @FXML private TableColumn<Injury, String> colLocation;
    @FXML private TableColumn<Injury, String> colSeverity;
    @FXML private TableColumn<Injury, String> colStartDate;

    private final ServiceInjury service = new ServiceInjury();
    private final ObservableList<Injury> data = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colSeverity.setCellValueFactory(new PropertyValueFactory<>("severity"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        table.setItems(data);
        loadData();
    }

    @FXML
    private void onAdd(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add_injury.fxml"));
        Node form = loader.load();
        AddInjuryController ctrl = loader.getController();
        ctrl.setOnSuccess(() -> { loadData(); root.setCenter(table); });
        root.setCenter(form);
    }

    @FXML
    private void onEdit(ActionEvent e) throws Exception {
        Injury inj = table.getSelectionModel().getSelectedItem();
        if (inj == null) return;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/edit_injury.fxml"));
        Node form = loader.load();
        EditInjuryController ctrl = loader.getController();
        ctrl.setInjury(inj);
        ctrl.setOnSuccess(() -> { loadData(); root.setCenter(table); });
        root.setCenter(form);
    }

    @FXML
    private void onDelete(ActionEvent e) {
        Injury inj = table.getSelectionModel().getSelectedItem();
        if (inj == null) return;
        service.supprimer(inj.getId());
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
