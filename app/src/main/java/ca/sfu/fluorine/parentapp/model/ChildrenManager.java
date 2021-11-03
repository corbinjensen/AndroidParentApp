package ca.sfu.fluorine.parentapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 *  ChildrenManager.java - represents a list of children.
 */

public class ChildrenManager {

    private static ChildrenManager instance;
    // create a list of children objects.
    List<Child> children = new ArrayList<>();

    public static ChildrenManager getInstance() {
        if(instance == null) {
            instance = new ChildrenManager();
        }
        return instance;
    }

    // add child
    public void addChild(String fName, String lName) {

        // create new child object
        Child child = new Child(fName,lName);

        // add the child to the array
        children.add(child);

    }

    //get child by index
    public Child getChildFromIndex(int index) {
        return children.get(index);
    }

    // modify child
    public void modifyChild(int id,String fName, String lName) {

        // Create a new child with given names
        Child child = new Child(fName,lName);

        // Overwrite the old child at that ID
        children.set(id,child);

    }

    public List<Child> getAllChildren() {
        return children;
    }

    // delete child
    public void deleteChild(int id) {
        children.remove(id);
    }

}
