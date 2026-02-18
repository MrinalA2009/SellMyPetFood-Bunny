package SellMyPetFood;
import java.util.Set;
import java.util.HashSet;
import java.io.File;
/*
* Problem 2.3.1 Sell My Pet Food
*/
/**
* Entry point for building a targeted advertisement list.
*/
public class TargetedAd {


 /**
  * Quick check that a file exists and we can read it.
  *
  * @param filename the name of the file
  * @return true if the file looks usable, false otherwise
  */
 private static boolean validateFile(String filename) {
   if (filename == null || filename.trim().isEmpty()) {
     System.err.println("Error: filename is missing.");
     return false;
   }


   File file = new File(filename);


   if (!file.exists() || !file.isFile()) {
     System.err.println("Error: can't find file: " + filename);
     return false;
   }


   if (!file.canRead()) {
     System.err.println("Error: can't read file: " + filename);
     return false;
   }


   if (file.length() == 0) {
     // Not always a dealbreaker, but nothing useful will happen.
     System.err.println("Warning: file is empty: " + filename);
   }


   return true;
 }


 /**
  * Make sure post has username and ig some text.
  *
  * @param post one line from the posts file
  * @return true if the line looks like "username smth smth" else false
  */
 private static boolean validatePost(String post) {
   if (post == null || post.trim().isEmpty()) {
     return false;
   }


   return post.indexOf(" ") != -1;
 }


 /**
  * Run the targeted advertising workflow.
  *
  * @param args command-line arguments (not used)
  */
 public static void main(String[] args)
 {
   /* 
    * TODO:
    * PREPARATION WORK
    * (1) Create a file called targetWords.txt. Populate this file with words on each line that
    *     you think would determine if a user is a dog or cat owner.
    *
    * PROGRAMMING
    * (2) Create a new DataCollector object and set the data to "socialMediaPostsSmall.txt" and "targetWords.txt"
    *     Important: Use the socialMedialPostsSmall to create your algorithm. Using a small file will help you
    *     generate your solution quicker and give you the ability to double check your work.
    * (3) Create a String variable to hold the names of all the user. (The first word of every post is
    *     a person's username)
    * (4) Compare each user's post to each target word. If a user mentions a target word, add their username to
    *     the String of users. Separate usernames with a space.
    *         Hint: You can use loops to look through each word.
    *         Hint2: You can use indexOf to check if a word is in a user post.
    * (5) Once you have all the users, use your DataCollector's prepareAdvertisement method to prepare a file
    *     with all users and the advertisement you will send them.
    *         Additional Info: The prepareAdvertisement creates a new file on your computer. Check the posts of
    *         some of the usernames to make sure your algorithm worked.
    *
    * THE FINAL SOLUTION
    * (6) Your solution should work with the socialMedialPostsSmall.txt. Modify your DataCollector initialization
    *    so you use the socialMediaPosts.txt. You should now have a larger file of users to target.
    */




   /* your code here */


   // pre-check on files
   String postsFile = "socialMediaPosts.txt";
   String targetWordsFile = "targetwords.txt";
  
   if (!validateFile(postsFile)) {
     System.err.println("Stopping: posts file isn't usable.");
     return;
   }
  
   if (!validateFile(targetWordsFile)) {
     System.err.println("Stopping: target words file isn't usable.");
     return;
   }


   DataCollector dc = new DataCollector();
   dc.setData(postsFile, targetWordsFile);
  
   // Set = no duplicates
   Set<String> userNamesSet = new HashSet<>();
   String post = dc.getNextPost();
   int processedPosts = 0;
   int skippedPosts = 0;
  
   while (!post.equals("NONE")) {
     // Skip weird/blank lines so we don't crash on substring/indexOf.
     if (!validatePost(post)) {
       skippedPosts++;
       post = dc.getNextPost();
       continue;
     }
    
     processedPosts++;
     String targetWord = dc.getNextTargetWord();
     while (!targetWord.equals("NONE")) {
       if (post.indexOf(targetWord) != -1) {
         String userName = post.substring(0, post.indexOf(" "));
         userNamesSet.add(userName.trim());
         break;
       }
       targetWord = dc.getNextTargetWord();
     }
     post = dc.getNextPost();
   }
  
   if (userNamesSet.isEmpty()) {
     System.err.println("Warning: No matching users found. Output file empty.");
   }
  
   System.out.println("Processed " + processedPosts + " posts, skipped " + skippedPosts + " invalid posts.");
   System.out.println("Found " + userNamesSet.size() + " unique matching users.");
  
   String userNames = String.join(" ", userNamesSet);
  
   dc.prepareAdvertisement("targetedUser.txt", userNames, "Buy our pet food you donut");
 }
}


  





