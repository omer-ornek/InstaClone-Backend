import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java Main <input_file> <output_file>");
            return;
        }
        String inputFileName = args[0];
        String outputFileName = args[1];
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            String line;
            while((line =reader.readLine())!=null) {
                String[] words = line.split(" ");
                String action = words[0];
                String user1,user2,postID,content;
                int num;
                switch (action) {
                    case "create_user":
                        user1 = words[1];
                        writer.write(Operations.createUser(user1));
                        writer.newLine();
                        break;
                    case "create_post":
                        user1 = words[1];
                        postID = words[2];
                        content = words[3];
                        writer.write(Operations.createPost(postID,user1,content));
                        writer.newLine();
                        break;
                    case "follow_user":
                        user1 = words[1];
                        user2 = words[2];
                        writer.write(Operations.followUser(user1,user2));
                        writer.newLine();
                        break;
                    case "unfollow_user":
                        user1 = words[1];
                        user2 = words[2];
                        writer.write(Operations.unfollowUser(user1,user2));
                        writer.newLine();
                        break;
                    case "see_post":
                        user1 = words[1];
                        postID = words[2];
                        writer.write(Operations.seePost(user1,postID));
                        writer.newLine();
                        break;
                    case "see_all_posts_from_user":
                        user1 = words[1]; //viewer
                        user2 = words[2]; //viewed
                        writer.write(Operations.seeAllPosts(user1,user2));
                        writer.newLine();
                        break;
                    case "toggle_like":
                        user1 = words[1];
                        postID = words[2];
                        writer.write(Operations.toggleLike(user1,postID));
                        writer.newLine();
                        break;
                    case "generate_feed":
                        user1 = words[1];
                        num = Integer.parseInt(words[2]);
                        ArrayList<String> feed = Operations.generateFeed(user1,num);
                        for (String s : feed) {
                            writer.write(s);
                            writer.newLine();
                        }
                        break;
                    case "scroll_through_feed":
                        user1 = words[1];
                        num = Integer.parseInt(words[2]);
                        int[] nums = new int[num];
                        for (int i = 0; i < num; i++) {
                            nums[i] = Integer.parseInt(words[i+3]);
                        }
                        for (String s : Operations.scrollThroughFeed(user1,num,nums)) {
                            writer.write(s);
                            writer.newLine();
                        }
                        break;
                    case "sort_posts":
                        user1 = words[1];
                        for (String s : Operations.sortPosts(user1)) {
                            writer.write(s);
                            writer.newLine();
                        }
                }
            }
        }
        catch (IOException e) {
            System.out.println("Error: File not found.");
            e.printStackTrace();
        }
    }

}