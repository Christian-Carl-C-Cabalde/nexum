package nexum.nexum;

import data.users.User;
import data.users.UsersRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ForgotPasswordController {

    @FXML private TextField emailField;
    @FXML private Button searchBtn;
    @FXML private Button backToLoginBtn;

    @FXML
    private void handleSearchClick() {
        String email = emailField.getText().trim();

        if (email.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter your email.");
            return;
        }

        User user = UsersRepository.findByEmail(email);

        if (user == null) {
            showAlert(Alert.AlertType.ERROR, "Account Not Found", "No account found with that email.");
            return;
        }

        // Store this email temporarily (for OTP + new password)
        ResetPasswordState.emailForReset = email;

        loadScene("otp-view.fxml");
    }

    @FXML
    private void handleBackToLoginClick() {
        loadScene("login.fxml");
    }

    private void loadScene(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Scene scene = new Scene(root);

            // get current stage via any node in the window:
            Stage stage = (Stage) emailField.getScene().getWindow();

            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showAlert(Alert.AlertType type, String msg, String header) {
        Alert alert = new Alert(type);
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showAlert(Alert.AlertType type, String msg) {
        showAlert(type, msg, null);
    }


}
