package gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.edu.esprit.entities.AdherenceHistory;
import tn.edu.esprit.services.ServiceAdherenceHistory;

public class HistoryController {

    @FXML private Label historyPlanLabel, currentScoreLabel, currentStreakLabel, currentLevelLabel;
    @FXML private TableView<AdherenceHistory> tableHistory;
    @FXML private TableColumn<AdherenceHistory, String> colHistoryDate;
    @FXML private TableColumn<AdherenceHistory, Integer> colHistoryScore, colHistoryCalories;

    private ServiceAdherenceHistory sh = new ServiceAdherenceHistory();
    private NutritionController parentController;

    public void setParentController(NutritionController controller) {
        this.parentController = controller;
    }

    public void setHistoryPlanLabel(String text) {
        historyPlanLabel.setText(text);
    }

    public void setCurrentScore(String score) {
        currentScoreLabel.setText(score);
    }

    public void setCurrentStreak(String streak) {
        currentStreakLabel.setText(streak);
    }

    public void setCurrentLevel(String level) {
        currentLevelLabel.setText(level);
    }

    @FXML
    public void initialize() {
        colHistoryDate.setCellValueFactory(new PropertyValueFactory<>("checkDate"));
        colHistoryScore.setCellValueFactory(new PropertyValueFactory<>("adherenceScore"));
        colHistoryCalories.setCellValueFactory(new PropertyValueFactory<>("caloriesConsumed"));
    }

    public void refresh() {
        if (parentController != null && parentController.getSelectedPlan() != null) {
            int planId = parentController.getSelectedPlan().getId();
            tableHistory.setItems(FXCollections.observableArrayList(sh.getByPlanId(planId)));
            System.out.println("📊 Historique chargé pour plan ID: " + planId);
        }
    }
}