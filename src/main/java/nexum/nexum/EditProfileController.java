package nexum.nexum;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class EditProfileController {

    // This method is called when the 'X' button in the modal is clicked
    @FXML
    private void onCloseClick(ActionEvent event) {
        // Get the source of the event (the 'X' button)
        Node source = (Node) event.getSource();

        // Get the stage (window) that the button belongs to
        Stage stage = (Stage) source.getScene().getWindow();

        // Close the stage
        stage.close();
    }
}