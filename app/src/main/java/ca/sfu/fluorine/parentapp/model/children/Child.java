package ca.sfu.fluorine.parentapp.model.children;

import androidx.annotation.NonNull;

/**
 *  Child.java - represents a child
 */
public class Child {
    private final String firstName;
    private final String lastName;

    public Child(@NonNull String firstName, @NonNull String lastName) {
        if (firstName.isEmpty() || lastName.isEmpty()) {
            throw new IllegalArgumentException("Input name should not be empty");
        }
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
