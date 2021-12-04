package ca.sfu.fluorine.parentapp.view.task.history;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import ca.sfu.fluorine.parentapp.databinding.TaskHistoryRowLayoutBinding;
import ca.sfu.fluorine.parentapp.model.composite.WhoseTurnRecord;
import ca.sfu.fluorine.parentapp.view.utils.Utility;

/**
 * Represents each row of the RecyclerView of the task history
 */
public class TaskHistoryViewHolder extends RecyclerView.ViewHolder {
    private final TaskHistoryRowLayoutBinding binding;

    public TaskHistoryViewHolder(TaskHistoryRowLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void populateHistoryData(
        Context context, WhoseTurnRecord record, @Nullable Bitmap childIcon) {
        String fullName = Utility.formatChildName(context, record.getChild());
        binding.taskHistoryChildName.setText(fullName);
        Utility.setupImage(record.getChild(),childIcon,binding.taskHistoryChildIcon);
        binding.completeTime.setText(Utility.formatUnixTime(record.getCompletionTime()));
    }
}
