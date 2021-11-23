package ca.sfu.fluorine.parentapp.model.task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import ca.sfu.fluorine.parentapp.model.children.Child;

/**
 * Represents Task table in the database.
 *
 * This table has foreign key pointing to the child table.
 * If the referenced child get deleted, then the foreign key will set to NULL
 */
@Entity(tableName = "tasks",
		foreignKeys = @ForeignKey(
				entity = Child.class,
				parentColumns = "child_id",
				childColumns = "child_turn_id",
				onDelete = ForeignKey.SET_NULL
		))
public class Task {
	@ColumnInfo(name = "task_id")
	@PrimaryKey(autoGenerate = true)
	private int id;

	private String name;

	// This filed will automatically set to NULL when the referenced child get deleted
	@ColumnInfo(name = "child_turn_id", defaultValue = "'NULL'", index = true)
	private Integer childId;

	public Task(String name, Integer childId) {
		this.name = name;
		this.childId = childId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@Nullable
	public Integer getChildId() {
		return childId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setChildId(@Nullable Integer childId) {
		this.childId = childId;
	}

	public void update(@NonNull String taskName, Integer childId) {
		this.name = taskName;
		this.childId = childId;
	}
}
