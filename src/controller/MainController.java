package controller;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;

public class MainController {

    

    @FXML private StackPane contentHost;
    @FXML private StackPane loginAnimOverlay;
    @FXML private Label pushRunner;
    @FXML private Label pushTarget;
    @FXML private VBox brandPanel;
    @FXML private HBox logoBox;
    @FXML private Label logoIcon;
    @FXML private Label logoText;
    @FXML private Label brandTagline;
    @FXML private Region brandSeparator;
    @FXML private VBox card1, card2, card3, card4;
    @FXML private Label forgeTagline;
    @FXML private Region bgRegion;
    @FXML private VBox loginAnimCard;
    @FXML private Label loginAnimTitle;
    @FXML private Label loginAnimSub;

    @FXML
    public void initialize() {
        startBackgroundPan();
        playStartupAnimation();
        startLogoPulse();
        openHome(null);
    }

    private void startBackgroundPan() {
        Timeline kenBurns = new Timeline();
        
        KeyValue kvScaleX1 = new KeyValue(bgRegion.scaleXProperty(), 1.08, Interpolator.EASE_BOTH);
        KeyValue kvScaleY1 = new KeyValue(bgRegion.scaleYProperty(), 1.08, Interpolator.EASE_BOTH);
        KeyValue kvTransX1 = new KeyValue(bgRegion.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf1 = new KeyFrame(Duration.ZERO, kvScaleX1, kvScaleY1, kvTransX1);

        KeyValue kvScaleX2 = new KeyValue(bgRegion.scaleXProperty(), 1.13, Interpolator.EASE_BOTH);
        KeyValue kvScaleY2 = new KeyValue(bgRegion.scaleYProperty(), 1.13, Interpolator.EASE_BOTH);
        KeyValue kvTransX2 = new KeyValue(bgRegion.translateXProperty(), -28, Interpolator.EASE_BOTH);
        KeyFrame kf2 = new KeyFrame(Duration.seconds(14), kvScaleX2, kvScaleY2, kvTransX2);

        kenBurns.getKeyFrames().addAll(kf1, kf2);
        kenBurns.setCycleCount(Animation.INDEFINITE);
        kenBurns.setAutoReverse(true);
        kenBurns.play();
    }

    private void playStartupAnimation() {
        Node[] elements = { logoBox, brandTagline, brandSeparator, card1, card2, card3, card4, forgeTagline };
        for (int i = 0; i < elements.length; i++) {
            Node node = elements[i];
            node.setOpacity(0);
            node.setTranslateY(-20);

            FadeTransition ft = new FadeTransition(Duration.millis(500), node);
            ft.setToValue(1);
            ft.setInterpolator(Interpolator.EASE_OUT);
            ft.setDelay(Duration.millis(150 + (i * 120)));

            TranslateTransition tt = new TranslateTransition(Duration.millis(500), node);
            tt.setToY(0);
            tt.setInterpolator(Interpolator.EASE_OUT);
            tt.setDelay(Duration.millis(150 + (i * 120)));

            ParallelTransition pt = new ParallelTransition(ft, tt);
            pt.play();
        }
    }

    private void startLogoPulse() {
        DropShadow ds = new DropShadow(0, Color.web("#E53935", 0.6));
        ds.setSpread(0.2);
        logoIcon.setEffect(ds);

        Timeline pulse = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(ds.radiusProperty(), 0, Interpolator.EASE_BOTH)),
            new KeyFrame(Duration.millis(1500), new KeyValue(ds.radiusProperty(), 18, Interpolator.EASE_BOTH)),
            new KeyFrame(Duration.millis(3000), new KeyValue(ds.radiusProperty(), 0, Interpolator.EASE_BOTH))
        );
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.play();

        ScaleTransition st = new ScaleTransition(Duration.millis(3000), logoText);
        st.setFromX(1.0); st.setFromY(1.0);
        st.setToX(1.02); st.setToY(1.02);
        st.setCycleCount(Animation.INDEFINITE);
        st.setAutoReverse(true);
        st.setInterpolator(Interpolator.EASE_BOTH);
        st.play();
    }

    @FXML
    private void openHome(MouseEvent e) {
        swapTo("/view/home.fxml");
    }

    @FXML
    private void openPrograms(MouseEvent e) {
        swapTo("/view/program_panel.fxml");
    }

    @FXML
    private void openExercises(MouseEvent e) {
        swapTo("/view/exercise_panel.fxml");
    }

    @FXML
    private void openSessions(MouseEvent e) {
        swapTo("/view/session_panel.fxml");
    }

    @FXML
    private void openProgress(MouseEvent e) {
        swapTo("/view/progress_panel.fxml");
    }

    @FXML
    private void openHealthProfiles(MouseEvent e) {
        swapTo("/view/health_profile_panel.fxml");
    }

    @FXML
    private void openGoals(MouseEvent e) {
        swapTo("/view/goal_panel.fxml");
    }

    @FXML
    private void openInjuries(MouseEvent e) {
        swapTo("/view/injury_panel.fxml");
    }

    @FXML
    private void openRehabPlans(MouseEvent e) {
        swapTo("/view/rehab_plan_panel.fxml");
    }

    @FXML
    private void openRehabTracking(MouseEvent e) {
        swapTo("/view/rehab_tracking_panel.fxml");
    }

    @FXML
    private void openTasks(MouseEvent e) {
        swapTo("/view/task_panel.fxml");
    }

    private void swapTo(String fxmlPath) {
        try {
            URL url = resolveResource(fxmlPath);
            if (url == null) return;
            
            FXMLLoader loader = new FXMLLoader(url);
            Node in = loader.load();
            
            Object ctrl = loader.getController();
            if (ctrl instanceof ChildController) {
                ((ChildController) ctrl).setMainController(this);
            }
            
            if (contentHost.getChildren().isEmpty()) {
                contentHost.getChildren().add(in);
                animateFormIn(in);
                return;
            }

            Node current = contentHost.getChildren().get(0);
            
            FadeTransition ftOut = new FadeTransition(Duration.millis(150), current);
            ftOut.setToValue(0);
            
            ScaleTransition stOut = new ScaleTransition(Duration.millis(150), current);
            stOut.setToX(0.96); stOut.setToY(0.96);
            
            ParallelTransition ptOut = new ParallelTransition(ftOut, stOut);
            ptOut.setOnFinished(e -> {
                contentHost.getChildren().setAll(in);
                animateFormIn(in);
            });
            ptOut.play();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void animateFormIn(Node node) {
        node.setOpacity(0);
        node.setTranslateY(30);
        node.setScaleX(0.95);
        node.setScaleY(0.95);

        FadeTransition ft = new FadeTransition(Duration.millis(400), node);
        ft.setToValue(1);
        ft.setInterpolator(Interpolator.EASE_OUT);

        TranslateTransition tt = new TranslateTransition(Duration.millis(400), node);
        tt.setToY(0);
        tt.setInterpolator(Interpolator.EASE_OUT);

        ScaleTransition st = new ScaleTransition(Duration.millis(400), node);
        st.setToX(1); st.setToY(1);
        st.setInterpolator(Interpolator.EASE_OUT);

        ParallelTransition pt = new ParallelTransition(ft, tt, st);
        pt.play();
    }

    public void playLoginPushAnimation(Runnable onDone) {
        loginAnimOverlay.setVisible(true);
        loginAnimOverlay.setManaged(true);
        loginAnimOverlay.setOpacity(0);
        
        loginAnimCard.setOpacity(0);
        loginAnimCard.setScaleX(0.7);
        loginAnimCard.setScaleY(0.7);
        
        loginAnimTitle.setOpacity(0);
        loginAnimSub.setOpacity(0);

        // Phase 1
        FadeTransition ftOverlay = new FadeTransition(Duration.millis(200), loginAnimOverlay);
        ftOverlay.setToValue(1);

        ScaleTransition stCard = new ScaleTransition(Duration.millis(350), loginAnimCard);
        stCard.setToX(1); stCard.setToY(1);
        stCard.setInterpolator(Interpolator.EASE_OUT);
        
        FadeTransition ftCard = new FadeTransition(Duration.millis(350), loginAnimCard);
        ftCard.setToValue(1);

        SequentialTransition seq = new SequentialTransition();
        seq.getChildren().addAll(ftOverlay, new ParallelTransition(stCard, ftCard));

        // Phase 2
        pushRunner.setTranslateX(-40);
        DropShadow glow = new DropShadow(0, Color.web("#E53935", 0.0));
        pushRunner.setEffect(glow);

        TranslateTransition ttRunner = new TranslateTransition(Duration.millis(500), pushRunner);
        ttRunner.setFromX(-40); ttRunner.setToX(55);
        ttRunner.setInterpolator(Interpolator.SPLINE(0.2, 0.8, 0.3, 1.0));

        ScaleTransition stRunner = new ScaleTransition(Duration.millis(500), pushRunner);
        stRunner.setToX(1.15); stRunner.setToY(0.90);
        stRunner.setInterpolator(Interpolator.EASE_IN);

        RotateTransition rtRunner = new RotateTransition(Duration.millis(500), pushRunner);
        rtRunner.setToAngle(-14);
        rtRunner.setInterpolator(Interpolator.EASE_IN);

        Timeline glowBuild = new Timeline(
            new KeyFrame(Duration.millis(500), 
                new KeyValue(glow.radiusProperty(), 22),
                new KeyValue(glow.colorProperty(), Color.web("#E53935", 0.7)))
        );

        seq.getChildren().add(new ParallelTransition(ttRunner, stRunner, rtRunner, glowBuild));

        // Phase 3
        TranslateTransition ttImpact = new TranslateTransition(Duration.millis(300), pushTarget);
        ttImpact.setFromX(0); ttImpact.setToX(70);
        ttImpact.setInterpolator(Interpolator.EASE_OUT);

        ScaleTransition stImpact = new ScaleTransition(Duration.millis(300), pushTarget);
        stImpact.setToX(1.25); stImpact.setToY(0.85);

        RotateTransition rtImpact = new RotateTransition(Duration.millis(300), pushTarget);
        rtImpact.setToAngle(12);

        Timeline glowBurst = new Timeline(
            new KeyFrame(Duration.millis(100), new KeyValue(glow.radiusProperty(), 35)),
            new KeyFrame(Duration.millis(350), new KeyValue(glow.radiusProperty(), 8))
        );

        FadeTransition ftTitle = new FadeTransition(Duration.millis(300), loginAnimTitle);
        ftTitle.setToValue(1);
        FadeTransition ftSub = new FadeTransition(Duration.millis(300), loginAnimSub);
        ftSub.setToValue(1);

        TranslateTransition shake1 = new TranslateTransition(Duration.millis(60), loginAnimCard); shake1.setToX(6);
        TranslateTransition shake2 = new TranslateTransition(Duration.millis(60), loginAnimCard); shake2.setToX(-5);
        TranslateTransition shake3 = new TranslateTransition(Duration.millis(80), loginAnimCard); shake3.setToX(0);

        seq.getChildren().add(new ParallelTransition(ttImpact, stImpact, rtImpact, glowBurst, ftTitle, ftSub, new SequentialTransition(shake1, shake2, shake3)));

        // Phase 4
        seq.getChildren().add(new PauseTransition(Duration.millis(600)));
        FadeTransition ftOut = new FadeTransition(Duration.millis(300), loginAnimOverlay);
        ftOut.setToValue(0);
        ftOut.setOnFinished(e -> {
            loginAnimOverlay.setVisible(false);
            loginAnimOverlay.setManaged(false);
            pushRunner.setEffect(null);
            if (onDone != null) onDone.run();
        });
        seq.getChildren().add(ftOut);

        seq.play();
    }

    private URL resolveResource(String path) {
        URL url = getClass().getResource(path);
        if (url == null && path.startsWith("/")) url = getClass().getResource(path.substring(1));
        if (url == null) url = Thread.currentThread().getContextClassLoader().getResource(path.startsWith("/") ? path.substring(1) : path);
        return url;
    }
}
