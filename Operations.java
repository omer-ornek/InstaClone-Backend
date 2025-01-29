import java.util.ArrayList;
/**
 * Class to perform operations on the social media platform.
 */
public class Operations {
    /**
     * Creates a new user with the given ID.
     * @param ID The ID of the user to be created.
     * @return A string indicating the result of the operation.
     */
    public static String createUser(String ID) {
        StringBuilder result = new StringBuilder();
        if (User.createUser(ID)) {
            result.append("Created user with Id ").append(ID).append(".");
        } else {
            result.append("Some error occurred in create_user.");
        }
        return result.toString();
    }

    /**
     * Follows the user with ID2 from the user with ID1.
     * @param ID1 The ID of the user who will follow.
     * @param ID2 The ID of the user who will be followed.
     * @return A string indicating the result of the operation.
     */
    public static String followUser(String ID1, String ID2) {
        if (User.isUser(ID1) && User.isUser(ID2)) {
            User user1 = User.getUser(ID1);
            if (user1.followUser(ID2)) {
                StringBuilder result = new StringBuilder();
                result.append(ID1).append(" followed ").append(ID2).append(".");
                return result.toString();
            }
        }
        return "Some error occurred in follow_user.";
    }

    /**
        * Unfollows the user with ID2 from the user with ID1.
        * @param ID1 The ID of the user who will unfollow.
        * @param ID2 The ID of the user who will be unfollowed.
        * @return A string indicating the result of the operation.
        */
    public static String unfollowUser(String ID1 , String ID2) {
        if ( User.isUser(ID1) && User.isUser(ID2)) {
            User user1 = User.getUser(ID1);
            if (user1.unfollowUser(ID2)) {
                StringBuilder result = new StringBuilder();
                result.append(ID1).append(" unfollowed ").append(ID2).append(".");
                return result.toString();
            }
        }
        return  "Some error occurred in unfollow_user.";
    }

    /**
     * Creates a new post with the given ID, author ID, and content.
     * @param ID The ID of the post to be created.
     * @param authorID The ID of the author of the post.
     * @param content The content of the post.
     * @return A string indicating the result of the operation.
     */
    public static String createPost(String ID, String authorID, String content) {
        if (User.isUser(authorID)) {
            User author = User.getUser(authorID);
            if (Post.createPost(ID, author, content)) {
                StringBuilder result = new StringBuilder();
                result.append(authorID).append(" created a post with Id ").append(ID).append(".");
                return  result.toString();
            }
        }
        return "Some error occurred in create_post.";
    }

    /**
     * Marks the post with postID as seen by the user with userID.
     * @param userID The ID of the user who will see the post.
     * @param postID The ID of the post to be seen.
     * @return A string indicating the result of the operation.
     */
    public static String seePost(String userID, String postID) {
        if (User.isUser(userID)) {
            User user = User.getUser(userID);
            if (user.seePost(postID)) {
                StringBuilder result = new StringBuilder();
                result.append(userID).append(" saw ").append(postID).append(".");
                return result.toString();
            }
        }
        return "Some error occurred in see_post.";
    }

    /**
     * Marks all posts of the user with viewedID as seen by the user with viewerID.
     * @param viewerID The ID of the user who will see all posts.
     * @param viewedID The ID of the user whose posts will be seen.
     * @return A string indicating the result of the operation.
     */
    public static String seeAllPosts(String viewerID, String viewedID) {
        if(User.isUser(viewerID) && User.isUser(viewedID)) {
            User viewer = User.getUser(viewerID);
            viewer.seeAllPosts(viewedID);
            StringBuilder result = new StringBuilder();
            result.append(viewerID).append(" saw all posts of ").append(viewedID).append(".");
            return result.toString();
        }
        return "Some error occurred in see_all_posts_from_user.";
    }

    /**
     * Toggles the like status of the post with postID by the user with userID.
     * @param userID The ID of the user who will toggle the like status.
     * @param postID The ID of the post whose like status will be toggled.
     * @return A string indicating the result of the operation.
     */
    public static String toggleLike(String userID, String postID) {
        if (User.isUser(userID) && Post.isPost(postID)) {
            User user = User.getUser(userID);
            Post post = Post.getPost(postID);
            StringBuilder result = new StringBuilder();
            if (post.like(user))
                result.append(userID).append(" liked ").append(postID).append(".");
            else
                result.append(userID).append(" unliked ").append(postID).append(".");
            return result.toString();
        }
        return "Some error occurred in toggle_like.";
    }

    /**
     * Generates a feed of posts for the user with userID.
     * @param userID The ID of the user for whom the feed will be generated.
     * @param num The number of posts to be generated.
     * @return An ArrayList of strings indicating the result of the operation.
     */
    public static ArrayList<String > generateFeed(String userID, int num) {
        ArrayList<String> result = new ArrayList<>();
        if (User.isUser(userID)) {
            User user = User.getUser(userID);
            ArrayList<Post> feed = user.generateFeed(num);
            StringBuilder res;
            res = new StringBuilder();
            res.append("Feed for ").append(userID).append(":");
            result.add(res.toString());
            for (int i = 0; i < feed.size(); i++) {
                res = new StringBuilder();
                res.append("Post ID: ").append(feed.get(i).ID).append(", Author: ").
                        append(feed.get(i).author.ID).append(", Likes: ").append(feed.get(i).likes);
                result.add(res.toString());
            }
            if (feed.size() < num)
                result.add("No more posts available for "+ userID + ".");
            return result;
        }
        result.add("Some error occurred in generate_feed.");
        return result;
    }

    /**
     * Simulates a user scrolling through the feed.
     * @param userID The ID of the user who will scroll through the feed.
     * @param num The number of posts to be scrolled through.
     * @param nums An array of integers indicating the action to be taken for each post.
     * @return An ArrayList of strings indicating the result of the operation.
     */
    public static ArrayList<String> scrollThroughFeed(String userID, int num, int[] nums) {
        ArrayList<String> result = new ArrayList<>();
        if (User.isUser(userID)) {
            StringBuilder res;
            res = new StringBuilder();
            res.append(userID).append(" is scrolling through feed:");
            result.add(res.toString());
            User user = User.getUser(userID);
            ArrayList<Post> feed = user.generateFeed(num);
            for (int i=0; i < feed.size();i++) {
                if (nums[i] == 0) {// only sees the post
                    user.seePost(feed.get(i).ID);
                    res = new StringBuilder();
                    res.append(userID).append(" saw ").append(feed.get(i).ID).append(" while scrolling.");
                    result.add(res.toString());
                }
                if (nums [i] == 1) { // likes the post
                    if (feed.get(i).like(user)) {
                        res = new StringBuilder();
                        res.append(userID).append(" saw ").append(feed.get(i).ID).append(" while scrolling and clicked the like button.");
                        result.add(res.toString());
                    }
                }
            }
            if (feed.size() < num) {
                result.add("No more posts in feed.");
            }
            return result;
        }
        result.add("Some error occurred in scroll_through_feed.");
        return result;
    }

    /**
     * Sorts the posts of the user with userID.
     * @param userID The ID of the user whose posts will be sorted.
     * @return An ArrayList of strings indicating the result of the operation.
     */
    public static ArrayList<String> sortPosts(String userID) {
        ArrayList<String> result = new ArrayList<>();
        if (User.isUser(userID)) {
            User user = User.getUser(userID);
            StringBuilder res;
            if (user.posts == null) {
                res = new StringBuilder();
                res.append("No posts from ").append(userID).append(".");
                result.add(res.toString());
            }
            else {
                res = new StringBuilder();
                res.append("Sorting ").append(userID).append("'s posts:");
                result.add(res.toString());
                ArrayList<Post> sortedPosts = user.sortPosts();
                for (Post post : sortedPosts) {
                    res = new StringBuilder();
                    res.append(post.ID).append(", Likes: ").append(post.likes);
                    result.add(res.toString());
                }
            }
            return result;
        }
        result.add("Some error occurred in sort_posts.");
        return result;
    }
}
