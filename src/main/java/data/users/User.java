package data.users;

import java.io.Serializable;

public class User implements Serializable {

    private String userId;
    private String email;
    private String userFirstName;
    private String userMiddleName;
    private String userLastName;
    private String password;

    public User(String userId, String email, String firstName, String middleName, String lastName, String password) {
        this.userId = userId;
        this.email = email;
        this.userFirstName = firstName;
        this.userMiddleName = middleName;
        this.userLastName = lastName;
        this.password = password;
    }

    public String getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getUserFirstName() { return userFirstName; }
    public String getUserMiddleName() { return userMiddleName; }
    public String getUserLastName() { return userLastName; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
