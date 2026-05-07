package greenmindtechfx;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import tn.edu.esprit.entities.User;
import tn.edu.esprit.services.ServicePasswordReset;
import tn.edu.esprit.services.ServiceUser;
import tn.edu.esprit.tools.EmailSender;
import tn.edu.esprit.tools.ValidationUtil;

public class MainController {

    /* ── FXML BINDINGS ── */
    @FXML private StackPane contentHost;
    @FXML private StackPane loginAnimOverlay;
    @FXML private Label pushRunner;
    @FXML private Label pushTarget;

    // Brand header elements (was sidebar)
    @FXML private VBox brandPanel;
    @FXML private HBox logoBox;
    @FXML private Label logoIcon;
    @FXML private Label logoText;
    @FXML private Label brandTagline;
    @FXML private Region brandSeparator;
    @FXML private VBox card1;
    @FXML private VBox card2;
    @FXML private VBox card3;
    @FXML private Label forgeTagline;

    // Background region (animated slow pan via Timeline)
    @FXML private Region bgRegion;

    // Login anim extras
    @FXML private VBox loginAnimCard;
    @FXML private Label loginAnimTitle;
    @FXML private Label loginAnimSub;

    /* ── SERVICES ── */
    private final ServiceUser serviceUser = new ServiceUser();
    private final ServicePasswordReset servicePasswordReset = new ServicePasswordReset();
    private final EmailSender emailSender = EmailSenderFactory.createFromEnv();

    /* ════════════════════════════════════════════════════════════
       INITIALIZATION
       ════════════════════════════════════════════════════════════ */
    @FXML
    private void initialize() {
        startBackgroundPan();
        playStartupAnimation();
        showLogin();
        startLogoPulse();
    }

    /* ── BACKGROUND SLOW PAN — Ken Burns effect via Timeline ── */
    private void startBackgroundPan() {
        if (bgRegion == null) return;

        // Start slightly zoomed and offset
        bgRegion.setScaleX(1.08);
        bgRegion.setScaleY(1.08);
        bgRegion.setTranslateX(0);

        // Slow pan left and right, breathing scale
        Timeline pan = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(bgRegion.translateXProperty(), 0,    Interpolator.EASE_BOTH),
                new KeyValue(bgRegion.scaleXProperty(),    1.08,  Interpolator.EASE_BOTH),
                new KeyValue(bgRegion.scaleYProperty(),    1.08,  Interpolator.EASE_BOTH)),
            new KeyFrame(Duration.millis(14000),
                new KeyValue(bgRegion.translateXProperty(), -28,  Interpolator.EASE_BOTH),
                new KeyValue(bgRegion.scaleXProperty(),    1.13,  Interpolator.EASE_BOTH),
                new KeyValue(bgRegion.scaleYProperty(),    1.13,  Interpolator.EASE_BOTH)),
            new KeyFrame(Duration.millis(28000),
                new KeyValue(bgRegion.translateXProperty(), 0,    Interpolator.EASE_BOTH),
                new KeyValue(bgRegion.scaleXProperty(),    1.08,  Interpolator.EASE_BOTH),
                new KeyValue(bgRegion.scaleYProperty(),    1.08,  Interpolator.EASE_BOTH))
        );
        pan.setCycleCount(Animation.INDEFINITE);
        pan.play();
    }

    /* ── STARTUP: staggered reveal of brand header elements from above ── */
    private void playStartupAnimation() {
        Node[] elements = { logoBox, brandTagline, brandSeparator, card1, card2, card3, forgeTagline };

        for (Node n : elements) {
            if (n != null) {
                n.setOpacity(0);
                n.setTranslateY(-20);
            }
        }

        for (int i = 0; i < elements.length; i++) {
            Node n = elements[i];
            if (n == null) continue;

            int delay = 150 + (i * 120);

            FadeTransition fade = new FadeTransition(Duration.millis(500), n);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.setDelay(Duration.millis(delay));
            fade.setInterpolator(Interpolator.EASE_OUT);

            TranslateTransition slide = new TranslateTransition(Duration.millis(500), n);
            slide.setFromY(-20);
            slide.setToY(0);
            slide.setDelay(Duration.millis(delay));
            slide.setInterpolator(Interpolator.EASE_OUT);

            new ParallelTransition(fade, slide).play();
        }
    }

    /* ── LOGO PULSE: continuous subtle glow animation on the fire icon ── */
    private void startLogoPulse() {
        if (logoIcon == null) return;

        DropShadow glow = new DropShadow();
        glow.setColor(Color.rgb(229, 57, 53, 0.6));
        glow.setRadius(0);
        glow.setSpread(0.2);
        logoIcon.setEffect(glow);

        Timeline pulse = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(glow.radiusProperty(), 0, Interpolator.EASE_BOTH)),
            new KeyFrame(Duration.millis(1500),
                new KeyValue(glow.radiusProperty(), 18, Interpolator.EASE_BOTH)),
            new KeyFrame(Duration.millis(3000),
                new KeyValue(glow.radiusProperty(), 0, Interpolator.EASE_BOTH))
        );
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.play();

        // Subtle scale breathing on logo text
        if (logoText != null) {
            ScaleTransition breathe = new ScaleTransition(Duration.millis(3000), logoText);
            breathe.setFromX(1.0);
            breathe.setToX(1.02);
            breathe.setFromY(1.0);
            breathe.setToY(1.02);
            breathe.setCycleCount(Animation.INDEFINITE);
            breathe.setAutoReverse(true);
            breathe.setInterpolator(Interpolator.EASE_BOTH);
            breathe.play();
        }
    }

    /* ════════════════════════════════════════════════════════════
       VIEW NAVIGATION
       ════════════════════════════════════════════════════════════ */
    void showLogin() {
        swapTo(loadView("/greenmindtechfx/Login.fxml"));
    }

    void showHome(User user) {
        Node view = loadView("/greenmindtechfx/Home.fxml");
        if (view.getUserData() instanceof HomeController) {
            ((HomeController) view.getUserData()).setUser(user);
        }
        swapTo(view);
    }

    void showSignup() {
        swapTo(loadView("/greenmindtechfx/Signup.fxml"));
    }

    void showReset() {
        swapTo(loadView("/greenmindtechfx/Reset.fxml"));
    }

    void showSession(User user) {
        Node view = loadView("/greenmindtechfx/Session.fxml");
        if (view.getUserData() instanceof SessionController) {
            ((SessionController) view.getUserData()).setUser(user);
        }
        swapTo(view);
    }

    void showSessionAfterLoginAnimation(User user) {
        Node view = loadView("/greenmindtechfx/Session.fxml");
        if (view.getUserData() instanceof SessionController) {
            ((SessionController) view.getUserData()).setUser(user);
        }
        playLoginPushAnimation(() -> swapTo(view));
    }

    void showHomeAfterLoginAnimation(User user) {
        Node view = loadView("/greenmindtechfx/Home.fxml");
        if (view.getUserData() instanceof HomeController) {
            ((HomeController) view.getUserData()).setUser(user);
        }
        playLoginPushAnimation(() -> swapTo(view));
    }

    /* ════════════════════════════════════════════════════════════
       BUSINESS LOGIC (unchanged)
       ════════════════════════════════════════════════════════════ */
    User login(String email, String password) {
        if (!ValidationUtil.isValidEmail(email)) return null;
        if (password == null || password.trim().isEmpty()) return null;
        return serviceUser.login(email.trim(), password);
    }

    String signup(String email, String password, String nom, String prenom) {
        if (!ValidationUtil.isValidEmail(email)) return "Invalid email.";
        if (!ValidationUtil.isValidPassword(password)) return "Password must be 8-64 with upper/lower/digit/symbol.";
        if (!ValidationUtil.isValidName(nom) || !ValidationUtil.isValidName(prenom)) return "Nom/Prenom must be 2-50 letters.";
        if (serviceUser.findUserIdByEmail(email.trim()) != null) return "Email already exists.";
        boolean ok = serviceUser.register(new User(email.trim(), password, nom.trim(), prenom.trim(), "[\"ROLE_USER\"]"));
        return ok ? null : "Sign up failed.";
    }

    String sendResetCode(String email) {
        if (!ValidationUtil.isValidEmail(email)) return "Invalid email.";
        Integer userId = serviceUser.findUserIdByEmail(email.trim());
        if (userId == null) return "Email not found.";
        String code = servicePasswordReset.createResetCode(userId);
        if (code == null) return "Could not generate reset code.";
        try {
            emailSender.send(email.trim(), "Password Reset Code", "Your 6-digit reset code is: " + code);
        } catch (RuntimeException e) {
            return "Could not send email. Check Gmail SMTP environment variables.";
        }
        return null;
    }

    String confirmReset(String code, String newPassword) {
        if (!ValidationUtil.isValidSixDigitCode(code)) return "Code must be 6 digits.";
        if (!ValidationUtil.isValidPassword(newPassword)) return "Password must be 8-64 with upper/lower/digit/symbol.";
        if (!servicePasswordReset.validateResetCode(code.trim())) return "Invalid or expired code.";
        Integer userId = servicePasswordReset.getUserIdByResetCode(code.trim());
        if (userId == null) return "Invalid code.";
        boolean ok = serviceUser.updatePassword(userId, newPassword);
        if (!ok) return "Could not update password.";
        servicePasswordReset.markAsUsed(code.trim());
        return null;
    }

    /* ════════════════════════════════════════════════════════════
       VIEW LOADING
       ════════════════════════════════════════════════════════════ */
    private Node loadView(String resource) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
            Node node = loader.load();
            Object c = loader.getController();
            if (c instanceof ChildController) {
                ((ChildController) c).setMainController(this);
            }
            node.setUserData(c);
            return node;
        } catch (Exception e) {
            return new StackPane();
        }
    }

    /* ════════════════════════════════════════════════════════════
       VIEW SWAP — animated form transition (unchanged)
       ════════════════════════════════════════════════════════════ */
    private void swapTo(Node in) {
        if (in == null) return;

        if (contentHost.getChildren().isEmpty()) {
            contentHost.getChildren().setAll(in);
            animateFormIn(in);
            return;
        }

        Node current = contentHost.getChildren().get(0);
        if (current == in) return;

        FadeTransition fadeOut = new FadeTransition(Duration.millis(150), current);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(150), current);
        scaleOut.setFromX(1);
        scaleOut.setToX(0.96);
        scaleOut.setFromY(1);
        scaleOut.setToY(0.96);

        ParallelTransition exit = new ParallelTransition(fadeOut, scaleOut);
        exit.setOnFinished(e -> {
            contentHost.getChildren().setAll(in);
            animateFormIn(in);
        });
        exit.play();
    }

    private void animateFormIn(Node node) {
        node.setOpacity(0);
        node.setTranslateY(30);
        node.setScaleX(0.95);
        node.setScaleY(0.95);

        FadeTransition fade = new FadeTransition(Duration.millis(400), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setInterpolator(Interpolator.EASE_OUT);

        TranslateTransition slide = new TranslateTransition(Duration.millis(400), node);
        slide.setFromY(30);
        slide.setToY(0);
        slide.setInterpolator(Interpolator.EASE_OUT);

        ScaleTransition scale = new ScaleTransition(Duration.millis(400), node);
        scale.setFromX(0.95);
        scale.setToX(1);
        scale.setFromY(0.95);
        scale.setToY(1);
        scale.setInterpolator(Interpolator.EASE_OUT);

        new ParallelTransition(fade, slide, scale).play();
    }

    /* ════════════════════════════════════════════════════════════
       LOGIN PUSH ANIMATION (unchanged)
       ════════════════════════════════════════════════════════════ */
    private void playLoginPushAnimation(Runnable onDone) {
        if (loginAnimOverlay == null || pushRunner == null || pushTarget == null) {
            onDone.run();
            return;
        }

        loginAnimOverlay.setManaged(true);
        loginAnimOverlay.setVisible(true);
        loginAnimOverlay.setOpacity(0);
        pushRunner.setTranslateX(0);
        pushRunner.setTranslateY(0);
        pushRunner.setScaleX(1);
        pushRunner.setScaleY(1);
        pushRunner.setRotate(0);
        pushTarget.setTranslateX(0);
        pushTarget.setScaleX(1);
        pushTarget.setScaleY(1);
        pushTarget.setRotate(0);

        if (loginAnimCard != null) {
            loginAnimCard.setScaleX(0.7);
            loginAnimCard.setScaleY(0.7);
            loginAnimCard.setOpacity(0);
        }
        if (loginAnimTitle != null) loginAnimTitle.setOpacity(0);
        if (loginAnimSub != null) loginAnimSub.setOpacity(0);

        DropShadow runnerGlow = new DropShadow();
        runnerGlow.setColor(Color.rgb(229, 57, 53, 0.0));
        runnerGlow.setRadius(0);
        runnerGlow.setSpread(0.4);
        pushRunner.setEffect(runnerGlow);

        // Phase 1
        FadeTransition overlayIn = new FadeTransition(Duration.millis(200), loginAnimOverlay);
        overlayIn.setFromValue(0);
        overlayIn.setToValue(1);

        ParallelTransition cardEntrance = new ParallelTransition();
        if (loginAnimCard != null) {
            ScaleTransition cardScale = new ScaleTransition(Duration.millis(350), loginAnimCard);
            cardScale.setFromX(0.7); cardScale.setToX(1);
            cardScale.setFromY(0.7); cardScale.setToY(1);
            cardScale.setInterpolator(Interpolator.EASE_OUT);
            FadeTransition cardFade = new FadeTransition(Duration.millis(350), loginAnimCard);
            cardFade.setFromValue(0); cardFade.setToValue(1);
            cardEntrance.getChildren().addAll(cardScale, cardFade);
        }

        // Phase 2
        pushRunner.setTranslateX(-40);
        TranslateTransition runnerCharge = new TranslateTransition(Duration.millis(500), pushRunner);
        runnerCharge.setFromX(-40); runnerCharge.setToX(55);
        runnerCharge.setInterpolator(Interpolator.SPLINE(0.2, 0.8, 0.3, 1.0));
        ScaleTransition runnerSquash = new ScaleTransition(Duration.millis(500), pushRunner);
        runnerSquash.setFromX(1); runnerSquash.setToX(1.15);
        runnerSquash.setFromY(1); runnerSquash.setToY(0.90);
        runnerSquash.setInterpolator(Interpolator.EASE_IN);
        RotateTransition runnerLean = new RotateTransition(Duration.millis(500), pushRunner);
        runnerLean.setFromAngle(0); runnerLean.setToAngle(-14);
        runnerLean.setInterpolator(Interpolator.EASE_IN);
        Timeline glowBuild = new Timeline(
            new KeyFrame(Duration.ZERO,
                new KeyValue(runnerGlow.radiusProperty(), 0),
                new KeyValue(runnerGlow.colorProperty(), Color.rgb(229, 57, 53, 0.0))),
            new KeyFrame(Duration.millis(500),
                new KeyValue(runnerGlow.radiusProperty(), 22),
                new KeyValue(runnerGlow.colorProperty(), Color.rgb(229, 57, 53, 0.7)))
        );
        ParallelTransition charge = new ParallelTransition(runnerCharge, runnerSquash, runnerLean, glowBuild);

        // Phase 3
        TranslateTransition targetPush = new TranslateTransition(Duration.millis(300), pushTarget);
        targetPush.setFromX(0); targetPush.setToX(70);
        targetPush.setInterpolator(Interpolator.EASE_OUT);
        ScaleTransition targetSquash = new ScaleTransition(Duration.millis(300), pushTarget);
        targetSquash.setFromX(1); targetSquash.setToX(1.25);
        targetSquash.setFromY(1); targetSquash.setToY(0.85);
        RotateTransition targetSpin = new RotateTransition(Duration.millis(300), pushTarget);
        targetSpin.setFromAngle(0); targetSpin.setToAngle(12);

        TranslateTransition shake1 = null, shake2 = null, shake3 = null;
        if (loginAnimCard != null) {
            shake1 = new TranslateTransition(Duration.millis(60), loginAnimCard);
            shake1.setFromX(0); shake1.setToX(6);
            shake2 = new TranslateTransition(Duration.millis(60), loginAnimCard);
            shake2.setFromX(6); shake2.setToX(-5);
            shake3 = new TranslateTransition(Duration.millis(80), loginAnimCard);
            shake3.setFromX(-5); shake3.setToX(0);
        }

        ParallelTransition impact = new ParallelTransition(targetPush, targetSquash, targetSpin);

        Timeline glowBurst = new Timeline(
            new KeyFrame(Duration.ZERO,       new KeyValue(runnerGlow.radiusProperty(), 22)),
            new KeyFrame(Duration.millis(100), new KeyValue(runnerGlow.radiusProperty(), 35)),
            new KeyFrame(Duration.millis(350), new KeyValue(runnerGlow.radiusProperty(), 8))
        );

        FadeTransition titleIn = null;
        if (loginAnimTitle != null) {
            titleIn = new FadeTransition(Duration.millis(300), loginAnimTitle);
            titleIn.setFromValue(0); titleIn.setToValue(1);
        }
        FadeTransition subIn = null;
        if (loginAnimSub != null) {
            subIn = new FadeTransition(Duration.millis(300), loginAnimSub);
            subIn.setFromValue(0); subIn.setToValue(1);
        }

        // Phase 4
        PauseTransition hold = new PauseTransition(Duration.millis(600));
        FadeTransition overlayOut = new FadeTransition(Duration.millis(300), loginAnimOverlay);
        overlayOut.setFromValue(1); overlayOut.setToValue(0);
        overlayOut.setOnFinished(e -> {
            loginAnimOverlay.setVisible(false);
            loginAnimOverlay.setManaged(false);
            pushRunner.setEffect(null);
            onDone.run();
        });

        // Chain
        SequentialTransition sequence = new SequentialTransition();
        sequence.getChildren().add(overlayIn);
        if (cardEntrance.getChildren().size() > 0) sequence.getChildren().add(cardEntrance);
        sequence.getChildren().add(new PauseTransition(Duration.millis(100)));
        sequence.getChildren().add(charge);

        ParallelTransition impactPhase = new ParallelTransition(impact, glowBurst);
        if (titleIn != null) impactPhase.getChildren().add(titleIn);
        if (subIn != null)   impactPhase.getChildren().add(subIn);
        sequence.getChildren().add(impactPhase);

        if (shake1 != null && shake2 != null && shake3 != null) {
            sequence.getChildren().add(new SequentialTransition(shake1, shake2, shake3));
        }

        sequence.getChildren().add(hold);
        sequence.getChildren().add(overlayOut);
        sequence.play();
    }
}
