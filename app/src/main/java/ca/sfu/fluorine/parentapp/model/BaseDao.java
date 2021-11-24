package ca.sfu.fluorine.parentapp.model;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

/**
 * A generic interface of DAO
 * to support inserting, updating, and deleting data in the database
 * @param <T> Entity class
 */
public interface BaseDao<T> {
    @Insert
    void add(T object);

    @Delete
    void delete(T object);

    @Update
    void update(T object);
}
