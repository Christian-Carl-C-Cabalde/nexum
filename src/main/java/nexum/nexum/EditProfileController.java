package nexum.nexum;

import data.users.User;
import javafx.scene.control.Alert;
import data.users.UsersRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class EditProfileController {

    @FXML private VBox modalRoot;
    @FXML private Button closeButton;

    // These @FXML links will now work because of the fx:id tags in the FXML
    @FXML private TextField firstNameField;
    @FXML private TextField middleNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    private ProfileController parentController;
    private User currentUser;

    public void setParentController(ProfileController parentController) {
        this.parentController = parentController;
    }

    /**
     * Load user data into fields (read-only for everything except password)
     * This will no longer throw a NullPointerException.
     */
    public void loadUserData(User user) {
        if (user == null) return;

        this.currentUser = user;

        firstNameField.setText(user.getUserFirstName());
        firstNameField.setEditable(false);

        middleNameField.setText(user.getUserMiddleName());
        middleNameField.setEditable(false);

        lastNameField.setText(user.getUserLastName());
        lastNameField.setEditable(false);

        emailField.setText(user.getEmail());
        emailField.setEditable(false);

        passwordField.setText(""); // keep password empty initially
    }

    @FXML
    void onCloseClick(ActionEvent event) {
        // This logic is correct
        StackPane parentPane = (StackPane) modalRoot.getParent();
        parentPane.getChildren().remove(modalRoot);

        if (parentController != null) {
            parentController.closeModal();
        }
    }

    /**
     * Save the new password and show a confirmation alert
     */
    @FXML
    void onSaveClick(ActionEvent event) { // <-- 1. Renamed from showAlert
        if (currentUser == null) return;

        String newPassword = passwordField.getText();

        // --- 2. Show an ERROR alert if the password is empty ---
        if (newPassword.isEmpty()) {
            System.out.println("⚠️ Password field is empty. No changes made.");

            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Password Cannot Be Empty");
            errorAlert.setContentText("Please enter a new password to save.");
            errorAlert.showAndWait();

            return; // Stop running the method
        }

        // Update password in the user object
        currentUser.setPassword(newPassword);

        // Save the change using the updateUser method
        UsersRepository.updateUser(currentUser);

        System.out.println("✅ Password updated successfully.");

        // --- 3. THIS IS THE SUCCESS ALERT YOU WANTED ---
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Password updated successfully!");
        successAlert.showAndWait(); // Wait for user to click OK

        // Close the modal
        onCloseClick(event);
    }
}