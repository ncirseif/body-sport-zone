package greenmindtechfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GreenMindTechApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/greenmindtechfx/Main.fxml"));
        Scene scene = new Scene(root, 980, 560);
        scene.getStylesheets().add(getClass().getResource("/greenmindtechfx/theme.css").toExternalForm());
        stage.setTitle("Body Sport Zone");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(520);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
