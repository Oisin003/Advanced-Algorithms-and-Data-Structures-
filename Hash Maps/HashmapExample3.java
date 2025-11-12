
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Author: Oisin Gibson (L00172671)
 * Date: 12/11/2025
 * Description:
 */
public class HashmapExample3 {
    public static void main(String[] args) {

        HashMap<Person, Address> taxDefaulters = new HashMap<>();// HashMap to store name to Person object mapping
        // Mapping Person to Address of tax defaulters

        System.err.println("--- Tax Defaulters Example ---");

        // Adding Person-Address pairs to the map
        taxDefaulters.put(new Person("John Doe", 45,
                new Address("123 Main St", "Springfield", "IL", "62701")),
                new Address("456 Elm St", "Springfield", "IL", "62702"));
        taxDefaulters.put(new Person("Jane Smith", 38,
                new Address("789 Oak St", "Springfield", "IL", "62703")),
                new Address("101 Pine St", "Springfield", "IL", "62704"));
        taxDefaulters.put(new Person("Alice Johnson", 50,
                new Address("202 Maple St", "Springfield", "IL", "62705")),
                new Address("303 Birch St", "Springfield", "IL", "62706")); // Adding Person-Address pairs to the map

        System.err.println("Print All Tax Defaulters:");// Print all tax defaulters
        for (Person currPerson : taxDefaulters.keySet()) {// Iterate through the set of keys (Persons)
            System.out.println(currPerson + " " + taxDefaulters.get(currPerson));// Print each Person and their
                                                                                 // corresponding Address

        }
        System.err.println("");

        System.err.println("Search for Address Example:");// Search for a specific address
        Address addressToSearchFor = new Address("101 Pine St", "Springfield", "IL", "62704");// Address to search for
        // Have it iterate through the key and the vaule together to find the address
        // Map.Entry<Person, Address> // Want a paired data structure where the key and
        // the value are together

        Set<Map.Entry<Person, Address>> entries = taxDefaulters.entrySet();// Get the entry set of the map

        // Contains key value pairs together
        // Map.Entry represents a key-value pair in the map
        // entrySet() returns a set of all key-value pairs in the map
        // Now we can iterate through the entries to find the address
        // Each entry contains both the key (Person) and the value (Address)
        // We can use this to compare the value with the address we are searching for
        // If we find a match, we can print the corresponding Person
        // This is more efficient than iterating through the keys and values separately
        // as we have both the key and value together in each entry
        // We can avoid multiple lookups in the map
        // This is especially useful for larger maps
        // It also makes the code cleaner and easier to read
        // We can directly access both the key and value in each iteration

        for (Map.Entry<Person, Address> entry : entries) {// Iterate through the entry set

            // System.err.println(entry);// Print each entry for debugging
            System.out.println("");
            if (entry.getValue().toString().equals(addressToSearchFor.toString())) {// Compare the value with the
                                                                                    // address to search for
                System.out.println("Found tax defaulter: " + entry.getKey());// Print the corresponding Person if a
                                                                             // match is found
            }

        }

        System.err.println("");

    }
}
