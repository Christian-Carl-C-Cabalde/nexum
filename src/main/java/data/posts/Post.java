package data.posts;

import data.notifications.Notification;
import data.notifications.NotificationRepository;
import data.users.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Post {

    private String content;
    private String authorName;
    private String authorEmail;
    private String imageUrl; // <-- 1. ADD THIS FIELD
    private LocalDateTime timestamp;
    private List<Comment> comments;
    private List<String> likers;
    private List<String> starringUsers;

    /**
     * 2. UPDATE THE CONSTRUCTOR
     */
    public Post(String content, String authorName, String authorEmail, String imageUrl) {
        this.content = content;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.imageUrl = imageUrl; // <-- 3. SET THE IMAGE URL
        this.timestamp = LocalDateTime.now();
        this.comments = new ArrayList<>();
        this.likers = new ArrayList<>();
        this.starringUsers = new ArrayList<>();
    }

    // --- Getters ---
    public String getContent() { return content; }
    public String getAuthorName() { return authorName; }
    public String getAuthorEmail() { return authorEmail; }
    public String getImageUrl() { return imageUrl; } // <-- 4. ADD GETTER

    // ... (All other methods: getLikeCount, toggleLike, addComment, etc. are unchanged) ...
    public int getLikeCount() { return likers.size(); }
    public boolean hasLiked(String userEmail) { return likers.contains(userEmail); }
    public void toggleLike(User actor) {
        String actorEmail = actor.getEmail();
        if (hasLiked(actorEmail)) {
            likers.remove(actorEmail);
        } else {
            likers.add(actorEmail);
            if (!actorEmail.equals(this.authorEmail)) {
                Notification n = new Notification(this.authorEmail, actor.getUserFirstName() + " " + actor.getUserLastName(), Notification.NotificationType.LIKE, this.content);
                NotificationRepository.addNotification(n);
            }
        }
    }
    public boolean hasStarred(String userEmail) { return starringUsers.contains(userEmail); }
    public void toggleStar(User actor) {
        String actorEmail = actor.getEmail();
        if (hasStarred(actorEmail)) {
            starringUsers.remove(actorEmail);
        } else {
            starringUsers.add(actorEmail);
            if (!actorEmail.equals(this.authorEmail)) {
                Notification n = new Notification(this.authorEmail, actor.getUserFirstName() + " " + actor.getUserLastName(), Notification.NotificationType.STAR, this.content);
                NotificationRepository.addNotification(n);
            }
        }
    }
    public List<Comment> getComments() { return comments; }
    public void addComment(Comment comment, User actor) {
        this.comments.add(comment);
        if (!actor.getEmail().equals(this.authorEmail)) {
            Notification n = new Notification(this.authorEmail, actor.getUserFirstName() + " " + actor.getUserLastName(), Notification.NotificationType.COMMENT, this.content);
            NotificationRepository.addNotification(n);
        }
    }
}