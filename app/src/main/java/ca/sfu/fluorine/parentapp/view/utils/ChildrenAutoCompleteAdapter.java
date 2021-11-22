package ca.sfu.fluorine.parentapp.view.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.model.children.Child;

public class ChildrenAutoCompleteAdapter extends ArrayAdapter<Child> {
	private Child selectedChild = Child.getUnspecifiedChild();

	public ChildrenAutoCompleteAdapter(@NonNull Context context,
									   @NonNull List<Child> children,
									   boolean hasUnassignedChild) {
		super(context, 0, children);
		if (hasUnassignedChild) {
			add(Child.getUnspecifiedChild());
		}
	}

	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.child_selection_item_layout, parent, false);
		}
		TextView childName = convertView.findViewById(R.id.child_name);
		ImageView childIcon = convertView.findViewById(R.id.child_icon);
		ImageView checkmark = convertView.findViewById(R.id.selected_checkmark);

		// Set up data
		Child child = getItem(position);

		// Empty child (or no child)
		childName.setText(Utility.formatChildName(getContext(), child));
		Utility.setupImage(getContext(), childIcon, child);
		checkmark.setVisibility(
				(child.getId() == selectedChild.getId()) ? View.VISIBLE : View.INVISIBLE);
		return convertView;
	}

	@NonNull
	@Override
	public Filter getFilter() {
		return new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence charSequence) {
				return null;
			}

			@Override
			protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
			}

			@Override
			public CharSequence convertResultToString(Object resultValue) {
				Child child = (Child) resultValue;
				return Utility.formatChildName(getContext(), child);
			}
		};
	}

	@NonNull
	public Child getSelectedChild() {
		return selectedChild;
	}

	public void setSelectedChild(@NonNull Child selectedChild) {
		this.selectedChild = selectedChild;
	}

	public void reset(List<Child> children, boolean hasUnassignedChild) {
		clear();
		addAll(children);
		if (hasUnassignedChild) {
			add(Child.getUnspecifiedChild());
		}
	}
}
