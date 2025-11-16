package data.users;

public class User {

    private String userId;
    private String email;
    private String userFirstName;
    private String userMiddleName;
    private String userLastName;
    private String password;

    // NEW constructor that accepts middle name
    public User(String userId, String email, String userFirstName, String userMiddleName, String userLastName, String password) {
        this.userId = userId;
        this.email = email;
        this.userFirstName = userFirstName;
        this.userMiddleName = userMiddleName;
        this.userLastName = userLastName;
        this.password = password;
    }

    // If you still have older code that used the 5-arg constructor (without middle name),
    // keep this convenience constructor as well (optional):
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

    // Optionally add setters if you need to mutate fields later
}
