package ca.sfu.fluorine.parentapp.model.composite;

import androidx.annotation.NonNull;
import androidx.room.Embedded;

import ca.sfu.fluorine.parentapp.model.children.Child;
import ca.sfu.fluorine.parentapp.model.coinflip.CoinResult;

/**
 * Intermediate data class to represents one-to-one relationship
 * between CoinResult and Child entities
 *
 * Each coin result associate with exactly one child
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

	public void setChild(@NonNull Child child) {
		this.child = child;
	}

	public void setCoinResult(@NonNull CoinResult coinResult) {
		this.coinResult = coinResult;
	}
}
