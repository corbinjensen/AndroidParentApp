package ca.sfu.fluorine.parentapp.view.coin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentCoinFlipBinding;
import ca.sfu.fluorine.parentapp.model.composite.CoinResultWithChild;
import ca.sfu.fluorine.parentapp.viewmodel.coin.CoinFlipViewModel;
import dagger.hilt.android.AndroidEntryPoint;

/**
 * CoinFlipFragment
 *
 * Represents the UI for the coin flip animation and user input.
 */
@AndroidEntryPoint
public class CoinFlipFragment extends Fragment {
	private FragmentCoinFlipBinding binding;
	private CoinFlipViewModel viewModel;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		binding = FragmentCoinFlipBinding.inflate(inflater, container, false);
		viewModel = new ViewModelProvider(this).get(CoinFlipViewModel.class);
		return binding.getRoot();
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		binding.navigationCoinFlip.setOnClickListener(v -> {
			Intent add = new Intent(requireContext(), CoinFlipActivity.class);
			startActivity(add);
		});
		viewModel.getLiveCoinResultsWithChildren().observe(getViewLifecycleOwner(),
				coinResultWithChildren -> {
					if (coinResultWithChildren.isEmpty()) {
						binding.listCoinFlip.showEmpty();
					} else {
						binding.listCoinFlip.useAdapter(
								new CoinHistoryAdapter(coinResultWithChildren));
					}
				});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		binding = null;
	}

	public class CoinHistoryAdapter extends RecyclerView.Adapter<CoinFlipViewHolder> {
		private final List<CoinResultWithChild> coinResultsWithChildren;

		public CoinHistoryAdapter(List<CoinResultWithChild> coinResultsWithChildren) {
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
			CoinResultWithChild coinResultWithChild = coinResultsWithChildren.get(position);
			holder.populateData(
					requireContext(),
					coinResultWithChild,
					viewModel.loadChildIconFromCoinResult(coinResultWithChild));
		}

		@Override
		public int getItemCount() {
			return coinResultsWithChildren.size();
		}

	}
}
