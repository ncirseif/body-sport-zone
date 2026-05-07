package gui;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.util.Duration;
import java.io.IOException;

public class NutriMainController {

    @FXML private StackPane contentHost;
    @FXML private StackPane animOverlay;
    @FXML private VBox animCard;
    @FXML private Label animTitle, animRunner, animTarget, animSub;
    @FXML private Label statsActivePlans, statsAdherence, statsAvgCalories;
    @FXML private Region bgRegion;
    @FXML private VBox card1, card2, card3;
    @FXML private VBox brandPanel;
    @FXML private HBox logoBox;
    @FXML private Label logoIcon, logoText, brandTagline, forgeTagline;
    @FXML private Region brandSeparator;

    private NutritionController nutritionController;

    @FXML
    public void initialize() {
        startBackgroundPan();
        playStartupAnimation();
        startLogoPulse();
        showNutrition();
    }

    // ══════════════════════════════
    //  MISE À JOUR STATS
    // ══════════════════════════════
    public void updateStats(int activePlans, String adherence, int avgCalories) {
        if (statsActivePlans != null)
            statsActivePlans.setText(String.valueOf(activePlans));
        if (statsAdherence != null)
            statsAdherence.setText(adherence);
        if (statsAvgCalories != null)
            statsAvgCalories.setText(String.valueOf(avgCalories));
    }

    // ══════════════════════════════
    //  NAVIGATION
    // ══════════════════════════════
    @FXML
    private void showNutrition() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("nutrition.fxml"));
            Node view = loader.load();
            nutritionController = loader.getController();
            nutritionController.setMainController(this);
            swapTo(view);
        } catch (IOException e) {
            e.printStackTrace();
            showError("Erreur", "Impossible de charger nutrition.fxml");
        }
    }

    @FXML
    private void showMeals() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("MealsView.fxml"));
            Node view = loader.load();
            MealsController mealsController = loader.getController();
            mealsController.setParentController(nutritionController);

            if (nutritionController != null
                    && nutritionController.getSelectedPlan() != null) {
                mealsController.setSelectedPlanLabel(
                    "✅ Plan sélectionné : "
                    + nutritionController.getSelectedPlan().getGoalType()
                    + " (" + nutritionController.getSelectedPlan().getDailyCalories()
                    + " kcal)");
            } else {
                mealsController.setSelectedPlanLabel(
                    "⚠️ Sélectionnez d'abord un plan dans l'onglet Plans");
            }

            swapTo(view);
        } catch (IOException e) {
            e.printStackTrace();
            showError("Erreur", "Impossible de charger MealsView.fxml");
        }
    }

    @FXML
    private void showAllergies() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("AllergyView.fxml"));
            Node view = loader.load();
            swapTo(view);
        } catch (IOException e) {
            e.printStackTrace();
            showError("Erreur", "Impossible de charger AllergyView.fxml");
        }
    }

    @FXML
    private void showHistory() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("HistoryView.fxml"));
            Node view = loader.load();
            HistoryController historyController = loader.getController();
            historyController.setParentController(nutritionController);

            if (nutritionController != null
                    && nutritionController.getSelectedPlan() != null) {
                historyController.setHistoryPlanLabel(
                    "📊 Historique du plan : "
                    + nutritionController.getSelectedPlan().getGoalType());
                historyController.setCurrentScore(
                    nutritionController.getSelectedPlan().getAdherenceScore() + "%");
                historyController.setCurrentStreak(
                    nutritionController.getSelectedPlan().getStreakDays() + " jours");
                historyController.setCurrentLevel(
                    nutritionController.getSelectedPlan().getLevel());
                historyController.refresh();
            }

            swapTo(view);
        } catch (IOException e) {
            e.printStackTrace();
            showError("Erreur", "Impossible de charger HistoryView.fxml");
        }
    }

    // ══════════════════════════════
    //  ANIMATIONS
    // ══════════════════════════════
    private void startBackgroundPan() {
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(bgRegion.scaleXProperty(), 1.08, Interpolator.EASE_BOTH),
                new KeyValue(bgRegion.scaleYProperty(), 1.08, Interpolator.EASE_BOTH),
                new KeyValue(bgRegion.translateXProperty(), 0, Interpolator.EASE_BOTH)
            ),
            new KeyFrame(Duration.millis(14000),
                new KeyValue(bgRegion.translateXProperty(), -28, Interpolator.EASE_BOTH),
                new KeyValue(bgRegion.scaleXProperty(), 1.13, Interpolator.EASE_BOTH),
                new KeyValue(bgRegion.scaleYProperty(), 1.13, Interpolator.EASE_BOTH)
            ),
            new KeyFrame(Duration.millis(28000),
                new KeyValue(bgRegion.scaleXProperty(), 1.08, Interpolator.EASE_BOTH),
                new KeyValue(bgRegion.scaleYProperty(), 1.08, Interpolator.EASE_BOTH),
                new KeyValue(bgRegion.translateXProperty(), 0, Interpolator.EASE_BOTH)
            )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void playStartupAnimation() {
        Node[] elements = {
            logoBox, brandTagline, brandSeparator,
            card1, card2, card3, forgeTagline
        };

        for (int i = 0; i < elements.length; i++) {
            Node element = elements[i];
            element.setOpacity(0);
            element.setTranslateY(-20);

            FadeTransition fade = new FadeTransition(Duration.millis(500), element);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.setInterpolator(Interpolator.EASE_OUT);
            fade.setDelay(Duration.millis(150 + i * 120));

            TranslateTransition translate = new TranslateTransition(Duration.millis(500), element);
            translate.setFromY(-20);
            translate.setToY(0);
            translate.setInterpolator(Interpolator.EASE_OUT);
            translate.setDelay(Duration.millis(150 + i * 120));

            new ParallelTransition(fade, translate).play();
        }
    }

    private void startLogoPulse() {
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(logoIcon.styleProperty(),
                    "-fx-effect: dropshadow(gaussian, rgba(229,57,53,0), 0, 0.2, 0, 0);")),
            new KeyFrame(Duration.millis(1500),
                new KeyValue(logoIcon.styleProperty(),
                    "-fx-effect: dropshadow(gaussian, rgba(229,57,53,0.6), 18, 0.2, 0, 0);")),
            new KeyFrame(Duration.millis(3000),
                new KeyValue(logoIcon.styleProperty(),
                    "-fx-effect: dropshadow(gaussian, rgba(229,57,53,0), 0, 0.2, 0, 0);"))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        ScaleTransition scale = new ScaleTransition(Duration.millis(3000), logoText);
        scale.setFromX(1.0); scale.setToX(1.02);
        scale.setFromY(1.0); scale.setToY(1.02);
        scale.setAutoReverse(true);
        scale.setCycleCount(ScaleTransition.INDEFINITE);
        scale.setInterpolator(Interpolator.EASE_BOTH);
        scale.play();
    }

    // ══════════════════════════════
    //  SWAP ANIMATION
    // ══════════════════════════════
    private void swapTo(Node newNode) {
        if (contentHost.getChildren().isEmpty()) {
            contentHost.getChildren().add(newNode);
            animateFormIn(newNode);
            return;
        }

        Node oldNode = contentHost.getChildren().get(0);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(150), oldNode);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(150), oldNode);
        scaleOut.setFromX(1); scaleOut.setToX(0.96);
        scaleOut.setFromY(1); scaleOut.setToY(0.96);

        ParallelTransition outTransition = new ParallelTransition(fadeOut, scaleOut);
        outTransition.setOnFinished(e -> {
            contentHost.getChildren().setAll(newNode);
            animateFormIn(newNode);
        });
        outTransition.play();
    }

    private void animateFormIn(Node node) {
        StackPane.setAlignment(node, Pos.TOP_CENTER);
        node.setOpacity(0);
        node.setTranslateY(0);
        node.setScaleX(1);
        node.setScaleY(1);

        FadeTransition fade = new FadeTransition(Duration.millis(400), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setInterpolator(Interpolator.EASE_OUT);

        new ParallelTransition(fade).play();
    }

    // ══════════════════════════════
    //  ERROR
    // ══════════════════════════════
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}