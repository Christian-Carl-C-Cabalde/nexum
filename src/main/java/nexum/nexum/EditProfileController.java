package nexum.nexum;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class EditProfileController {

    @FXML
    private Button closeButton;

    @FXML
    private VBox modalRoot; // The root VBox from edit-profile.fxml

    // This will hold a reference to the ProfileController
    private ProfileController parentController;

    /**
     * This method is called by the ProfileController to give us a reference
     * to itself, so we can tell it when we're closing.
     */
    public void setParentController(ProfileController parentController) {
        this.parentController = parentController;
    }

    /**
     * Called when the "X" button is clicked.
     */
    @FXML
    void onCloseClick(ActionEvent event) {
        // 1. Get the parent (which is the StackPane from profile.fxml)
        StackPane parentPane = (StackPane) modalRoot.getParent();

        // 2. Remove this modal (modalRoot) from the parent StackPane
        parentPane.getChildren().remove(modalRoot);

        // 3. Tell the parent controller that we have closed
        if (parentController != null) {
            parentController.closeModal();
        }
    }
}