package greenmindtechfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import tn.edu.esprit.entities.User;

public class LoginController implements ChildController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label statusLabel;

    private MainController mainController;

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void onLogin() {
        clearStatus();
        if (mainController == null) return;
        String email = emailField.getText() == null ? "" : emailField.getText().trim();
        String password = passwordField.getText() == null ? "" : passwordField.getText();
        User user = mainController.login(email, password);
        if (user == null) {
            setError("Login failed.");
            return;
        }
        mainController.showHomeAfterLoginAnimation(user);
    }

    @FXML
    private void onGoSignup() {
        clearStatus();
        if (mainController != null) mainController.showSignup();
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
}
