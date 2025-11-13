package nexum.nexum;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ProfileController {

    // --- FXML Elements from profile.fxml ---
    @FXML
    private StackPane profileRoot; // The root StackPane from profile.fxml

    @FXML
    private Button editProfileButton;

    @FXML
    private Button postsBtn;

    @FXML
    private Button savedBtn;

    /**
     * This method is called by JavaFX after the FXML file is loaded.
     * We use it to set the default active tab.
     */
    @FXML
    public void initialize() {
        // Set "Posts" as the active button by default
        if (postsBtn != null) {
            postsBtn.getStyleClass().add("tab-button-selected");
        }
    }

    // --- Modal Logic ---

    /**
     * Called when the "Edit Profile" button is clicked.
     * This method loads and displays the edit-profile.fxml modal.
     */
    @FXML
    void showEditProfileModal(ActionEvent event) {
        try {
            // Load the modal FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit-profile.fxml"));
            VBox modalRoot = loader.load(); // This is the root VBox from edit-profile.fxml

            // Add the loaded modal to the StackPane. It will appear on top, centered.
            profileRoot.getChildren().add(modalRoot);

            // Disable the "Edit Profile" button so you can't open multiple modals
            editProfileButton.setDisable(true);

            // --- Pass a reference to this controller to the modal ---
            // This allows the modal to tell us when it's closed.
            EditProfileController modalController = loader.getController();
            modalController.setParentController(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called BY the modal controller when it closes.
     */
    public void closeModal() {
        // Re-enable the "Edit Profile" button
        editProfileButton.setDisable(false);
        // The modal's controller already removes itself from the scene.
    }

    // --- Tab Logic ---

    @FXML
    void onPostsClick(ActionEvent event) {
        System.out.println("Posts tab clicked");
        // Add logic to show posts
        // Use the correct class from your profile.css
        postsBtn.getStyleClass().add("tab-button-selected");
        savedBtn.getStyleClass().remove("tab-button-selected");
    }

    @FXML
    void onSavedClick(ActionEvent event) {
        System.out.println("Saved tab clicked");
        // Add logic to show saved items
        // Use the correct class from your profile.css
        savedBtn.getStyleClass().add("tab-button-selected");
        postsBtn.getStyleClass().remove("tab-button-selected");
    }
}