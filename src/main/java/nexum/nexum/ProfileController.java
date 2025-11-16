package nexum.nexum;

import data.posts.Post;
import data.posts.PostRepository;
import data.users.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

/**
 * Controller for the profile.fxml view.
 * Displays the user's information and manages the "Posts" and "Saved" tabs.
 */
public class ProfileController {

    // --- FXML Fields from profile.fxml ---
    @FXML private Label nameLabel;
    @FXML private Label emailLabel;
    @FXML private Button editProfileButton;
    @FXML private Button postsBtn;
    @FXML private Button savedBtn;
    @FXML private VBox postContainer;    // The VBox inside the ScrollPane
    @FXML private StackPane profileRoot;  // The root StackPane for the modal

    // --- Class Fields ---
    private User currentUser;
    private DashboardController dashboardController;

    /**
     * This method receives the user and dashboard from the DashboardController.
     * It's called when the user navigates to the profile.
     */
    public void setContext(User user, DashboardController controller) {
        this.currentUser = user;
        this.dashboardController = controller;

        // Set the user's name and email in the UI
        nameLabel.setText(user.getUserFirstName() + " " + user.getUserLastName());
        emailLabel.setText(user.getEmail());

        // Load the user's own posts by default
        loadUserPosts();
    }

    /**
     * Called when the "Posts" tab is clicked.
     * Loads the user's own posts.
     */
    @FXML
    void onPostsClick(ActionEvent event) {
        postsBtn.getStyleClass().add("active");
        savedBtn.getStyleClass().remove("active");

        loadUserPosts();
    }

    /**
     * Called when the "Saved" tab is clicked.
     * Loads the posts the user has starred.
     */
    @FXML
    void onSavedClick(ActionEvent event) {
        savedBtn.getStyleClass().add("active");
        postsBtn.getStyleClass().remove("active");

        // This is the new logic for Step 7
        loadSavedPosts();
    }

    /**
     * This method filters and displays ONLY the user's own posts.
     */
    private void loadUserPosts() {
        postContainer.getChildren().clear();
        if (currentUser == null) return;

        String currentUserName = currentUser.getUserFirstName() + " " + currentUser.getUserLastName();
        List<Post> allPosts = PostRepository.getAllPosts();

        boolean hasPosts = false;
        for (Post post : allPosts) {
            // FILTER: Show posts where author name matches
            if (post.getAuthorName().equals(currentUserName)) {
                loadFeedCard(post); // Use helper method
                hasPosts = true;
            }
        }

        if (!hasPosts) {
            postContainer.getChildren().add(new Label("You haven't created any posts yet."));
        }
    }

    /**
     * NEW: This method filters and displays ONLY the user's starred (saved) posts.
     */
    private void loadSavedPosts() {
        postContainer.getChildren().clear();
        if (currentUser == null) return;

        List<Post> allPosts = PostRepository.getAllPosts();

        boolean hasSaved = false;
        for (Post post : allPosts) {
            // FILTER: Show posts where the user is in the "starringUsers" list
            if (post.hasStarred(currentUser.getEmail())) {
                loadFeedCard(post); // Use same helper method
                hasSaved = true;
            }
        }

        if (!hasSaved) {
            postContainer.getChildren().add(new Label("You haven't saved any posts yet."));
        }
    }

    /**
     * NEW: Helper method to load a FeedCard, used by both loadUserPosts and loadSavedPosts.
     * This avoids code duplication.
     */
    private void loadFeedCard(Post post) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FeedCard.fxml"));
            Parent postCardNode = loader.load();

            FeedCardController controller = loader.getController();

            // Pass all context so the card is clickable and like/star-able
            controller.setContext(post, dashboardController, currentUser);

            postContainer.getChildren().add(postCardNode);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when the "Edit Profile" button is clicked.
     * Loads and displays the EditProfile.fxml as a modal.
     */
    @FXML
    void showEditProfileModal(ActionEvent event) {
        if (currentUser == null) return;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit-profile.fxml"));
            Parent modalView = loader.load();

            EditProfileController controller = loader.getController();
            controller.setParentController(this);
            controller.loadUserData(currentUser);

            profileRoot.getChildren().add(modalView); // Add modal on top
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is called by the EditProfileController when it closes.
     */
    public void closeModal() {
        // Find and remove the modal (which is the last-added child)
        if (profileRoot.getChildren().size() > 1) {
            profileRoot.getChildren().remove(profileRoot.getChildren().size() - 1);
        }
    }
}