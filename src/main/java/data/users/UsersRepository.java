package data.users;

import java.util.ArrayList;

/**
 * Simple in-memory repository for users.
 * Uses the User model (not UsersData).
 */
public class UsersRepository {
    public static ArrayList<User> users = new ArrayList<>();

    public static void addUser(User user) {
        users.add(user);
    }

    public static User findByEmail(String email) {
        if (email == null) return null;
        for (User u : users) {
            if (u.getEmail() != null && u.getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        return null;
    }
}
