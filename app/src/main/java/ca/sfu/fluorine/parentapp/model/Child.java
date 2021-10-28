package ca.sfu.fluorine.parentapp.model;
/*
    Child.java - represents a child
 */
public class Child {
    private String firstName, lastName;

    public void Child(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Child{" +
            "firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            '}';
    }
}
