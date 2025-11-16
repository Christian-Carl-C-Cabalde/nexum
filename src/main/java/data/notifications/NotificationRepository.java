package data.notifications;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NotificationRepository {

    private static final List<Notification> allNotifications = new ArrayList<>();

    /**
     * Adds a new notification to the global list.
     */
    public static void addNotification(Notification notification) {
        // Add to the top of the list (newest first)
        allNotifications.add(0, notification);
    }

    /**
     * Gets all unread notifications for a specific user.
     */
    public static List<Notification> getUnreadNotificationsForUser(String userEmail) {
        return allNotifications.stream()
                .filter(n -> n.getRecipientEmail().equals(userEmail) && !n.isRead())
                .collect(Collectors.toList());
    }

    /**
     * Gets ALL notifications for a user, for the main notification page.
     */
    public static List<Notification> getAllNotificationsForUser(String userEmail) {
        return allNotifications.stream()
                .filter(n -> n.getRecipientEmail().equals(userEmail))
                .collect(Collectors.toList());
    }
}