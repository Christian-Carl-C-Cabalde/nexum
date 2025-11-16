package nexum.nexum;

import data.posts.Post;
import data.posts.PostRepository;
import data.users.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser; // <-- For picking image files
import java.io.File; // <-- For handling the image file

/**
 * Controller for the post.fxml view.
 * Handles creating a new post, including attaching an image.
 */
public class PostController {

    // --- FXML Fields from post.fxml ---
    @FXML private TextArea threadTextArea;
    @FXML private Button postButton;
    @FXML private Label usernameLabel;
    @FXML private Label emailLabel;

    // --- New FXML Fields for Image Upload ---
    @FXML private Button attachImageButton;
    @FXML private VBox imagePreviewContainer;
    @FXML private ImageView imagePreview;
    @FXML private Button removeImageButton;

    // --- Class Fields ---
    private User currentUser;
    private DashboardController dashboardController;
    private File selectedImageFile; // Stores the chosen image file

    /**
     * Receives the user and dashboard from the DashboardController.
     */
    public void setContext(User user, DashboardController controller) {
        this.currentUser = user;
        this.dashboardController = controller;
        if (currentUser != null) {
            usernameLabel.setText(currentUser.getUserFirstName() + " " + currentUser.getUserLastName());
            emailLabel.setText(currentUser.getEmail());
            threadTextArea.setPromptText("Write a thread as " + currentUser.getUserFirstName());
        }
    }

    /**
     * Called when the FXML is loaded.
     * Hides the image preview container by default.
     */
    @FXML
    private void initialize() {
        postButton.setOnAction(e -> submitPost());
        // Hide image preview until an image is selected
        imagePreviewContainer.setVisible(false);
        imagePreviewContainer.setManaged(false);
    }

    /**
     * Called when the "Image" icon button is clicked.
     * Opens a FileChooser to select an image.
     */
    @FXML
    void onAttachImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");

        // Set filters to only allow image files
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        // Show the open dialog
        File file = fileChooser.showOpenDialog(attachImageButton.getScene().getWindow());

        if (file != null) {
            this.selectedImageFile = file;
            // Show preview
            // We must convert the file path to a URI string for the Image constructor
            Image image = new Image(file.toURI().toString());
            imagePreview.setImage(image);

            // Make the preview container visible
            imagePreviewContainer.setVisible(true);
            imagePreviewContainer.setManaged(true);
        }
    }

    /**
     * Called when the "Remove Image" button is clicked.
     * Clears the image selection and hides the preview.
     */
    @FXML
    void onRemoveImage(ActionEvent event) {
        selectedImageFile = null;
        imagePreview.setImage(null);
        imagePreviewContainer.setVisible(false);
        imagePreviewContainer.setManaged(false);
    }

    /**
     * Called when the "Post" button is clicked.
     * Gathers all data (text and image) and creates a new Post.
     */
    @FXML
    void submitPost() {
        String content = threadTextArea.getText();

        // Check for empty content (or you could allow image-only posts)
        if (content.isEmpty() || currentUser == null || dashboardController == null) {
            System.err.println("Error: Post content is empty or controller context is not set.");
            return;
        }

        String authorName = currentUser.getUserFirstName() + " " + currentUser.getUserLastName();
        String authorEmail = currentUser.getEmail();

        // --- Get the image URL ---
        String imageUrl = null;
        if (selectedImageFile != null) {
            // Convert the file path to a string URL that JavaFX can read
            imageUrl = selectedImageFile.toURI().toString();
        }

        // --- Create the new Post using the 4-argument constructor ---
        Post newPost = new Post(content, authorName, authorEmail, imageUrl);

        // Add post to the central "database"
        PostRepository.addPost(newPost);

        // Clear the form
        threadTextArea.clear();
        onRemoveImage(null); // Clear the image preview

        // Go back to the home feed
        dashboardController.goHome(null);
    }
}