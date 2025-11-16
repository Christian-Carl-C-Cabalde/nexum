package nexum.nexum;

import data.posts.Comment;
import data.posts.Post;
import data.posts.PostRepository;
import data.users.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;       // <-- Import for Image
import javafx.scene.image.ImageView; // <-- Import for ImageView
import javafx.scene.layout.VBox;

/**
 * Controller for the FeedDetail.fxml view (the full post screen).
 * Handles all logic for showing post details, comments, and all interactions
 * (like, star, comment, delete).
 */
public class FeedDetailController {

    // --- FXML Fields from FeedDetail.fxml ---
    @FXML private ScrollPane commentScrollPane;
    @FXML private VBox commentListVBox;
    @FXML private TextField commentTextField;
    @FXML private Button postCommentButton;
    @FXML private Button backButton;
    @FXML private Label authorNameLabel;
    @FXML private Label emailLabel;
    @FXML private Label contentLabel;
    @FXML private Label likeCountLabel;
    @FXML private Label commentCountLabel;
    @FXML private Button likeButton;
    @FXML private Button starButton;
    @FXML private Button optionsButton; // The "..." button

    @FXML private ImageView postImageView; // <-- The ImageView for the post's image

    // --- Class Fields ---
    private Post currentPost;
    private DashboardController dashboardController;
    private User currentUser;

    /**
     * Receives all necessary data from the DashboardController when the view is loaded.
     */
    public void setContext(Post post, DashboardController controller, User user) {
        this.currentPost = post;
        this.dashboardController = controller;
        this.currentUser = user;

        // Populate basic post data
        authorNameLabel.setText(post.getAuthorName());
        emailLabel.setText(post.getAuthorEmail());
        contentLabel.setText(post.getContent());

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
                // Handle bad URL or missing file
                System.err.println("Error loading image for detail view: " + imageUrl);
                postImageView.setVisible(false);
                postImageView.setManaged(false);
            }
        } else {
            // No image URL, so hide the ImageView
            postImageView.setVisible(false);
            postImageView.setManaged(false);
        }
        // --- END OF IMAGE LOGIC ---

        // Load dynamic parts
        loadComments();
        updateCounts();
        updateLikeButtonVisuals();
        updateStarButtonVisuals();

        // Check "ownership" to show/hide the delete button
        if (currentUser != null && currentUser.getEmail().equals(currentPost.getAuthorEmail())) {
            optionsButton.setVisible(true);
        } else {
            optionsButton.setVisible(false);
        }
    }

    /**
     * Called when the "•••" options button is clicked.
     * Creates and shows a "Delete" menu.
     */
    @FXML
    void onOptionsClicked(ActionEvent event) {
        // Create a "Delete" menu item
        MenuItem deleteItem = new MenuItem("Delete");

        // Add our custom CSS class from feeds.css
        deleteItem.getStyleClass().add("delete-menu-item");

        // Set the action for the "Delete" item
        deleteItem.setOnAction(e -> {
            PostRepository.deletePost(currentPost);
            dashboardController.goHome(null); // Go back to feed after delete
        });

        // Create a context menu
        ContextMenu contextMenu = new ContextMenu();

        // Add our custom CSS class from feeds.css
        contextMenu.getStyleClass().add("delete-context-menu");

        contextMenu.getItems().add(deleteItem);

        // Show the menu anchored to the bottom of the button
        contextMenu.show(optionsButton, Side.BOTTOM, 0, 0);
    }

    /**
     * Called when the "Like" (flame) button is clicked.
     * Toggles the like state and updates UI.
     */
    @FXML
    void onLikeClicked(ActionEvent event) {
        if (currentPost != null && currentUser != null) {
            currentPost.toggleLike(currentUser); // This also triggers the notification
            updateCounts();
            updateLikeButtonVisuals();
        }
    }

    /**
     * Called when the "Star" (save) button is clicked.
     * Toggles the star state and updates UI.
     */
    @FXML
    void onStarClicked(ActionEvent event) {
        if (currentPost != null && currentUser != null) {
            currentPost.toggleStar(currentUser); // This also triggers the notification
            updateStarButtonVisuals();
        }
    }

    /**
     * Called when the "Send" button for comments is clicked.
     * Creates a new comment and refreshes the list.
     */
    @FXML
    void onPostComment(ActionEvent event) {
        String text = commentTextField.getText();
        if (text.isEmpty() || currentUser == null) return;

        String author = currentUser.getUserFirstName() + " " + currentUser.getUserLastName();
        Comment newComment = new Comment(text, author);

        // Add the comment (this also triggers the notification)
        currentPost.addComment(newComment, currentUser);

        commentTextField.clear();
        loadComments(); // Refresh the comment list
        updateCounts(); // Update the "0 Comments" label
    }

    /**
     * Refreshes the Like and Comment count labels.
     */
    private void updateCounts() {
        likeCountLabel.setText(String.valueOf(currentPost.getLikeCount()));
        commentCountLabel.setText(String.valueOf(currentPost.getComments().size()));
    }

    /**
     * Toggles the 'liked-button' CSS class on the like button.
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
     * Toggles the 'starred-button' CSS class on the star button.
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
     * Clears and rebuilds the list of comments from the Post object.
     */
    private void loadComments() {
        commentListVBox.getChildren().clear();

        if (currentPost.getComments().isEmpty()) {
            Label noCommentsLabel = new Label("No comments yet.");
            commentListVBox.getChildren().add(noCommentsLabel);
        } else {
            // Build a UI card for each comment
            for (Comment comment : currentPost.getComments()) {
                VBox commentBox = new VBox(2);
                commentBox.setPadding(new Insets(5));
                commentBox.setStyle("-fx-background-color: #f1f1f1; -fx-background-radius: 5;");

                Label authorLabel = new Label(comment.getAuthorName());
                authorLabel.setStyle("-fx-font-weight: bold;");

                Label contentLabel = new Label(comment.getContent());
                contentLabel.setWrapText(true);

                commentBox.getChildren().addAll(authorLabel, contentLabel);
                commentListVBox.getChildren().add(commentBox);
            }
        }
    }

    /**
     * Called when the "< Back to Feed" button is clicked.
     * Tells the DashboardController to go home.
     */
    @FXML
    void onBackClicked(ActionEvent event) {
        if (dashboardController != null) {
            dashboardController.goHome(null);
        }
    }
}