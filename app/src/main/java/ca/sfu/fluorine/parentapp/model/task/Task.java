package ca.sfu.fluorine.parentapp.model.task;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import ca.sfu.fluorine.parentapp.model.children.Child;

@Entity(tableName = "tasks",
		foreignKeys = @ForeignKey(
				entity = Child.class,
				parentColumns = "child_id",
				childColumns = "child_turn_id",
				onDelete = ForeignKey.SET_DEFAULT
		))
public class Task {
	@ColumnInfo(name = "task_id")
	@PrimaryKey(autoGenerate = true)
	private int id;

	private String name;

	@ColumnInfo(name = "child_turn_id", index = true, defaultValue = "-1")
	private int childId;

	public Task(String name, int childId) {
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

	public int getChildId() {
		return childId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void update(@NonNull String taskName, int childId) {
		this.name = taskName;
		this.childId = childId;
	}
}
