package gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.edu.esprit.entities.NutritionPlan;
import tn.edu.esprit.services.ServiceNutritionPlan;
import java.time.LocalDate;
import java.util.List;

public class NutritionController {

    @FXML private ComboBox<String> pGoalCombo;
    @FXML private TextField pCal, pProt, pCarb, pFat;
    @FXML private TableView<NutritionPlan> tablePlans;
    @FXML private TableColumn<NutritionPlan, Integer> colId;
    @FXML private TableColumn<NutritionPlan, String> colGoal;
    @FXML private TableColumn<NutritionPlan, Integer> colCal, colProt, colCarb, colFat, colAdherence;
    @FXML private TableColumn<NutritionPlan, String> colStatus;
    @FXML private Label statusLabel;

    private ServiceNutritionPlan sp = new ServiceNutritionPlan();
    private NutritionPlan selectedPlan = null;
    private NutriMainController mainController;

    public void setMainController(NutriMainController mc) {
        this.mainController = mc;
    }

    @FXML
    public void initialize() {
        System.out.println("=== INITIALISATION CONTROLLER ===");

        pGoalCombo.getItems().clear();
        pGoalCombo.getItems().addAll("perte_poids", "prise_masse", "maintien");
        pGoalCombo.setValue("perte_poids");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colGoal.setCellValueFactory(new PropertyValueFactory<>("goalType"));
        colCal.setCellValueFactory(new PropertyValueFactory<>("dailyCalories"));
        colProt.setCellValueFactory(new PropertyValueFactory<>("proteinTarget"));
        colCarb.setCellValueFactory(new PropertyValueFactory<>("carbTarget"));
        colFat.setCellValueFactory(new PropertyValueFactory<>("fatTarget"));
        colAdherence.setCellValueFactory(new PropertyValueFactory<>("adherenceScore"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tablePlans.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            if (newVal != null) {
                selectedPlan = newVal;
                pGoalCombo.setValue(newVal.getGoalType());
                pCal.setText(String.valueOf(newVal.getDailyCalories()));
                pProt.setText(String.valueOf(newVal.getProteinTarget()));
                pCarb.setText(String.valueOf(newVal.getCarbTarget()));
                pFat.setText(String.valueOf(newVal.getFatTarget()));
                statusLabel.setText("✅ Plan sélectionné : " + newVal.getGoalType());
                statusLabel.setStyle("-fx-text-fill: #69f0ae;");
            }
        });

        refresh();
    }

    public NutritionPlan getSelectedPlan() {
        return selectedPlan;
    }

    public void updateAdherenceScore(String date) {
        if (selectedPlan != null) {
            sp.updateAdherenceScore(selectedPlan.getId(), date);
            refresh();
        }
    }

    private void refresh() {
        List<NutritionPlan> plans = sp.getAll();
        tablePlans.setItems(FXCollections.observableArrayList(plans));
        System.out.println("📊 Plans chargés: " + plans.size());

        // Calcul stats
        int activePlans = (int) plans.stream()
            .filter(p -> "actif".equals(p.getStatus()))
            .count();

        int avgAdherence = (int) plans.stream()
            .mapToInt(NutritionPlan::getAdherenceScore)
            .average()
            .orElse(0);

        int avgCalories = (int) plans.stream()
            .mapToInt(NutritionPlan::getDailyCalories)
            .average()
            .orElse(0);

        // Mise à jour stats MainController
        if (mainController != null) {
            mainController.updateStats(activePlans, avgAdherence + "%", avgCalories);
        }
    }

    @FXML
    private void handleAddPlan() {
        try {
            int cal = Integer.parseInt(pCal.getText());
            int prot = Integer.parseInt(pProt.getText());
            int carb = Integer.parseInt(pCarb.getText());
            int fat = Integer.parseInt(pFat.getText());
            String goalType = pGoalCombo.getValue();

            if (goalType == null) {
                showAlert("Erreur", "Sélectionnez un objectif");
                return;
            }

            int bmr = 1850;
            NutritionPlan plan = new NutritionPlan(
                0, 1, goalType, bmr, cal, prot, carb, fat,
                0, 0, 0, "bronze", "actif", LocalDate.now()
            );
            sp.ajouter(plan);
            refresh();
            clearPlanFields();
            showAlert("Succès", "✅ Plan ajouté !");
        } catch (NumberFormatException e) {
            showAlert("Erreur", "⚠ Valeurs invalides !");
        }
    }

    @FXML
    private void handleUpdatePlan() {
        if (selectedPlan == null) {
            showAlert("Erreur", "Sélectionnez un plan !");
            return;
        }
        try {
            selectedPlan.setDailyCalories(Integer.parseInt(pCal.getText()));
            selectedPlan.setProteinTarget(Integer.parseInt(pProt.getText()));
            selectedPlan.setCarbTarget(Integer.parseInt(pCarb.getText()));
            selectedPlan.setFatTarget(Integer.parseInt(pFat.getText()));
            selectedPlan.setGoalType(pGoalCombo.getValue());
            sp.modifier(selectedPlan);
            refresh();
            clearPlanFields();
            showAlert("Succès", "✅ Plan modifié !");
        } catch (NumberFormatException e) {
            showAlert("Erreur", "⚠ Valeurs invalides !");
        }
    }

    @FXML
    private void handleDeletePlan() {
        if (selectedPlan == null) {
            showAlert("Erreur", "Sélectionnez un plan !");
            return;
        }
        sp.supprimer(selectedPlan.getId());
        refresh();
        clearPlanFields();
        showAlert("Succès", "🗑 Plan supprimé !");
    }

    private void clearPlanFields() {
        pGoalCombo.setValue("perte_poids");
        pCal.clear();
        pProt.clear();
        pCarb.clear();
        pFat.clear();
        selectedPlan = null;
        statusLabel.setText("");
        tablePlans.getSelectionModel().clearSelection();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}