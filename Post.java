import java.util.ArrayList;

/**
 * Class to represent a post on the social media platform.
 */
public class Post implements Comparable<Post> {
    /**
     * The unique ID of the post.
     */
    public final String ID;
    /**
     * A global HashMap to store all the posts created IDs as key.
     */
    private static HashMap<String,Post> allPosts = new HashMap<>();
    /**
     * A HashMap to store all the users who liked the post.
     */
    private HashMap<String,User> likedUsers = new HashMap<>();
    /**
     * The author of the post.
     */
    public final User author;
    /**
     * The content of the post.
     */
    private final String content;
    /**
     * The number of likes on the post.
     */
    public int likes = 0;

    /**
     * Constructor to create a new post.
     * @param ID The ID of the post.
     * @param author The author of the post.
     * @param content The content of the post.
     */
    public Post(String ID, User author, String content) {
        this.ID = ID;
        this.author = author;
        this.content = content;
        allPosts.put(ID,this);
    }
    @Override
    public int compareTo(Post other) {
        if (this.likes != other.likes) {
            return Integer.compare(this.likes,other.likes); // Higher likes first
        }
        // If likes are equal, compare by ID (lexicographical order)
        return this.ID.compareTo(other.ID);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Post post = (Post) obj;
        return ID.equals(post.ID);
    }
    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    /**
     * Method to like a post.
     * @param user The user who is liking the post.
     * @return True if the post was liked, false if the post was unliked.
     */
    public boolean like(User user) {
        if (user.SeenPosts == null)
            user.SeenPosts = new HashMap<>();
        user.SeenPosts.put(this.ID,this);
        if (!likedUsers.contains(user.ID)) {
            likedUsers.put(user.ID,user);
            likes++;
            return true;
        }
        likedUsers.remove(user.ID);
        likes--;
        return false;
    }
    /**
     * Method to get the content of the post.
     * @return The content of the post.
     */
    public static boolean createPost(String ID, User author, String content) {
        if (!allPosts.contains(ID)) {
            Post p = new Post(ID, author, content);
            User u = User.getUser(author.ID);
            if (u.posts == null)
                u.posts = new ArrayList<>();
            u.posts.add(p);
            return true;
        }
        return false;
    }
    /**
     * Method to get the content of the post.
     * @return The content of the post.
     */
    public static Post getPost(String ID) {
        return allPosts.getValue(ID);
    }

    /**
     * Method to check if a post exists.
     * @param ID The ID of the post.
     * @return True if the post exists, false otherwise.
     */
    public static boolean isPost(String ID) {
        return allPosts.contains(ID);
    }

}