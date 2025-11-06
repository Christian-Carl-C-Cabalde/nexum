package nexum.nexum;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import java.io.IOException;

public class ResetPasswordController {

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private void handleResetClick() {
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Please fill out both fields.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showAlert("Passwords do not match. Try again.");
            return;
        }

        // ✅ If passwords match, go to Login (Sign-In) screen
        showAlert("Password successfully reset!");
        goToLoginScene();
    }

    // Switch scene to login or home screen
    private void goToLoginScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signin-view.fxml")); // ← change if your login FXML name differs
            Scene scene = new Scene(loader.load());

            // ✅ Add CSS
            String css = this.getClass().getResource("application.css").toExternalForm();
            scene.getStylesheets().add(css);

            Stage stage = (Stage) newPasswordField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Nexum - Sign In");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error loading Sign In screen.");
        }
    }

    // Alert helper
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reset Password");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}