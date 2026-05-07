package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tn.edu.esprit.tools.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HomeController implements ChildController {

    @FXML private Label statUsers;
    @FXML private Label statExercises;
    @FXML private Label statUsersCount;
    @FXML private Label statExCount;

    private MainController mainController;

    @Override
    public void setMainController(MainController m) {
        this.mainController = m;
        loadStats();
    }

    private void loadStats() {
        int users = queryCount("SELECT COUNT(*) FROM user");
        int exercises = queryCount("SELECT COUNT(*) FROM exercise");

        String usersText = users > 0 ? users + "+" : "—";
        String exText = exercises > 0 ? String.valueOf(exercises) : "—";

        if (statUsers      != null) statUsers.setText(usersText);
        if (statExercises  != null) statExercises.setText(exText);
        if (statUsersCount != null) statUsersCount.setText(usersText);
        if (statExCount    != null) statExCount.setText(exText);
    }

    private int queryCount(String sql) {
        try {
            Connection cnx = DataSource.getConnection();
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) return rs.getInt(1);
        } catch (Exception ignored) {}
        return 0;
    }

    // ── CTA buttons ──────────────────────────────────────────
    @FXML private void onStartNow()       { if (mainController != null) mainController.openPrograms(); }
    @FXML private void onExploreModules() {}
    @FXML private void onOpenFitness()    { if (mainController != null) mainController.openPrograms(); }
    @FXML private void onOpenNutrition()  { if (mainController != null) mainController.openNutrition(); }
    @FXML private void onOpenRehab()      { if (mainController != null) mainController.openInjuries(); }
    @FXML private void onOpenHealth()     { if (mainController != null) mainController.openHealthProfiles(); }
    @FXML private void onOpenGoals()      { if (mainController != null) mainController.openGoals(); }
    @FXML private void onOpenTasks()      { if (mainController != null) mainController.openTasks(); }
}
