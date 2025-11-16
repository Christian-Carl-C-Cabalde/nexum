package data.users;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsersData {
    private static final String FILE_PATH = "users.dat";
    private static List<User> users = new ArrayList<>();

    static {
        loadUsers();
    }

    public static void addUser(User user) {
        users.add(user);
        saveUsers();
    }

    public static User getUserByEmail(String email) {
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        return null;
    }

    private static void saveUsers() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            out.writeObject(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadUsers() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            users = (List<User>) in.readObject();
        } catch (Exception ignored) {
            users = new ArrayList<>();
        }
    }
}
