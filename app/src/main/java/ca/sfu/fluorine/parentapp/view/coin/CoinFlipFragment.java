package ca.sfu.fluorine.parentapp.view.coin;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

import ca.sfu.fluorine.parentapp.CoinFlipActivity;
import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentCoinFlipBinding;
import ca.sfu.fluorine.parentapp.model.coinflip.CoinFlipHistory;
import ca.sfu.fluorine.parentapp.model.coinflip.CoinResult;

/**
 * CoinFlipFragment
 *
 * Represents the UI for the coin flip animation and user input.
 */
public class CoinFlipFragment extends Fragment {
	private FragmentCoinFlipBinding binding;
	private CoinFlipHistory flipHistory;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		flipHistory = CoinFlipHistory.getInstance(requireContext());
		binding = FragmentCoinFlipBinding.inflate(inflater, container, false);
		return binding.getRoot();
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		binding.navigationCoinFlip.setOnClickListener(v -> {
			Intent add = new Intent(getContext(), CoinFlipActivity.class);
			startActivity(add);
		});

		// Set up layout for the list
		LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
		binding.listCoinFlip.setLayoutManager(layoutManager);
		DividerItemDecoration dividerItemDecoration =
				new DividerItemDecoration(requireContext(), layoutManager.getOrientation());
		binding.listCoinFlip.addItemDecoration(dividerItemDecoration);
	}

	@Override
	public void onResume() {
		super.onResume();
		binding.listCoinFlip.setAdapter(new CoinHistoryAdapter());
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		binding = null;
	}

	public class CoinHistoryAdapter extends RecyclerView.Adapter<CoinHistoryAdapter.CoinFlipViewHolder> {
		@NonNull
		@Override
		public CoinFlipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(requireContext()).inflate(
					R.layout.coin_flip_row_layout, parent, false);
			return new CoinFlipViewHolder(view);
		}

		@Override
		public void onBindViewHolder(@NonNull CoinFlipViewHolder holder, int position) {
			CoinResult result = flipHistory.getCoinFlipAtIndex(position);

			// Populate data for each row
			String formatDateTime = DateFormat
					.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)
					.format(new Date(result.getDateTimeOfFlip()));
			holder.dateTimeView.setText(formatDateTime);
			holder.childNameView.setText(result.getWhoPicked().getFirstName());
			holder.didPickerWinView.setText(result.didPickerWin() ? R.string.win : R.string.lose);
			int color = getResources().getColor(
					result.didPickerWin()
							? android.R.color.holo_green_dark
							: android.R.color.holo_red_dark,
					null);
			holder.didPickerWinView.setTextColor(color);
			holder.coinResultView.setImageResource(
					result.getResultIsHead() ? R.drawable.ic_heads : R.drawable.ic_tails);
		}

		@Override
		public int getItemCount() {
			return flipHistory.getCoinFlip().size();
		}

		public class CoinFlipViewHolder extends RecyclerView.ViewHolder {
			TextView dateTimeView;
			TextView childNameView;
			TextView didPickerWinView;
			ImageView coinResultView;

			public CoinFlipViewHolder(@NonNull View itemView) {
				super(itemView);

				dateTimeView = itemView.findViewById(R.id.dateTimeFlip);
				childNameView = itemView.findViewById(R.id.childNameCoinView);
				didPickerWinView = itemView.findViewById(R.id.didPickerWin);
				coinResultView = itemView.findViewById(R.id.imageView);
			}
		}
	}
}
