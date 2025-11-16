package nexum.nexum;

import data.users.User;
import data.users.UsersRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class ResetPasswordController {

    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button resetBtn;
    @FXML private Button backBtn;

    @FXML
    private void handleResetClick() {

        String newPass = newPasswordField.getText().trim();
        String confirm = confirmPasswordField.getText().trim();

        if (newPass.isEmpty() || confirm.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "All fields are required.");
            return;
        }

        if (!newPass.equals(confirm)) {
            showAlert(Alert.AlertType.ERROR, "Password Mismatch", "Passwords do not match!");
            return;
        }

        String email = ResetPasswordState.emailForReset;

        if (email == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No email loaded for password reset.");
            return;
        }

        User user = UsersRepository.findByEmail(email);

        if (user == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not find user.");
            return;
        }

        // Update password
        user.setPassword(newPass);

        showAlert(Alert.AlertType.INFORMATION, "Success", "Password has been reset!");

        loadScene("login.fxml", resetBtn);
    }

    @FXML
    private void handleBackClick() {
        loadScene("otp-view.fxml", backBtn);
    }

    private void loadScene(String fxml, Button btn) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Scene scene = new Scene(root);
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- FIXED showAlert method (now accepts title + message) ---
    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
