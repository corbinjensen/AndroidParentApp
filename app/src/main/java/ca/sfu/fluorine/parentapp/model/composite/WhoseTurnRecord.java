package ca.sfu.fluorine.parentapp.model.composite;

import androidx.annotation.NonNull;
import androidx.room.Embedded;

import ca.sfu.fluorine.parentapp.model.children.Child;

/**
 * Intermediate data class for mapping the SQL results of whose turns history
 * into Java objects
 *
 * Currently, since the UI shows the history corresponding with a specific task,
 * the task data filed is omitted in this class
 */
public class WhoseTurnRecord {
    @Embedded Child child;
    long completionTime;

    @NonNull
    public Child getChild() {
        return child;
    }

    public long getCompletionTime() {
        return completionTime;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public void setCompletionTime(long completionTime) {
        this.completionTime = completionTime;
    }
}
