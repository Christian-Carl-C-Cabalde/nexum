package nexum.nexum;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // 1. Load the FXML file for the main page
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("profile.fxml"));

        // 2. Load the FXML content
        Parent root = fxmlLoader.load();

        // 3. Create the Scene
        Scene scene = new Scene(root);

        // 4. Add the stylesheet to the scene
        try {
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        } catch (NullPointerException e) {
            System.err.println("Error: 'application.css' not found. Make sure it's in the same folder as MainApplication.java");
        }

        // 5. Set the scene and show the stage
        stage.setScene(scene);
        stage.setTitle("Profile");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}