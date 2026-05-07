package greenmindtechfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignupController implements ChildController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private Label statusLabel;

    private MainController mainController;

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void onSignup() {
        clearStatus();
        if (mainController == null) {
            setError("Screen is not ready. Please reopen Sign up.");
            return;
        }
        String email = emailField.getText() == null ? "" : emailField.getText().trim();
        String password = passwordField.getText() == null ? "" : passwordField.getText();
        String nom = nomField.getText() == null ? "" : nomField.getText().trim();
        String prenom = prenomField.getText() == null ? "" : prenomField.getText().trim();

        String err;
        try {
            err = mainController.signup(email, password, nom, prenom);
        } catch (RuntimeException e) {
            setError("Sign up failed. Please check database connection.");
            return;
        }
        if (err != null) {
            setError(err);
            return;
        }
        setSuccess("Account created. Please login.");
    }

    @FXML
    private void onGoLogin() {
        clearStatus();
        if (mainController != null) mainController.showLogin();
    }

    @FXML
    private void onGoReset() {
        clearStatus();
        if (mainController != null) mainController.showReset();
    }

    private void clearStatus() {
        statusLabel.getStyleClass().removeAll("gmt-error", "gmt-success");
        statusLabel.setText("");
    }

    private void setError(String msg) {
        statusLabel.getStyleClass().removeAll("gmt-success");
        if (!statusLabel.getStyleClass().contains("gmt-error")) statusLabel.getStyleClass().add("gmt-error");
        statusLabel.setText(msg);
    }

    private void setSuccess(String msg) {
        statusLabel.getStyleClass().removeAll("gmt-error");
        if (!statusLabel.getStyleClass().contains("gmt-success")) statusLabel.getStyleClass().add("gmt-success");
        statusLabel.setText(msg);
    }
}
