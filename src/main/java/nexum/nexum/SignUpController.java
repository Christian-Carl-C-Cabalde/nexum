package nexum.nexum;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class SignUpController {

    @FXML
    private Button loginBtn;

    @FXML
    private Button createBtn; // You can add functionality later for account creation

    @FXML
    private void handleLoginClick() throws IOException {
        // Navigate back to login.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) loginBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
