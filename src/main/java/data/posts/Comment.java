package data.posts; // Or whatever package your Post.java is in

import java.time.LocalDateTime;

public class Comment {
    private String content;
    private String authorName;
    private LocalDateTime timestamp;

    public Comment(String content, String authorName) {
        this.content = content;
        this.authorName = authorName;
        this.timestamp = LocalDateTime.now();
    }

    // --- Getters ---
    public String getContent() {
        return content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}