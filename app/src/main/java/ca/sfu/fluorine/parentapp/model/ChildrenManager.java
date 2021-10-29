package ca.sfu.fluorine.parentapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 *  ChildrenManager.java - represents a list of children.
 */

public class ChildrenManager {

    // create a list of children objects.
    List<Child> children = new ArrayList<>();

    // add child
    public void addChild(String fName, String lName) {

        // create new child object
        Child child = new Child(fName,lName);

        // add the child to the array
        children.add(child);

    }

    // modify child
    public void modifyChild(int id,String fName, String lName) {

        // Create a new child with given names
        Child child = new Child(fName,lName);

        // Overwrite the old child at that ID
        children.set(id,child);

    }

    // delete child
    public void deleteChild(int id) {
        children.remove(id);
    }

}
