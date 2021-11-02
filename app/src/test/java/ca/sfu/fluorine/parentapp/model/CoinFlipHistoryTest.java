package ca.sfu.fluorine.parentapp.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CoinFlipHistoryTest {
    private final String TEST_CHILD_FIRST = "FIRST";
    private final String TEST_CHILD_LAST = "LAST";

    CoinFlipHistory listFlipHistory = new CoinFlipHistory();
    Child dummyChild = new Child(TEST_CHILD_FIRST, TEST_CHILD_LAST);

    final CoinResult.CoinSide childGuess1 = CoinResult.CoinSide.HEAD;
    final CoinResult.CoinSide childGuess2 = CoinResult.CoinSide.TAIL;
    final CoinResult.CoinSide childGuess3 = CoinResult.CoinSide.HEAD;

    final CoinResult.CoinSide flipResult1 = CoinResult.CoinSide.HEAD;
    final CoinResult.CoinSide flipResult2 = CoinResult.CoinSide.HEAD;
    final CoinResult.CoinSide flipResult3 = CoinResult.CoinSide.TAIL;

    final CoinResult flipHistory1 = new CoinResult(dummyChild, childGuess1, flipResult1);
    final CoinResult flipHistory2 = new CoinResult(dummyChild, childGuess2, flipResult2);
    final CoinResult flipHistory3 = new CoinResult(dummyChild, childGuess3, flipResult3);


    @Test
    void addCoinResultToHistory() {
        listFlipHistory.addCoinResultToHistory(flipHistory1);
        assertEquals(listFlipHistory.coinResultHistory.getFirst(), flipHistory1);
    }

    @Test
    void removeCoinResultFromHistory() {
        final int INDEX_TO_REMOVE = 1;
        final int INDEX_OF_EMPTY = -1;

        listFlipHistory.coinResultHistory.add(flipHistory1);
        listFlipHistory.coinResultHistory.add(flipHistory2);
        listFlipHistory.coinResultHistory.add(flipHistory3);

        listFlipHistory.removeCoinResultFromHistory(INDEX_TO_REMOVE);

        assertEquals(listFlipHistory.coinResultHistory.indexOf(flipHistory2),INDEX_OF_EMPTY);

    }

}