package greenmindtechfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ResetController implements ChildController {

    @FXML
    private TextField emailField;
    @FXML
    private TextField codeField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private Label statusLabel;

    private MainController mainController;

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void onSendCode() {
        clearStatus();
        if (mainController == null) return;
        String email = emailField.getText() == null ? "" : emailField.getText().trim();
        String err = mainController.sendResetCode(email);
        if (err != null) {
            setError(err);
            return;
        }
        setSuccess("Reset code sent. Check your email.");
    }

    @FXML
    private void onConfirmReset() {
        clearStatus();
        if (mainController == null) return;
        String code = codeField.getText() == null ? "" : codeField.getText().trim();
        String newPassword = newPasswordField.getText() == null ? "" : newPasswordField.getText();
        String err = mainController.confirmReset(code, newPassword);
        if (err != null) {
            setError(err);
            return;
        }
        setSuccess("Password updated. You can login now.");
    }

    @FXML
    private void onGoLogin() {
        clearStatus();
        if (mainController != null) mainController.showLogin();
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
