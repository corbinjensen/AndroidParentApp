package ca.sfu.fluorine.parentapp.model.children;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 *  Child.java - represents a child
 */
@Entity(tableName = "children")
public class Child {
    @PrimaryKey(autoGenerate = true)
    int id; // This field is only mutable within the same package

    private String firstName;
    private String lastName;
    long createdTime; // This field is only mutable within the same package

    public Child(@NonNull String firstName, @NonNull String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdTime = System.currentTimeMillis(); // Current UNIX time
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void updateName(@NonNull String firstName, @NonNull String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }
}
