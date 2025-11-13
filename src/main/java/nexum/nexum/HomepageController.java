package nexum.nexum;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays; // Import Arrays
import java.util.List;   // Import List

public class HomepageController {

    // --- Main Content Area ---
    @FXML
    private StackPane mainContent; // <-- This MUST be StackPane to center content

    // --- Sidebar Buttons ---
    @FXML
    private Button goHome;
    @FXML
    private Button goCreate;
    @FXML
    private Button goNotification;
    @FXML
    private Button goProfile;
    @FXML
    private Button logout;

    // List to hold all navigation buttons for easy styling
    private List<Button> navButtons;

    /**
     * This method is called by JavaFX after the FXML file is loaded.
     * We use it to set up our button list and default state.
     */
    @FXML
    public void initialize() {
        // Add all nav buttons (except logout) to our list
        navButtons = Arrays.asList(goHome, goCreate, goNotification, goProfile);

        // Set "Home" as the active button by default when the app starts
        setActiveButton(goHome);
    }

    /**
     * Helper method to set the 'active' style class on the clicked button
     * and remove it from all other navigation buttons.
     */
    private void setActiveButton(Button activeButton) {
        for (Button button : navButtons) {
            if (button == activeButton) {
                // Add "active" class if it's not already there
                if (!button.getStyleClass().contains("active")) {
                    button.getStyleClass().add("active");
                }
            } else {
                // Remove "active" class from all other buttons
                button.getStyleClass().remove("active");
            }
        }
    }

    /**
     * Helper method to load FXML views into the mainContent StackPane
     * This is used for Profile, Notification, and Create Post
     */
    private void loadView(String fxmlFile) {
        try {
            // Load the FXML file as a Parent node
            Parent view = FXMLLoader.load(getClass().getResource(fxmlFile));

            // Clear the main content and add the new view
            mainContent.getChildren().clear();
            mainContent.getChildren().add(view);

        } catch (IOException e) {
            e.printStackTrace();
            // Show an error message in the UI if the file isn't found
            mainContent.getChildren().clear();
            mainContent.getChildren().add(new Label("Error: Could not load " + fxmlFile));
        }
    }

    /**
     * This method is called when the "Create" (post) button is clicked.
     * It loads the post.fxml view into the main content area.
     */
    @FXML
    void goCreate(ActionEvent event) {
        loadView("post.fxml");
        setActiveButton(goCreate); // Set Create as active
    }

    /**
     * Clears the main content when Home is clicked.
     */
    @FXML
    void goHome(ActionEvent event) {
        mainContent.getChildren().clear();
        System.out.println("Home clicked");
        setActiveButton(goHome); // Set Home as active
    }

    /**
     * Loads the notification.fxml view.
     */
    @FXML
    void goNotification(ActionEvent event) {
        loadView("notification.fxml");
        setActiveButton(goNotification); // Set Notification as active
    }

    /**
     * Loads the profile.fxml view.
     */
    @FXML
    void goProfile(ActionEvent event) {
        loadView("profile.fxml");
        setActiveButton(goProfile); // Set Profile as active
    }

    /**
     * Logs the user out by loading the login.fxml view in a new scene.
     */
    @FXML
    void logout(ActionEvent event) {
        try {
            // Load the login.fxml file
            Parent loginView = FXMLLoader.load(getClass().getResource("login.fxml"));

            // Create a new Scene with the login view
            Scene loginScene = new Scene(loginView);

            // Get the current Stage (the window) from the logout button
            Stage currentStage = (Stage) logout.getScene().getWindow();

            // Set the new scene on the stage, effectively "logging out"
            currentStage.setScene(loginScene);
            currentStage.setTitle("Login"); // You can set your login page title
            currentStage.centerOnScreen(); // Optional: center the login window

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}