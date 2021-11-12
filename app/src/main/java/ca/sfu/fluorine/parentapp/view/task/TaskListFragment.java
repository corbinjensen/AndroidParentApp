package ca.sfu.fluorine.parentapp.view.task;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentTaskListBinding;

public class TaskListFragment extends Fragment {

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		FragmentTaskListBinding binding = FragmentTaskListBinding
				.inflate(inflater, container, false);
		return binding.getRoot();
	}
}