package ca.sfu.fluorine.parentapp.view.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.RecyclerViewWithStatesLayoutBinding;

/**
 * A recycler view which support different states of the list.
 *
 * Right now, it only support the empty states.
 */
public class RecyclerViewWithStates extends ConstraintLayout {
	private final RecyclerViewWithStatesLayoutBinding binding;

	public RecyclerViewWithStates(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		binding = RecyclerViewWithStatesLayoutBinding.inflate(
				LayoutInflater.from(context),
				this
		);

		TypedArray a = context
				.getTheme()
				.obtainStyledAttributes(
						attrs,
						R.styleable.RecyclerViewWithStates,
						0,
						0);
		setupAttribute(a);

		// Set up layout manager
		LinearLayoutManager layoutManager = new LinearLayoutManager(context);
		binding.recyclerView.setLayoutManager(layoutManager);
		DividerItemDecoration dividerItemDecoration =
				new DividerItemDecoration(context, layoutManager.getOrientation());
		binding.recyclerView.addItemDecoration(dividerItemDecoration);
	}

	public void showEmptyStates() {
		binding.emptyStateIcon.setVisibility(VISIBLE);
		binding.emptyStateMessage.setVisibility(VISIBLE);
		binding.recyclerView.setVisibility(GONE);
		binding.recyclerView.setAdapter(null);
	}

	public void showList(RecyclerView.Adapter<?> adapter) {
		binding.recyclerView.setVisibility(VISIBLE);
		binding.recyclerView.setAdapter(adapter);
		binding.emptyStateIcon.setVisibility(GONE);
		binding.emptyStateMessage.setVisibility(GONE);
	}

	private void setupAttribute(TypedArray a) {
		try {
			int emptyStateImageResId =
					a.getResourceId(R.styleable.RecyclerViewWithStates_emptyStateImage, 0);
			binding.emptyStateIcon.setImageResource(emptyStateImageResId);
			String emptyStateMessage =
					a.getString(R.styleable.RecyclerViewWithStates_emptyStateMessage);
			binding.emptyStateMessage.setText(emptyStateMessage);
		} finally {
			a.recycle();
		}
	}
}
