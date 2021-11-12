package ca.sfu.fluorine.parentapp.model.coinflip;

import androidx.room.Embedded;

import ca.sfu.fluorine.parentapp.model.children.Child;

/**
 * Intermediate data class to represents one-to-one relationship
 * between CoinResult and Child models
 */
public class CoinResultAndChild {
	@Embedded CoinResult coinResult;
	@Embedded Child child;

	public CoinResult getCoinResult() {
		return coinResult;
	}

	public Child getChild() {
		return child;
	}
}
