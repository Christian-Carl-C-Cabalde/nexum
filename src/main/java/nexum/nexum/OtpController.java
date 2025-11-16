package nexum.nexum;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class OtpController implements Initializable {

    @FXML private TextField otp1;
    @FXML private TextField otp2;
    @FXML private TextField otp3;
    @FXML private TextField otp4;
    @FXML private TextField otp5;
    @FXML private TextField otp6;

    @FXML private Button backButton;
    @FXML private Button confirmBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Chain all OTP fields
        setupOtpField(otp1, otp2, null);
        setupOtpField(otp2, otp3, otp1);
        setupOtpField(otp3, otp4, otp2);
        setupOtpField(otp4, otp5, otp3);
        setupOtpField(otp5, otp6, otp4);
        setupOtpField(otp6, null, otp5);  // last field
    }

    private void setupOtpField(TextField current, TextField next, TextField prev) {

        // Allow only numbers + auto next
        current.textProperty().addListener((obs, oldVal, newVal) -> {

            // Remove non-numeric characters
            if (!newVal.matches("\\d*")) {
                current.setText(newVal.replaceAll("[^\\d]", ""));
                return;
            }

            // Max 1 digit
            if (newVal.length() > 1) {
                current.setText(newVal.substring(0, 1));
            }

            // Move to next
            if (newVal.length() == 1 && next != null) {
                next.requestFocus();
            }

            // Optional: Auto submit when all 6 fields filled
            /*
            if (allOtpEntered()) {
                handleConfirmClick();
            }
            */
        });

        // Backspace auto-move to previous
        current.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case BACK_SPACE:
                    if (current.getText().isEmpty() && prev != null) {
                        prev.requestFocus();
                    }
                    break;
            }
        });
    }

    private boolean allOtpEntered() {
        return otp1.getText().length() == 1 &&
                otp2.getText().length() == 1 &&
                otp3.getText().length() == 1 &&
                otp4.getText().length() == 1 &&
                otp5.getText().length() == 1 &&
                otp6.getText().length() == 1;
    }

    @FXML
    private void handleConfirmClick() {
        String otp =
                otp1.getText() +
                        otp2.getText() +
                        otp3.getText() +
                        otp4.getText() +
                        otp5.getText() +
                        otp6.getText();

        if (otp.length() < 6) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter the 6-digit OTP.");
            return;
        }

        // Proceed
        loadScene("reset-password.fxml", confirmBtn);
    }

    @FXML
    private void handleBackClick() {
        loadScene("forgot-password.fxml", backButton);
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

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
