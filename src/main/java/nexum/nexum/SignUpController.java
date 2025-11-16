package nexum.nexum;

import data.users.User;
import data.users.UsersRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {

    @FXML private TextField firstNameField;
    @FXML private TextField middleNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML private Button createBtn;
    @FXML private Button loginBtn;

    @FXML
    private void handleCreateClick(ActionEvent event) throws IOException {

        String first = firstNameField.getText().trim();
        String middle = middleNameField.getText().trim();
        String last = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        // Empty fields check
        if (first.isEmpty() || middle.isEmpty() || last.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill all fields, including Middle Name!");
            return;
        }

        // Basic email check
        if (!email.contains("@")) {
            showAlert(Alert.AlertType.ERROR, "Invalid Email", "Email must contain '@'.");
            return;
        }

        // Check if email already exists
        if (UsersRepository.findByEmail(email) != null) {
            showAlert(Alert.AlertType.ERROR, "Duplicate Email", "That email is already registered.");
            return;
        }

        // Create user
        String userId = "U" + System.currentTimeMillis();
        User user = new User(userId, email, first, middle, last, password);

        UsersRepository.addUser(user);

        showAlert(Alert.AlertType.INFORMATION, "Success", "Account created successfully!");

        // Navigate back to login
        loadScene("login.fxml", createBtn);
    }

    @FXML
    private void handleLoginClick(ActionEvent event) {
        try {
            loadScene("login.fxml", loginBtn);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not load login screen.");
        }
    }

    private void loadScene(String fxml, Button btn) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(root);
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
