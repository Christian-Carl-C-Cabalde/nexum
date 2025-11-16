package nexum.nexum;

import data.notifications.Notification;
import data.notifications.NotificationRepository;
import data.users.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.List;

public class NotificationController {

    @FXML
    private VBox notificationListVBox; // The container from the FXML

    private User currentUser;

    /**
     * This method is called by the DashboardController to pass in the user.
     */
    public void setContext(User user) {
        this.currentUser = user;
        loadNotifications();
    }

    /**
     * Fetches notifications and builds the UI.
     */
    private void loadNotifications() {
        if (currentUser == null) return;

        notificationListVBox.getChildren().clear();

        List<Notification> notifications = NotificationRepository.getAllNotificationsForUser(currentUser.getEmail());

        if (notifications.isEmpty()) {
            notificationListVBox.getChildren().add(new Label("You have no notifications."));
        } else {
            for (Notification n : notifications) {
                // For each notification, create a card
                HBox card = createNotificationCard(n);
                notificationListVBox.getChildren().add(card);

                // Mark as read (for future use)
                n.setRead(true);
            }
        }
    }

    /**
     * Helper method to create a single notification UI card.
     */
    private HBox createNotificationCard(Notification n) {
        HBox card = new HBox(10);
        card.getStyleClass().add("notification-card");

        // =========================================================
        // THIS IS THE FIX:
        // Removed the leading "/" from the image paths
        // =========================================================
        String iconPath = "image/flame.png"; // Default
        if (n.getType() == Notification.NotificationType.COMMENT) {
            iconPath = "image/comment.png";
        } else if (n.getType() == Notification.NotificationType.STAR) {
            iconPath = "image/star.png";
        }

        // This line will no longer throw an error
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(iconPath)));
        icon.setFitHeight(20);
        icon.setFitWidth(20);
        icon.getStyleClass().add("notification-icon");

        // 3. Build the text with style classes
        Text actor = new Text(n.getActorName() + " ");
        actor.getStyleClass().add("notification-text-actor");

        String actionText = "liked";
        if (n.getType() == Notification.NotificationType.COMMENT) {
            actionText = "commented on";
        } else if (n.getType() == Notification.NotificationType.STAR) {
            actionText = "saved";
        }

        Text action = new Text(actionText + " your post: ");
        action.getStyleClass().add("notification-text-action");

        Text snippet = new Text("'" + n.getPostSnippet() + "'");
        snippet.getStyleClass().add("notification-text-snippet");

        // Use TextFlow to combine styled text
        TextFlow textFlow = new TextFlow(actor, action, snippet);
        textFlow.getStyleClass().add("notification-text-flow");

        card.getChildren().addAll(icon, textFlow);
        return card;
    }
}