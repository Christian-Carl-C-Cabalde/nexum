package data.users;

import java.util.ArrayList;
import java.util.List;

/**
 * This class simulates a database for users.
 * NOTE: I am assuming your repository is structured like this based on
 * your other controllers. If you use a Map or different logic,
 * you will need to adapt the 'updateUser' method.
 */
public class UsersRepository {

    // A static list to hold all users in memory
    private static List<User> users = new ArrayList<>();

    /**
     * Adds a new user to the "database".
     * (Used by SignUpController)
     */
    public static void addUser(User user) {
        if (user != null) {
            users.add(user);
        }
    }

    /**
     * Finds a user by their email address.
     * (Used by LoginController)
     *
     * @param email The email to search for.
     * @return The User object if found, or null if not.
     */
    public static User findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }

        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null; // No user found
    }

    /**
     * (NEW METHOD)
     * Finds and updates an existing user in the list.
     * (Used by EditProfileController)
     *
     * @param updatedUser The user object containing the new information.
     */
    public static void updateUser(User updatedUser) {
        if (updatedUser == null) return;

        // Find the user to update by their ID or Email
        for (int i = 0; i < users.size(); i++) {
            // We use email as the unique key
            if (users.get(i).getEmail().equalsIgnoreCase(updatedUser.getEmail())) {
                // Replace the old user object with the updated one
                users.set(i, updatedUser);
                System.out.println("User updated in repository: " + updatedUser.getEmail());
                return; // Exit after successful update
            }
        }

        // Optional: Handle case where user isn't found
        System.out.println("Warning: updateUser could not find user: " + updatedUser.getEmail());
    }
}