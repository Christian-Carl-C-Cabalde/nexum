package nexum.nexum;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class PostController {

    @FXML private TextArea threadTextArea;
    @FXML private Button postButton;

    private String username;
    private String email;

    /** Initialize post with user info */
    public void setPostData(String username, String email) {
        this.username = username;
        this.email = email;
    }

    @FXML
    private void initialize() {
        // Set the default username/email if provided
        if (username != null) threadTextArea.setPromptText("Write a thread as " + username);
        // Add event to Post button
        postButton.setOnAction(e -> submitPost());
    }

    private void submitPost() {
        String content = threadTextArea.getText();
        if (content.isEmpty()) return;

        System.out.println("New post by " + username + ": " + content);
        threadTextArea.clear();
        // Here you can also add logic to save the post in memory or database
    }
}
