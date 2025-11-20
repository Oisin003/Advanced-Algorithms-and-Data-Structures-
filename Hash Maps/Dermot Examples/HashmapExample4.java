
/**
 * Author: Oisin Gibson (L00172671)
 * Date: 12/11/2025
 * Last Modified: 12/11/2025
 * 
 * Assignment/Project: Hash Map Examples
 * Module: Advanced Algorithms and Data Structures
 * 
 * Description: This program is a placeholder for future HashMap examples.
 * 
 * Java Version: Java 17
 * 
 * Modification History:
 * 12/11/2025 - Initial creation
 * 
 */

import java.util.HashMap;

public class HashmapExample4 {
    public static void main(String[] args) {
        System.err.println("--- HashMap Example 4 ---");
        // Placeholder for future HashMap examples

        HashMap<Person, Address> tasDefaulterGlenties = new HashMap<>();

        tasDefaulterGlenties.put(new Person("John Doe", 45,
                new Address("123 Main St", "Glenties", "Donegal", "F92 XY12")),
                new Address("123 Main St", "Glenties", "Donegal", "F92 XY12"));
        tasDefaulterGlenties.put(new Person("Jane Smith", 38,
                new Address("789 Oak St", "Glenties", "Donegal", "F92   XY34")),
                new Address("789 Oak St", "Glenties", "Donegal", "F92   XY34"));
        tasDefaulterGlenties.put(new Person("Alice Johnson", 50,
                new Address("202 Maple St", "Glenties", "Donegal", "F92  XY56")),
                new Address("202 Maple St", "Glenties", "Donegal", "F92  XY56"));

        HashMap<Person, Address> tasDefaulterArdara = new HashMap<>();

        tasDefaulterArdara.put(new Person("Michael Brown", 52,
                new Address("456 Elm St", "Ardara", "Donegal", "F94 AB12")),
                new Address("456 Elm St", "Ardara", "Donegal", "F94 AB12"));
        tasDefaulterArdara.put(new Person("Emily Davis", 29,
                new Address("101 Pine St", "Ardara", "Donegal", "F  94  AB34")),
                new Address("101 Pine St", "Ardara", "Donegal", "F  94  AB34"));
        tasDefaulterArdara.put(new Person("David Wilson", 41,
                new Address("303 Birch St", "Ardara", "Donegal", "F94  AB56")),
                new Address("303 Birch St", "Ardara", "Donegal", "F94  AB56"));

        // You can also have a hashmap of a hashmap
        HashMap<String, HashMap<Person, Address>> database = new HashMap<>();// HashMap to store town to tax defaulters
                                                                             // mapping
        // Want to map town names to their respective tax defaulters

        database.put("Glenties", tasDefaulterGlenties);// Map "Glenties" to its tax defaulters
        database.put("Ardara", tasDefaulterArdara);// Map "Ardara" to its tax defaulters

        System.err.println("Database of Tax Defaulters by Town:");
        for (String town : database.keySet()) {// Iterate through the towns in the database
            System.out.println("Town: " + town);// Print the town name
            HashMap<Person, Address> defaulters = database.get(town);// Get the tax defaulters for the town
            for (Person currPerson : defaulters.keySet()) {// Iterate through the tax defaulters
                System.out.println(currPerson + " " + defaulters.get(currPerson));// Print each Person and their
                                                                                  // corresponding Address
            }
            System.out.println("");// Print a blank line for better readability

        }
    }

}
