package nexum.nexum;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {

    @FXML
    private Button createBtn;

    @FXML
    private Button loginBtn;

    /**
     * Called when the "Log In" button is clicked.
     * This will load the login.fxml scene.
     */
    @FXML
    void handleLoginClick(ActionEvent event) {
        try {
            // Load the login.fxml file
            Parent loginView = FXMLLoader.load(getClass().getResource("login.fxml"));

            // Create a new Scene with the login view
            Scene loginScene = new Scene(loginView);

            // Get the current Stage (the window) from the button
            Stage currentStage = (Stage) loginBtn.getScene().getWindow();

            // Set the new scene on the stage
            currentStage.setScene(loginScene);
            currentStage.setTitle("Login");
            currentStage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}