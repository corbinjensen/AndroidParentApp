package ca.sfu.fluorine.parentapp.model.task;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import ca.sfu.fluorine.parentapp.model.children.Child;

/**
 * Represent the task history table in the database
 *
 * This table contains two foreign keys referring to the child and task tables
 */
@Entity(
        tableName = "whose_turn",
        foreignKeys = {
                @ForeignKey(
                        entity = Child.class,
                        parentColumns = "child_id",
                        childColumns = "assigned_child_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = Task.class,
                        parentColumns = "task_id",
                        childColumns = "assigned_task_id",
                        onDelete = ForeignKey.CASCADE)
        }
)
public class WhoseTurn {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "whose_turn_id")
    private int id;

    @ColumnInfo(name = "assigned_child_id", index = true)
    private final int childId;

    @ColumnInfo(name = "assigned_task_id", index = true)
    private final int taskId;
    private long completionTime;

    public WhoseTurn(int taskId, int childId) {
        this.childId = childId;
        this.taskId = taskId;
        this.completionTime = System.currentTimeMillis();
    }

    public long getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(long completionTime) {
        this.completionTime = completionTime;
    }

    public int getChildId() {
        return childId;
    }

    public int getTaskId() {
        return taskId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
