package ca.sfu.fluorine.parentapp.model.coinflip;

import androidx.annotation.NonNull;
import androidx.room.Embedded;

import ca.sfu.fluorine.parentapp.model.children.Child;

/**
 * Intermediate data class to represents one-to-one relationship
 * between CoinResult and Child models
 */
public class CoinResultWithChild {
	@Embedded CoinResult coinResult;
	@Embedded Child child;

	@NonNull
	public CoinResult getCoinResult() {
		return coinResult;
	}

	@NonNull
	public Child getChild() {
		return child;
	}
}
