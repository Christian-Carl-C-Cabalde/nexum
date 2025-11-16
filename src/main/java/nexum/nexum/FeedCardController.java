package nexum.nexum;

import data.posts.Post;
import data.posts.PostRepository;
import data.users.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;       // <-- Import for Image
import javafx.scene.image.ImageView; // <-- Import for ImageView
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Controller for the FeedCard.fxml (the small card in the main feed).
 * Handles all logic for displaying post data, likes, stars,
 * and opening the detail view.
 */
public class FeedCardController {

    // --- FXML Fields ---
    @FXML private VBox postCardRoot;
    @FXML private Label authorNameLabel;
    @FXML private Label emailLabel;
    @FXML private Label contentLabel;
    @FXML private Label likeCountLabel;
    @FXML private Label commentCountLabel;
    @FXML private Button likeButton;
    @FXML private Button starButton;
    @FXML private Button optionsButton;

    @FXML private ImageView postImageView; // <-- The new ImageView for the post's image

    // --- Class Fields ---
    private Post currentPost;
    private DashboardController dashboardController;
    private User currentUser;

    /**
     * Receives all necessary data from the DashboardController.
     * This method populates the card with data.
     */
    public void setContext(Post post, DashboardController controller, User user) {
        this.currentPost = post;
        this.dashboardController = controller;
        this.currentUser = user;

        // Populate text data
        authorNameLabel.setText(post.getAuthorName());
        emailLabel.setText(post.getAuthorEmail());
        contentLabel.setText(post.getContent());

        // Populate counts
        likeCountLabel.setText(String.valueOf(post.getLikeCount()));
        commentCountLabel.setText(String.valueOf(post.getComments().size()));

        // --- NEW IMAGE LOGIC ---
        String imageUrl = post.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            // If the post has an image URL, try to load it
            try {
                Image image = new Image(imageUrl);
                postImageView.setImage(image);
                // Make the ImageView visible and part of the layout
                postImageView.setVisible(true);
                postImageView.setManaged(true);
            } catch (Exception e) {
                // If the URL is bad or file is not found
                System.err.println("Error loading image for post card: " + imageUrl);
                postImageView.setVisible(false);
                postImageView.setManaged(false);
            }
        } else {
            // No image URL, so hide the ImageView
            postImageView.setVisible(false);
            postImageView.setManaged(false);
        }
        // --- END OF IMAGE LOGIC ---

        // Update button states
        updateLikeButtonVisuals();
        updateStarButtonVisuals();

        // Show/hide the "•••" button based on post ownership
        if (currentUser != null && currentUser.getEmail().equals(currentPost.getAuthorEmail())) {
            optionsButton.setVisible(true);
        } else {
            optionsButton.setVisible(false);
        }
    }

    /**
     * Called when the "•••" options button is clicked.
     * Shows a "Delete" context menu.
     */
    @FXML
    void onOptionsClicked(ActionEvent event) {
        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.getStyleClass().add("delete-menu-item");

        deleteItem.setOnAction(e -> {
            PostRepository.deletePost(currentPost);
            dashboardController.goHome(null); // Refresh the feed
        });

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getStyleClass().add("delete-context-menu");
        contextMenu.getItems().add(deleteItem);

        // Show the menu anchored to the button
        contextMenu.show(optionsButton, Side.BOTTOM, 0, 0);
    }

    /**
     * Called when the "Like" (flame) button is clicked.
     */
    @FXML
    void onLikeClicked(ActionEvent event) {
        if (currentPost != null && currentUser != null) {
            currentPost.toggleLike(currentUser);
            updateLikeButtonVisuals();
            likeCountLabel.setText(String.valueOf(currentPost.getLikeCount()));
        }
    }

    /**
     * Called when the "Star" (save) button is clicked.
     */
    @FXML
    void onStarClicked(ActionEvent event) {
        if (currentPost != null && currentUser != null) {
            currentPost.toggleStar(currentUser);
            updateStarButtonVisuals();
        }
    }

    /**
     * Toggles the 'liked-button' CSS class.
     */
    private void updateLikeButtonVisuals() {
        if (currentUser != null && currentPost.hasLiked(currentUser.getEmail())) {
            if (!likeButton.getStyleClass().contains("liked-button")) {
                likeButton.getStyleClass().add("liked-button");
            }
        } else {
            likeButton.getStyleClass().remove("liked-button");
        }
    }

    /**
     * Toggles the 'starred-button' CSS class.
     */
    private void updateStarButtonVisuals() {
        if (currentUser != null && currentPost.hasStarred(currentUser.getEmail())) {
            if (!starButton.getStyleClass().contains("starred-button")) {
                starButton.getStyleClass().add("starred-button");
            }
        } else {
            starButton.getStyleClass().remove("starred-button");
        }
    }

    /**
     * Called when the main body of the card (the VBox) is clicked.
     * Navigates to the full detail view.
     */
    @FXML
    void onPostClicked(MouseEvent event) {
        // Prevent this from firing if the user clicked the "options" button
        if (event.getTarget() == optionsButton) {
            return;
        }

        if (dashboardController != null && currentPost != null) {
            dashboardController.showFeedDetail(currentPost);
        }
    }
}