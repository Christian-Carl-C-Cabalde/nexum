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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        Scene scene = new Scene(loader.load());

        String styleCss = this.getClass().getResource("application.css").toExternalForm();
        scene.getStylesheets().add(styleCss);

        Stage stage = (Stage) loginBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleForgotPasswordClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("forgot-password-view.fxml"));
        Scene scene = new Scene(loader.load());

        String styleCss = this.getClass().getResource("application.css").toExternalForm();
        scene.getStylesheets().add(styleCss);

        Stage stage = (Stage) forgotPasswordBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleSignUpClick() throws IOException {
        // Opens a "signup-view.fxml" when the Sign Up button is clicked
        FXMLLoader loader = new FXMLLoader(getClass().getResource("signup-view.fxml"));
        Scene scene = new Scene(loader.load());

        String styleCss = this.getClass().getResource("application.css").toExternalForm();
        scene.getStylesheets().add(styleCss);

        Stage stage = (Stage) signUpBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
