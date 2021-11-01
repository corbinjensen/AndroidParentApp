package ca.sfu.fluorine.parentapp.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import ca.sfu.fluorine.parentapp.model.CoinResult.CoinSide;

class CoinResultTest {
    //local constants
    private final String TEST_CHILD_FIRST = "FIRST";
    private final String TEST_CHILD_LAST = "LAST";

    @Test
    public void CoinResult(){
        Child dummyChild = new Child(TEST_CHILD_FIRST, TEST_CHILD_LAST);
        CoinSide dummyChildGuess = CoinSide.HEAD;
        CoinSide dummyFlipResult = CoinSide.TAIL;
        CoinResult expectedCoinResult = new CoinResult(dummyChild, dummyChildGuess, dummyFlipResult);

        assertNotNull(expectedCoinResult.getDateTimeOfFlip());
        assertEquals(expectedCoinResult.getWhoPicked(), dummyChild);
        assertEquals(expectedCoinResult.getChildGuess(), dummyChildGuess);
        assertEquals(expectedCoinResult.getFlipResult(), dummyFlipResult);
        assertEquals(expectedCoinResult.didPickerWin(), (dummyChildGuess.equals(dummyFlipResult)));

    }


    @Test
    public void getDateTimeOfFlip() {
        Child dummyChild = new Child(TEST_CHILD_FIRST, TEST_CHILD_LAST);
        CoinSide dummyChildGuess = CoinSide.HEAD;
        CoinSide dummyFlipResult = CoinSide.TAIL;
        CoinResult expectedCoinResult = new CoinResult(dummyChild, dummyChildGuess, dummyFlipResult);

        assertNotNull(expectedCoinResult.getDateTimeOfFlip());
    }

    @Test
    public void getFlipResult() {
        Child dummyChild = new Child(TEST_CHILD_FIRST, TEST_CHILD_LAST);
        CoinSide dummyChildGuess = CoinSide.HEAD;
        CoinSide dummyFlipResult = CoinSide.TAIL;
        CoinResult expectedCoinResult = new CoinResult(dummyChild, dummyChildGuess, dummyFlipResult);

        assertEquals(expectedCoinResult.getFlipResult(), dummyFlipResult);
    }

    @Test
    public void getChildGuess() {
        Child dummyChild = new Child(TEST_CHILD_FIRST, TEST_CHILD_LAST);
        CoinSide dummyChildGuess = CoinSide.HEAD;
        CoinSide dummyFlipResult = CoinSide.TAIL;
        CoinResult expectedCoinResult = new CoinResult(dummyChild, dummyChildGuess, dummyFlipResult);

        assertEquals(expectedCoinResult.getChildGuess(),dummyChildGuess);
    }

    @Test
    public void getWhoPicked() {
        Child dummyChild = new Child(TEST_CHILD_FIRST, TEST_CHILD_LAST);
        CoinSide dummyChildGuess = CoinSide.HEAD;
        CoinSide dummyFlipResult = CoinSide.TAIL;
        CoinResult expectedCoinResult = new CoinResult(dummyChild, dummyChildGuess, dummyFlipResult);

        assertEquals(expectedCoinResult.getWhoPicked(), dummyChild);
    }

    @Test
    public void getDidPickerWin() {
        Child dummyChild = new Child(TEST_CHILD_FIRST, TEST_CHILD_LAST);
        CoinSide dummyChildGuess = CoinSide.HEAD;
        CoinSide dummyFlipResult = CoinSide.TAIL;
        CoinResult expectedCoinResult = new CoinResult(dummyChild, dummyChildGuess, dummyFlipResult);

        assertEquals(expectedCoinResult.didPickerWin(), (dummyChildGuess.equals(dummyFlipResult)));

    }
}