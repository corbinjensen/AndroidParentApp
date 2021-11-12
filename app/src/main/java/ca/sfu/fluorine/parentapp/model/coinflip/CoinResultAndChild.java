package ca.sfu.fluorine.parentapp.model.coinflip;

import androidx.room.Embedded;
import androidx.room.Relation;

import ca.sfu.fluorine.parentapp.model.children.Child;

public class CoinResultAndChild {
	@Embedded CoinResult coinResult;

	@Relation(
			parentColumn = "child_id",
			entityColumn = "id"
	)
	Child child;

	public CoinResult getCoinResult() {
		return coinResult;
	}

	public Child getChild() {
		return child;
	}
}
