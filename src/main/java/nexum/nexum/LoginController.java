package nexum.nexum;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML
    private Button loginBtn;

    @FXML
    private Button forgotPasswordBtn;

    @FXML
    private Button signUpBtn;

    @FXML
    private void handleLoginClick() throws IOException {
        // Load homepage.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) loginBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleForgotPasswordClick() throws IOException {
        // Load forgot-password.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("forgot-password.fxml"));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) forgotPasswordBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleSignUpClick() throws IOException {
        // Load signup.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("signup.fxml"));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) signUpBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
