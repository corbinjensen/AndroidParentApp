package ca.sfu.fluorine.parentapp.view.coin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentCoinFlipBinding;
import ca.sfu.fluorine.parentapp.model.AppDatabase;
import ca.sfu.fluorine.parentapp.model.coinflip.CoinResultAndChild;

/**
 * CoinFlipFragment
 *
 * Represents the UI for the coin flip animation and user input.
 */
public class CoinFlipFragment extends Fragment {
	private AppDatabase database;
	private FragmentCoinFlipBinding binding;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		database = AppDatabase.getInstance(requireContext().getApplicationContext());
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		binding = FragmentCoinFlipBinding.inflate(inflater, container, false);
		return binding.getRoot();
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		binding.navigationCoinFlip.setOnClickListener(v -> {
			Intent add = new Intent(requireContext(), CoinFlipActivity.class);
			startActivity(add);
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		List<CoinResultAndChild> coinResultsWithChildren
				= database.coinResultDao().getAllCoinResultsWithChildren();
		if (coinResultsWithChildren.isEmpty()) {
			binding.listCoinFlip.showEmpty();
		} else {
			binding.listCoinFlip.useAdapter(new CoinHistoryAdapter(coinResultsWithChildren));
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		binding = null;
	}

	public class CoinHistoryAdapter extends RecyclerView.Adapter<CoinFlipViewHolder> {
		private final List<CoinResultAndChild> coinResultsWithChildren;

		public CoinHistoryAdapter(List<CoinResultAndChild> coinResultsWithChildren) {
			this.coinResultsWithChildren = coinResultsWithChildren;
		}

		@NonNull
		@Override
		public CoinFlipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(requireContext()).inflate(
					R.layout.coin_flip_row_layout, parent, false);
			return new CoinFlipViewHolder(view);
		}

		@Override
		public void onBindViewHolder(@NonNull CoinFlipViewHolder holder, int position) {
			CoinResultAndChild coinResultWithChild = coinResultsWithChildren.get(position);
			holder.populateData(requireContext(), coinResultWithChild);
		}

		@Override
		public int getItemCount() {
			return coinResultsWithChildren.size();
		}

	}
}
