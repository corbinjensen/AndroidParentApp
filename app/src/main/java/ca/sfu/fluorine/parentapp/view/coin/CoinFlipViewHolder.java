package ca.sfu.fluorine.parentapp.view.coin;

import android.content.Context;
import android.icu.text.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.model.coinflip.CoinResult;
import ca.sfu.fluorine.parentapp.model.coinflip.CoinResultAndChild;

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

	public void populateData(Context context, CoinResultAndChild coinResultAndChild) {
		CoinResult result = coinResultAndChild.getCoinResult();
		Child child = coinResultAndChild.getChild();

		String formatDateTime = DateFormat
				.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)
				.format(new Date(result.getDateTimeOfFlip()));

		dateTimeView.setText(formatDateTime);
		childNameView.setText(child.getFirstName());
		didPickerWinView.setText(result.didPickerWin() ? R.string.win : R.string.lose);
		int color = context.getResources().getColor(
				result.didPickerWin()
						? android.R.color.holo_green_dark
						: android.R.color.holo_red_dark,
				null);
		didPickerWinView.setTextColor(color);
		coinResultView.setImageResource(
				result.getResultIsHead() ? R.drawable.ic_heads : R.drawable.ic_tails);
	}


}
