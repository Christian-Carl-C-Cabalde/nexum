package data.posts;

import java.util.ArrayList;
import java.util.List;

/**
 * PostRepository.java
 * A static, in-memory repository to store and manage all Post objects.
 * This acts as a temporary database while the application is running.
 */
public class PostRepository {

    /**
     * This is the single, static list that holds all posts.
     * It is marked 'final' so the list itself cannot be replaced,
     * but the contents of the list can be changed (add, remove, etc.).
     */
    private static final List<Post> allPosts = new ArrayList<>();

    /**
     * Adds a new post to the central list.
     * New posts are added to the beginning (index 0) so they
     * appear at the top of the feed.
     *
     * @param post The Post object to add.
     */
    public static void addPost(Post post) {
        allPosts.add(0, post);
    }

    /**
     * Gets the complete list of all posts.
     *
     * @return A List of all Post objects.
     */
    public static List<Post> getAllPosts() {
        return allPosts;
    }

    /**
     * Removes a post from the central list.
     * This is used by the "Delete" feature.
     *
     * @param post The Post object to remove.
     */
    public static void deletePost(Post post) {
        allPosts.remove(post);
    }
}