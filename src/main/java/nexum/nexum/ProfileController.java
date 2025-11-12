package nexum.nexum;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ProfileController {

    // This method is called when the "Edit Profile" button is clicked
    @FXML
    private void onEditProfileClick(ActionEvent event) {
        try {
            // Load the FXML file for the edit profile modal
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("edit-profile.fxml"));
            Parent root = fxmlLoader.load();

            // Create a new stage (window) for the modal
            Stage modalStage = new Stage();

            // Set the stage to be a modal, so it blocks interaction with the main window
            modalStage.initModality(Modality.APPLICATION_MODAL);

            // Remove the window decorations (title bar, close buttons, etc.)
            modalStage.initStyle(StageStyle.TRANSPARENT);

            // Create the scene and make the background transparent
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            // Add the stylesheet to the scene
            try {
                scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            } catch (NullPointerException e) {
                System.err.println("Error: 'application.css' not found for modal.");
            }

            modalStage.setScene(scene);

            // Show the modal and wait for it to be closed
            modalStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading edit-profile.fxml. Check file name and controller.");
        }
    }

    // Stub method for "Posts" button
    @FXML
    private void onPostsClick(ActionEvent event) {
        System.out.println("Posts button clicked!");
        // Add logic for showing posts
    }

    // Stub method for "Saved" button
    @FXML
    private void onSavedClick(ActionEvent event) {
        System.out.println("Saved button clicked!");
        // Add logic for showing saved items
    }
}