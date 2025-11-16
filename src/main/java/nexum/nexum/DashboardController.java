package nexum.nexum;

// Import all necessary Java, JavaFX, and data classes
import data.posts.Post;
import data.posts.PostRepository;
import data.users.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * DashboardController.java
 * The main controller for the application after login.
 * It manages navigation between the home feed, create post, profile, and notification views.
 */
public class DashboardController {

    // FXML fields from dashboard.fxml
    @FXML private StackPane mainContent;
    @FXML private Button goHome;
    @FXML private Button goCreate;
    @FXML private Button goNotification;
    @FXML private Button goProfile;
    @FXML private Button logout;

    private List<Button> navButtons;
    private User currentUser;

    /**
     * Receives the logged-in user from the LoginController.
     */
    public void setUser(User user) {
        this.currentUser = user;
    }

    /**
     * Called when the FXML is loaded.
     * Sets up the nav button list and loads the home feed.
     */
    @FXML
    public void initialize() {
        navButtons = Arrays.asList(goHome, goCreate, goNotification, goProfile);

        // Load the home feed on startup
        goHome(null);
    }

    /**
     * Helper method to set the ".active" style class on the sidebar buttons.
     */
    private void setActiveButton(Button activeButton) {
        for (Button button : navButtons) {
            if (button == activeButton) {
                if (!button.getStyleClass().contains("active")) {
                    button.getStyleClass().add("active");
                }
            } else {
                button.getStyleClass().remove("active");
            }
        }
    }

    /**
     * Helper method to load simple views that don't need data.
     * (This is no longer used by goNotification, but is good to keep)
     */
    private void loadView(String fxmlFile) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlFile));
            mainContent.getChildren().clear();
            mainContent.getChildren().add(view);
        } catch (IOException e) {
            e.printStackTrace();
            mainContent.getChildren().clear();
            mainContent.getChildren().add(new Label("Error: Could not load " + fxmlFile));
        }
    }

    /**
     * Loads the post.fxml view.
     * It passes the current user and this dashboard controller to the PostController.
     */
    @FXML
    void goCreate(ActionEvent event) {
        if (currentUser == null) {
            System.err.println("Error: No user is logged in to create a post!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("post.fxml"));
            Parent postView = loader.load();
            PostController postController = loader.getController();

            // Pass context to the PostController
            postController.setContext(this.currentUser, this);

            mainContent.getChildren().clear();
            mainContent.getChildren().add(postView);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setActiveButton(goCreate);
    }

    /**
     * Loads the home feed.
     * It loops through all posts in PostRepository and loads a FeedCard.fxml for each one.
     */
    @FXML
    void goHome(ActionEvent event) {
        mainContent.getChildren().clear();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        VBox feedVBox = new VBox(15); // 15px spacing
        feedVBox.setPadding(new Insets(20));
        feedVBox.setStyle("-fx-background-color: #f0f2f5;"); // Light gray background

        List<Post> allPosts = PostRepository.getAllPosts();

        if (allPosts.isEmpty()) {
            Label emptyLabel = new Label("No posts yet. Go create one!");
            emptyLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #888;");
            feedVBox.getChildren().add(emptyLabel);
        } else {
            // Loop for each post
            for (Post post : allPosts) {
                try {
                    // 1. Create a loader for the feed card template
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FeedCard.fxml"));
                    Parent postCardNode = loader.load();

                    // 2. Get the controller for that specific card
                    FeedCardController controller = loader.getController();

                    // 3. Pass the post, this dashboard, AND the current user
                    // This allows the card to be clickable and interactive
                    controller.setContext(post, this, this.currentUser);

                    // 4. Add the finished card to the feed
                    feedVBox.getChildren().add(postCardNode);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        scrollPane.setContent(feedVBox);
        mainContent.getChildren().add(scrollPane);

        // Set the active button
        if (event != null) {
            setActiveButton(goHome); // Clicked by user
        } else {
            setActiveButton(goHome); // Called from initialize()
        }
    }

    /**
     * Loads the notification.fxml view
     * and passes the logged-in user.
     */
    @FXML
    void goNotification(ActionEvent event) {
        if (currentUser == null) {
            System.err.println("Error: No user is logged in!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("notification.fxml"));
            Parent view = loader.load();

            // Get the controller
            NotificationController controller = loader.getController();

            // Pass the user to it
            controller.setContext(currentUser);

            mainContent.getChildren().clear();
            mainContent.getChildren().add(view);

        } catch (IOException e) {
            e.printStackTrace();
        }

        setActiveButton(goNotification);
    }

    /**
     * Loads the profile.fxml view.
     * It passes the current user AND this controller to the ProfileController.
     */
    @FXML
    void goProfile(ActionEvent event) {
        if (currentUser == null) {
            System.err.println("Error: No user is logged in to show profile!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("profile.fxml"));
            Parent profileView = loader.load();

            ProfileController profileController = loader.getController();

            // Pass user data AND this dashboard controller to the profile
            profileController.setContext(this.currentUser, this);

            mainContent.getChildren().clear();
            mainContent.getChildren().add(profileView);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setActiveButton(goProfile);
    }

    /**
     * Loads the FeedDetail.fxml view.
     * This is called by FeedCardController when a card is clicked.
     */
    public void showFeedDetail(Post post) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FeedDetail.fxml"));
            Parent postDetailView = loader.load();

            FeedDetailController detailController = loader.getController();

            // Pass the post, this controller (for back button), and user (for commenting)
            detailController.setContext(post, this, this.currentUser);

            mainContent.getChildren().clear();
            mainContent.getChildren().add(postDetailView);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // We don't change the active sidebar button, as we are in a sub-view of "Home"
    }

    /**
     * Logs the user out and returns to the login.fxml screen.
     */
    @FXML
    void logout(ActionEvent event) {
        try {
            Parent loginView = FXMLLoader.load(getClass().getResource("login.fxml"));
            Scene loginScene = new Scene(loginView);
            Stage currentStage = (Stage) logout.getScene().getWindow();
            currentStage.setScene(loginScene);
            currentStage.setTitle("Login");
            currentStage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}