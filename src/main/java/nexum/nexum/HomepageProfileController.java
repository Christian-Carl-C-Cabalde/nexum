package nexum.nexum;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomepageProfileController {

    @FXML
    private Button postsButton;

    @FXML
    private Button savedButton;

    @FXML
    protected void onPostsClick() {
        // Set Posts button to selected style
        postsButton.getStyleClass().setAll("tab-button-selected");

        // Set Saved button to unselected style
        savedButton.getStyleClass().setAll("tab-button");

        // You can add logic here to show the user's posts
        System.out.println("Posts button clicked");
    }

    @FXML
    protected void onSavedClick() {
        // Set Posts button to unselected style
        postsButton.getStyleClass().setAll("tab-button");

        // Set Saved button to selected style
        savedButton.getStyleClass().setAll("tab-button-selected");

        // You can add logic here to show the user's saved items
        System.out.println("Saved button clicked");
    }
}