package data.notifications;

import java.time.LocalDateTime;

public class Notification {

    // Enum to define the type of notification
    public enum NotificationType {
        LIKE,
        COMMENT,
        STAR
    }

    private String recipientEmail; // Who is this for?
    private String actorName;      // Who did the action?
    private NotificationType type; // What action?
    private String postSnippet;    // A short preview of the post
    private LocalDateTime timestamp;
    private boolean isRead;

    public Notification(String recipientEmail, String actorName, NotificationType type, String postSnippet) {
        this.recipientEmail = recipientEmail;
        this.actorName = actorName;
        this.type = type;
        this.postSnippet = (postSnippet.length() > 40) ? postSnippet.substring(0, 40) + "..." : postSnippet;
        this.timestamp = LocalDateTime.now();
        this.isRead = false;
    }

    // --- Getters ---
    public String getRecipientEmail() { return recipientEmail; }
    public String getActorName() { return actorName; }
    public NotificationType getType() { return type; }
    public String getPostSnippet() { return postSnippet; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public boolean isRead() { return isRead; }

    public void setRead(boolean read) { isRead = read; }
}