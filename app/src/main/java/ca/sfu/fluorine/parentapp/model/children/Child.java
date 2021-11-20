package ca.sfu.fluorine.parentapp.model.children;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 *  Child.java - represents a child
 */
@Entity(tableName = "children")
public class Child {
    @ColumnInfo(name = "child_id")
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String firstName;
    private String lastName;
    private String photoFileName;
    private long createdTime;

    // Singleton for unspecified child
    private static Child UNSPECIFIED;

    public Child(@NonNull String firstName, @NonNull String lastName, @Nullable String photoFileName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.photoFileName = photoFileName;
        this.createdTime = System.currentTimeMillis(); // Current UNIX time
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void updateName(@NonNull String firstName, @NonNull String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static Child getUnspecifiedChild() {
        if (UNSPECIFIED == null) {
            UNSPECIFIED = new Child("", "", null);
            UNSPECIFIED.setId(-1);
        }
        return UNSPECIFIED;
    }

    // Setters is only serve Room database
    public void setId(int id) {
        this.id = id;
    }

    public void setCreatedTime(long createdTime){
        this.createdTime = createdTime;
    }

    public String getPhotoFileName() {
        return photoFileName;
    }

    public void updatePhotoFileName(@Nullable String photoFileName) {
        this.photoFileName = photoFileName;
    }
}
