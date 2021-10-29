package ca.sfu.fluorine.parentapp.model;

/**
 *  Child.java - represents a child
 */
public class Child {
    public String firstName, lastName;

    public Child(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

}
