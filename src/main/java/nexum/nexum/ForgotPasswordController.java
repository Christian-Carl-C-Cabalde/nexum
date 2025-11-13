package nexum.nexum;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class ForgotPasswordController {

    @FXML
    private Button loginBtn; // This is the "Login" button in forgot-password.fxml

    @FXML
    private void handleLoginClick() throws IOException {
        // Load login.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) loginBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
