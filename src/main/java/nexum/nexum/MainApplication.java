package nexum.nexum;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // 1. Load the FXML file you specified: "homepage-profile.fxml"
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("homepage-profile.fxml"));

        // 2. Load the FXML content into a Parent object
        Parent root = fxmlLoader.load();

        // 3. Create the Scene with the loaded content
        Scene scene = new Scene(root);

        // 4. IMPORTANT: Add the stylesheet to the scene
        // This assumes "application.css" is in the same folder as this .java file
        try {
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        } catch (NullPointerException e) {
            System.err.println("Error: 'application.css' not found. Make sure it's in the same folder as MainApplication.java");
            // The application will still run, but without styles
        }

        // 5. Set the scene and show the stage
        stage.setScene(scene);
        stage.setTitle("Nexum Profile"); // You can set a title here
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}