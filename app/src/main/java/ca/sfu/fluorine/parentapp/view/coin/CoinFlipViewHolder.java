package ca.sfu.fluorine.parentapp.view.coin;

import android.content.Context;
import android.graphics.Bitmap;
import android.icu.text.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.CoinFlipRowLayoutBinding;
import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.model.coinflip.CoinResult;
import ca.sfu.fluorine.parentapp.model.composite.CoinResultWithChild;
import ca.sfu.fluorine.parentapp.view.utils.Utility;

public class CoinFlipViewHolder extends RecyclerView.ViewHolder {
	private final CoinFlipRowLayoutBinding binding;
	public CoinFlipViewHolder(@NonNull CoinFlipRowLayoutBinding binding) {
		super(binding.getRoot());
		this.binding = binding;
	}

	public void populateData(Context context, @NonNull CoinResultWithChild coinResultWithChild, Bitmap bitmap) {
		CoinResult result = coinResultWithChild.getCoinResult();
		Child child = coinResultWithChild.getChild();

		String formatDateTime = DateFormat
				.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)
				.format(new Date(result.getDateTimeOfFlip()));

		binding.coinImageView.setImageResource(
				result.getResultIsHead() ? R.drawable.heads : R.drawable.tails
		);
		binding.dateTimeFlip.setText(formatDateTime);
		binding.childNameCoinView.setText(Utility.formatChildName(context, child));
		binding.didPickerWin.setText(result.didPickerWin() ? R.string.win : R.string.lose);
		int color = context.getResources().getColor(
				result.didPickerWin()
						? android.R.color.holo_green_dark
						: android.R.color.holo_red_dark,
				null);
		binding.didPickerWin.setTextColor(color);
		Utility.setupImage(child, bitmap, binding.coinFlipChildIcon);
	}
}
