package greenmindtechfx;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.edu.esprit.entities.Exercise;
import tn.edu.esprit.entities.Program;
import tn.edu.esprit.entities.User;
import tn.edu.esprit.services.ExerciseService;
import tn.edu.esprit.services.ProgramService;
import tn.edu.esprit.services.ServiceNutritionPlan;

public class HomeController implements ChildController {

    @FXML private Label welcomeLabel;
    @FXML private Label statPlans;
    @FXML private Label statPrograms;
    @FXML private Label statExercises;
    @FXML private VBox moduleAuth;
    @FXML private VBox moduleNutrition;
    @FXML private VBox moduleFitness;

    private MainController mainController;
    private User currentUser;

    @Override
    public void setMainController(MainController mc) {
        this.mainController = mc;
    }

    public void setUser(User user) {
        this.currentUser = user;
        if (user != null && welcomeLabel != null)
            welcomeLabel.setText("Welcome back, " + user.getPrenom() + " \uD83D\uDC4A");
        loadStats();
    }

    @FXML
    private void initialize() {
        playEntrance();
    }

    private void playEntrance() {
        VBox[] cards = { moduleAuth, moduleNutrition, moduleFitness };
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] == null) continue;
            cards[i].setOpacity(0);
            cards[i].setTranslateY(40);
            int delay = 100 + i * 160;
            FadeTransition ft = new FadeTransition(Duration.millis(500), cards[i]);
            ft.setToValue(1);
            ft.setDelay(Duration.millis(delay));
            ft.setInterpolator(Interpolator.EASE_OUT);
            TranslateTransition tt = new TranslateTransition(Duration.millis(500), cards[i]);
            tt.setToY(0);
            tt.setDelay(Duration.millis(delay));
            tt.setInterpolator(Interpolator.EASE_OUT);
            new ParallelTransition(ft, tt).play();
        }
    }

    private void loadStats() {
        // Nutrition plans count
        try {
            int c = new ServiceNutritionPlan().getAll().size();
            if (statPlans != null) statPlans.setText(String.valueOf(c));
        } catch (Exception e) {
            if (statPlans != null) statPlans.setText("—");
        }

        // Programs count — use getAll(new Program()) as ProgramService requires a param
        try {
            int c = new ProgramService().getAll(new Program()).size();
            if (statPrograms != null) statPrograms.setText(String.valueOf(c));
        } catch (Exception e) {
            if (statPrograms != null) statPrograms.setText("—");
        }

        // Exercises count — same pattern
        try {
            int c = new ExerciseService().getAll(new Exercise()).size();
            if (statExercises != null) statExercises.setText(String.valueOf(c));
        } catch (Exception e) {
            if (statExercises != null) statExercises.setText("—");
        }
    }

    @FXML
    private void onOpenAccount() {
        if (mainController != null) mainController.showSession(currentUser);
    }

    @FXML
    private void onOpenNutrition() {
        openModule("/gui/main.fxml", "/gui/theme.css", "Body Sport Zone — Nutrition");
    }

    @FXML
    private void onOpenFitness() {
        openModule("/view/main.fxml", "/style/theme.css", "Body Sport Zone — Fitness");
    }

    private void openModule(String fxmlPath, String cssPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root, 980, 560);
            try {
                scene.getStylesheets().add(
                    getClass().getResource(cssPath).toExternalForm());
            } catch (Exception ignored) {}
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setMinWidth(900);
            stage.setMinHeight(520);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
