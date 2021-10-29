package ca.sfu.fluorine.parentapp.model;

import java.util.ArrayList;
import java.util.List;

/*
    ChildrenManager.java - represents a list of children.
 */
public class ChildrenManager {

    // create a list of children objects.
    List<Child> children = new ArrayList<>();

    // add child
    public void addChild(List<Child> children) {

        Child child = new Child("Karen","Moore");

        children.add(child);

    }
    // modify child

    // delete child

}
