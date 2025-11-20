// Oisin Gibson
// L00172671
// Updated: 23.10.2025
// Note: Dermots code/comments have been unaltered where possible

public class Person implements Comparable<Person> {
    private String firstname;
    private String surname;
    private int age;
    
    public Person(String firstname, String surname, int age) {
        this.firstname = firstname;
        this.surname = surname;
        this.age = age;
    }

    @Override
    public int compareTo(Person o) {
        // return Integer.compare(age, o.age);
        return this.surname.compareTo(o.surname);
    }

    @Override
    public String toString() {
        return "Person{" +
                "surname='" + surname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", age=" + age +
                '}';
    }
}
