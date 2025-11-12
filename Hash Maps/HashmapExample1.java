import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Author: Oisin Gibson (L00172671)
 * Date: 12/11/2025
 * Description: This program demonstrates the use of a HashMap in Java to store
 * and retrieve key-value pairs.
 * This Example includes my own Notes
 * 
 */

public class HashmapExample1 {
    public static void main(String[] args) {
        // Create a HashMap to store String keys and Integer values
        HashMap<String, Integer> map = new HashMap<>();
        // Integar is a wrapper class for int
        System.err.println("--- HashMap Example ---");
        // Add key-value pairs to the HashMap
        map.put("Tim", 30);
        map.put("Mary", 24);
        map.put("Bill", 45);
        map.put("Annie", 37);

        // Keys are unique, if you put a value with an existing key it will overwrite
        // the previous value
        // Cant have duplicate keys in a HashMap
        map.put("Bill", 50); // This will overwrite the previous value for key "Bill"

        System.out.println("");

        System.out.println("Retrieve Value Example:");
        System.out.println("Bill's age is " + map.get("Bill"));// Retrieve value for key "Bill"

        System.out.println("");

        System.err.println("Print Entire Map:");
        System.out.println(map);// Does not come back out in the order it was put in
        // This is because HashMap uses hashing to store the key-value pairs
        // Meaning the order is based on the hash codes of the keys
        // This allows for fast access to values based on their keys

        System.err.println("");

        Set<String> setOfNames = map.keySet();// Retrieve the set of keys from the HashMap

        Collection<Integer> ages = map.values();// Retrieve the collection of values from the HashMap;

        System.err.println("Print Ages Method:");
        // Iterate through the collection of ages and print each age
        for (Integer currAge : ages) {// Iterate through the collection of ages
            System.out.println("Age: " + currAge);// Print each age
        }

        System.err.println("");

        ArrayList<Integer> arrAges = new ArrayList<>(map.values());// Convert the set of keys to an ArrayList

        System.out.println("");

        System.err.println("Print NamesMethod:");
        System.out.println("Names in the map: " + setOfNames);

        System.out.println("");

        System.err.println("Enhanced For Loop Method:");
        for (String currPerson : setOfNames) {// Iterate through the keys and print each person's age
            System.out.println(currPerson + "'s age is " + map.get(currPerson));// Iterate through the keys and print
                                                                                // each person's age
        }

        System.err.println("");

        System.out.println("Iterator Method:");
        Iterator<String> iterator = setOfNames.iterator();// Get an iterator for the set of keys
        while (iterator.hasNext()) {// Iterate through the keys using the iterator
            String currPerson = iterator.next();// Get the next key
            System.out.println(currPerson + "'s age is " + map.get(currPerson));// Print the person's age

        }

    }

    // O1 is constant time complexity for HashMap operations like put and get on
    // average
    // because it uses hashing to directly compute the index for a given key.
    // However, in the worst case, it can degrade to O(n) if many keys hash to the
    // same index,
    // causing collisions and resulting in a linked list or tree traversal.
}
