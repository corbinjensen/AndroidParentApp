package ca.sfu.fluorine.parentapp.model.children;

import java.util.ArrayList;

/**
 *  Child.java - represents a child
 */
public class Child {
    private final String firstName;
    private final String lastName;

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
}
