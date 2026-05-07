package greenmindtechfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tn.edu.esprit.entities.User;

public class SessionController implements ChildController {

    @FXML
    private Label subtitleLabel;

    private MainController mainController;

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    void setUser(User user) {
        if (subtitleLabel != null && user != null) {
            subtitleLabel.setText(user.getPrenom() + " " + user.getNom() + " • " + user.getEmail());
        }
    }

    @FXML
    private void onLogout() {
        if (mainController != null) mainController.showLogin();
    }
}
