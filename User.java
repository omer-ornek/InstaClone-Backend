import java.util.ArrayList;

/**
 * Represents a User in a social media-like system.
 * Users can follow other users, create posts, see posts, and generate a personalized feed.
 */
public class User{
    /**
     * Unique identifier for the user.
     */
    public final String ID;
    /**
     * A global map of all users with their IDs as keys.
     */
    public static HashMap<String,User> allUsers = new HashMap<>();
    /**
     * Map of users this user is following, with user IDs as keys and User objects as values.
     */
    public HashMap<String,User> following;

    /**
     * Map that tracks the last seen index of posts for followed users for see_all_post commands.
     */
    public HashMap<String,Integer> indexMap = new HashMap<>();
    /**
     * List of posts created by this user.
     */
    public ArrayList<Post> posts;
    /**
     * Map of posts this user has seen wıth IDs as keys.
     */
    public HashMap<String,Post> SeenPosts;

    @Override
    public int hashCode() {
        return ID.hashCode(); // Use the hashCode of the ID field
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Check for reference equality
        if (obj == null || getClass() != obj.getClass()) return false; // Check class type
        User user = (User) obj;
        return ID.equals(user.ID); // Compare based on the ID field
    }

    /**
     * Creates a new User with the specified ID and adds them to the global map.
     * @param ID The unique identifier for the user.
     */
    User(String ID) {
        this.ID = ID;
        allUsers.put(ID,this);
    }


    /**
     * Checks if a user exists in the global map.
     * @param ID The unique identifier of the user to check.
     * @return True if the user exists, false otherwise.
     */
    public static boolean isUser(String ID) {
        return allUsers.contains(ID);
    }

    /**
     * Retrieves a user from the global map based on their ID.
     * @param ID The unique identifier of the user to retrieve.
     * @return The User object, or null if the user does not exist.
     */
    public static User getUser(String ID) {
        return allUsers.getValue(ID);
    }

    /**
     * Creates a new user if a user with the given ID does not already exist.
     * @param ID The unique identifier for the new user.
     * @return True if the user was successfully created, false otherwise.
     */
    public static boolean createUser(String ID) {
        if (!allUsers.contains(ID)) {
            new User(ID);
            return true;
        }
        return false;
    }

    /**
     * Follows the user with the specified ID if not already following them.
     * @param ID The ID of the user to follow.
     * @return True if the user was successfully followed, false otherwise.
     */
    public boolean followUser(String ID) { // follow the user with ID if not already following
        User user= allUsers.getValue(ID);
        if (user.equals(this)) return false;
        if (user == null) return false;
        if (following == null) {
            following = new HashMap<>();
        }
        if (!following.contains(ID)) {
            following.put(ID,user);
            return true;
        }
        return false;
    }

    /**
     * Unfollows the user with the specified ID if currently following them.
     * @param ID The ID of the user to unfollow.
     * @return True if the user was successfully unfollowed, false otherwise.
     */
    public boolean unfollowUser(String ID) { // unfollow the user with ID if following
        User user= allUsers.getValue(ID);
        if (user == null) return false;
        if (following == null) return false;
        if (following.contains(ID)) {
            following.remove(ID);
            return true;
        }
        return false;
    }

    /**
     * Marks a post as seen by the user.
     * @param postID The ID of the post to mark as seen.
     * @return True if the post was successfully marked as seen, false otherwise.
     */
    public boolean seePost(String  postID) {
        if (Post.isPost(postID)) {
            Post post = Post.getPost(postID);
            if (SeenPosts == null)
                SeenPosts = new HashMap<>();
            SeenPosts.put(postID,post);
            return true;
        }
        return false;
    }

    /**
     * Marks all posts of a specified user as seen.
     * @param viewedID The ID of the user whose posts are to be marked as seen.
     */
    public void seeAllPosts(String viewedID) {
        User viewed = allUsers.getValue(viewedID);
        if (viewed.posts == null) {
            return;
        }
        if (indexMap == null) {
            indexMap = new HashMap<>();
        }
        indexMap.put(viewedID,viewed.posts.size());
        //to avoid putting all posts to viewedPosts set of viewer a small improvement
    }

    /**
     * Creates a feed of posts from followed users.
     * @return A list of posts from followed users, excluding already seen posts.
     */
    public ArrayList<Post> createFeed() {
        ArrayList<Post> feed = new ArrayList<>();
        if (following == null) return feed;
        for (String s : following) {
            User u = following.getValue(s);
            if (u.posts == null) continue;
            int i = 0;
            if (indexMap.getValue(u.ID) != null) {
                i = indexMap.getValue(u.ID);
            }
            for (; i < u.posts.size(); i++) {
                Post post = u.posts.get(i);
                if (SeenPosts == null || !SeenPosts.contains(post.ID))
                    feed.add(post);
            }
        }
        return feed;
    }

    /**
     * Generates a sorted feed of posts based on their relevance.
     * @param num The maximum number of posts to include in the feed.
     * @return A list of the top relevant posts from the feed.
     */
    public ArrayList<Post> generateFeed(int num) {
        ArrayList<Post> feed = createFeed();
        MaxHeap heap = new MaxHeap(feed);
        ArrayList<Post> result = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            if (heap.isEmpty()) break;
            result.add(heap.deleteMax());
        }
        return result;
    }

    /**
     * Sorts the user's own posts based on their relevance.
     * @return A sorted list of the user's posts.
     */
    public ArrayList<Post> sortPosts() {
        if (posts == null) return new ArrayList<>();
        MaxHeap heap = new MaxHeap(posts);
        ArrayList<Post> result = new ArrayList<>();
        while (!heap.isEmpty()) {
            result.add(heap.deleteMax());
        }
        return result;
    }

}
