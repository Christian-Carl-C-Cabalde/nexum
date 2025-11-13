package nexum.nexum;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginBtn;

    @FXML
    private Button forgotPasswordBtn;

    @FXML
    private Button signUpBtn;

    /**
     * Helper method to switch scenes.
     */
    private void loadScene(ActionEvent event, String fxmlFile, String title) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(view);

            // Get the stage from the event source
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            currentStage.setScene(scene);
            currentStage.setTitle(title);
            currentStage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when the "Login" button is clicked.
     * This will load the homepage.fxml scene.
     */
    @FXML
    void handleLoginClick(ActionEvent event) {
        // Add your login verification logic here...
        // For example: if (authService.login(emailField.getText(), passwordField.getText())) { ... }

        // On successful login, load the homepage
        loadScene(event, "homepage.fxml", "Nexum - Home");
    }

    /**
     * Called when the "Forgot Password?" button is clicked.
     */
    @FXML
    void handleForgotPasswordClick(ActionEvent event) {
        loadScene(event, "forgot-password.fxml", "Forgot Password");
    }

    /**
     * Called when the "Sign Up" button is clicked.
     */
    @FXML
    void handleSignUpClick(ActionEvent event) {
        loadScene(event, "signup.fxml", "Sign Up");
    }
}