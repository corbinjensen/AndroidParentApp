package ca.sfu.fluorine.parentapp.view.utils;

import android.content.Context;
import android.graphics.Bitmap;
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
import ca.sfu.fluorine.parentapp.service.ImageInternalStorage;

public class ChildrenAutoCompleteAdapter extends ArrayAdapter<Child> {
	public ChildrenAutoCompleteAdapter(@NonNull Context context,
									   @NonNull List<Child> children) {
		super(context, 0, children);
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

		// Set up data
		Child child = getItem(position);

		// Empty child (or no child)
		if (child == Child.getUnspecifiedChild()) {
			childName.setText(R.string.no_children);
			childIcon.setImageResource(R.drawable.ic_baseline_nothing);
		} else {
			childName.setText(
					getContext().getString(
							R.string.full_name,
							child.getFirstName(),
							child.getLastName())
			);
			Bitmap icon = ImageInternalStorage
					.getInstance(getContext().getApplicationContext())
					.loadImage(child.getPhotoFileName());
			if (icon != null) {
				childIcon.setImageBitmap(icon);
			} else {
				childIcon.setImageResource(R.drawable.robot);
			}
		}
		return convertView;
	}

	public Filter childrenFilter = new Filter() {
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
			if (child == Child.getUnspecifiedChild()) {
				return getContext().getString(R.string.no_children);
			}
			return getContext().getString(
					R.string.full_name, child.getFirstName(), child.getLastName());
		}
	};

	@NonNull
	@Override
	public Filter getFilter() {
		return childrenFilter;
	}
}
