package nexum.nexum;

import data.users.User;
import data.users.UsersRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

// (NEW) 1. Import the HomepageController


public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginBtn;
    @FXML private Button forgotPasswordBtn;
    @FXML private Button signUpBtn;

    // ... (your showWarning, requireField, and loadScene methods are fine) ...
    private void showWarning(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private boolean requireField(TextField field) {
        if (field.getText().trim().isEmpty()) {
            field.setStyle("-fx-border-color: red; -fx-border-width: 1;");
            return false;
        }
        field.setStyle("");
        return true;
    }

    private void loadScene(ActionEvent event, String fxmlFile, String title) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(view);
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            currentStage.setScene(scene);
            currentStage.setTitle(title);
            currentStage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void handleLoginClick(ActionEvent event) {

        boolean ok1 = requireField(emailField);
        boolean ok2 = requireField(passwordField);

        if (!ok1 || !ok2) {
            showWarning("Please enter both email and password.");
            return;
        }

        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        User user = UsersRepository.findByEmail(email);

        if (user == null) {
            showWarning("Account does not exist.");
            return;
        }

        if (!user.getPassword().equals(password)) {
            showWarning("Incorrect password.");
            return;
        }

        // (CHANGED) 2. We can't use the simple loadScene anymore.
        // We must load the FXML manually to get the controller.

        try {
            // Create a new FXML Loader
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));

            // Load the FXML file
            Parent root = loader.load();

            // Get the controller (HomepageController)
            DashboardController DashboardController = loader.getController();

            // *** THIS IS THE MOST IMPORTANT STEP ***
            // Pass the logged-in user to the homepage
            DashboardController.setUser(user);

            // Now, show the new scene
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) loginBtn.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.setTitle("Nexum - Home");
            currentStage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleForgotPasswordClick(ActionEvent event) {
        loadScene(event, "forgot-password.fxml", "Forgot Password");
    }

    @FXML
    void handleSignUpClick(ActionEvent event) {
        loadScene(event, "signup.fxml", "Sign Up");
    }
}