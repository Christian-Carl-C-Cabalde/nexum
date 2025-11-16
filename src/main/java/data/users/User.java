package data.users;

public class User {

    private String userId;
    private String email;
    private String userFirstName;
    private String userMiddleName;
    private String userLastName;
    private String password;

    // Constructor with middle name
    public User(String userId, String email, String userFirstName, String userMiddleName, String userLastName, String password) {
        this.userId = userId;
        this.email = email;
        this.userFirstName = userFirstName;
        this.userMiddleName = userMiddleName;
        this.userLastName = userLastName;
        this.password = password;
    }

    // Convenience constructor without middle name
    public User(String userId, String email, String userFirstName, String userLastName, String password) {
        this(userId, email, userFirstName, "", userLastName, password);
    }

    // Getters
    public String getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getUserFirstName() { return userFirstName; }
    public String getUserMiddleName() { return userMiddleName; }
    public String getUserLastName() { return userLastName; }
    public String getPassword() { return password; }

    // --- Added this so you can update the password ---
    public void setPassword(String password) {
        this.password = password;
    }
}
