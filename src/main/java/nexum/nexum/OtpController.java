package nexum.nexum;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class OtpController {

    @FXML private Label emailLabel;
    @FXML private Button backButton;
    @FXML private Button verifyButton;
    @FXML private TextField otp1, otp2, otp3, otp4, otp5, otp6;

    private String userEmail;

    // Setter for email text display
    public void setEmail(String email) {
        this.userEmail = email;
        if (emailLabel != null) {
            emailLabel.setText("We’ve sent an OTP to: " + email);
        }
    }

    @FXML
    private void initialize() {
        // Automatically move focus between OTP input boxes
        TextField[] fields = {otp1, otp2, otp3, otp4, otp5, otp6};
        for (int i = 0; i < fields.length; i++) {
            final int index = i;
            fields[i].textProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal.length() == 1 && index < fields.length - 1) {
                    fields[index + 1].requestFocus();
                }
            });
        }
    }

    @FXML
    private void handleVerifyClick() throws IOException {
        String otp = otp1.getText() + otp2.getText() + otp3.getText() +
                otp4.getText() + otp5.getText() + otp6.getText();

        if (otp.equals("123456")) { // Example OTP
            showAlert("OTP verified successfully!");

            // ✅ Load Reset Password screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("resetPassword-view.fxml"));
            Scene scene = new Scene(loader.load());

            // Add CSS
            String css = this.getClass().getResource("application.css").toExternalForm();
            scene.getStylesheets().add(css);

            Stage stage = (Stage) verifyButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Nexum - Reset Password");
            stage.show();

        } else {
            showAlert("Incorrect OTP. Try again.");
        }
    }

    @FXML
    private void handleBackClick() throws IOException {
        // ✅ Load Forgot Password (Find Account) screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("forgotPassword-view.fxml"));
        Scene scene = new Scene(loader.load());

        // Add CSS
        String css = this.getClass().getResource("application.css").toExternalForm();
        scene.getStylesheets().add(css);

        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Nexum - Find Account");
        stage.show();
    }

    // Alert helper
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("OTP Verification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}