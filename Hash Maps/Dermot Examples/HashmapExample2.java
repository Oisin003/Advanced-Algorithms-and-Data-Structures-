
import java.util.HashMap;

/**
 * Author: Oisin Gibson (L00172671)
 * Date: 12/11/2025
 * Description:
 */

public class HashmapExample2 {
    public static void main(String[] args) {
        HashMap<String, String> childToDadMap = new HashMap<>();// HashMap to store child to dad relationships
        // String String Mapping child names to dad names

        System.err.println("--- Child to Dad Map Example ---");

        childToDadMap.put("Alice", "Bob");// Bob is Alice's dad
        childToDadMap.put("Charlie", "David");// David is Charlie's dad
        childToDadMap.put("Eve", "Frank");// Frank is Eve's dad
        childToDadMap.put("Grace", "Heath");// Heath is Grace's dad
        childToDadMap.put("Bob", "Ian");// Ian is Bob's dad
        childToDadMap.put("David", "Jack");// Jack is David's dad
        childToDadMap.put("Frank", "Kevin");// Kevin is Frank's dad
        childToDadMap.put("Heath", "Larry");// Larry is Heath's dad

        // Adding child-dad pairs to the map
        String currPerson = "Eve";// Starting person
        String parent = null;// To hold the parent name

        // Traverse the ancestry chain
        System.out.println("Finding ancestors of " + currPerson + ":");// Print starting person
        while ((parent = childToDadMap.get(currPerson)) != null) {// Get the parent of the current person
            System.out.println("The parent of " + currPerson + " is " + parent);// Print the parent
            currPerson = parent;// Move up to the parent for the next iteration
        }
        System.err.println("");
    }

}
