package nexum.nexum;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class ForgotPasswordController {

    @FXML private TextField emailField;
    @FXML private Button searchBtn;

    @FXML
    private void initialize() {
        // Optional: only allow valid email characters while typing
        emailField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("[a-zA-Z0-9._%+-@]*")) {
                emailField.setText(oldVal);
            }
        });
    }

    @FXML
    private void handleSearchClick() {
        String email = emailField.getText();

        if (isValidEmail(email)) {
            try {
                // ✅ Load OTP scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("otp-view.fxml"));
                Scene scene = new Scene(loader.load());

                // ✅ Apply CSS
                String css = this.getClass().getResource("application.css").toExternalForm();
                scene.getStylesheets().add(css);

                // ✅ Pass email to OTP controller
                OtpController otpCtrl = loader.getController();
                otpCtrl.setEmail(email);

                // ✅ Switch to OTP scene
                Stage stage = (Stage) searchBtn.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Nexum - Verify OTP");
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error loading OTP screen.");
            }
        } else {
            showAlert("Please enter a valid email address.");
        }
    }

    // Simple email validation regex
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    // Alert helper
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Email Validation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}