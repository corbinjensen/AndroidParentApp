package ca.sfu.fluorine.parentapp.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ca.sfu.fluorine.parentapp.model.CoinResult.CoinSide;
import ca.sfu.fluorine.parentapp.model.children.Child;

class CoinResultTest {
    //local constants
    private final String TEST_CHILD_FIRST = "FIRST";
    private final String TEST_CHILD_LAST = "LAST";

    Child dummyChild = new Child(TEST_CHILD_FIRST, TEST_CHILD_LAST);
    CoinSide dummyChildGuess = CoinSide.HEAD;
    CoinSide dummyFlipResult = CoinSide.TAIL;
    CoinResult expectedCoinResult = new CoinResult(dummyChild, dummyChildGuess, dummyFlipResult);

    @Test
    public void CoinResult(){
        assertNotNull(expectedCoinResult.getDateTimeOfFlip());
        assertEquals(expectedCoinResult.getWhoPicked(), dummyChild);
        assertEquals(expectedCoinResult.getChildGuess(), dummyChildGuess);
        assertEquals(expectedCoinResult.getFlipResult(), dummyFlipResult);
        assertEquals(expectedCoinResult.didPickerWin(), (dummyChildGuess.equals(dummyFlipResult)));

    }


    @Test
    public void getDateTimeOfFlip() {
        assertNotNull(expectedCoinResult.getDateTimeOfFlip());
    }

    @Test
    public void getFlipResult() {
        assertEquals(expectedCoinResult.getFlipResult(), dummyFlipResult);
    }

    @Test
    public void getChildGuess() {
        assertEquals(expectedCoinResult.getChildGuess(),dummyChildGuess);
    }

    @Test
    public void getWhoPicked() {
        assertEquals(expectedCoinResult.getWhoPicked(), dummyChild);
    }

    @Test
    public void getDidPickerWin() {
        assertEquals(expectedCoinResult.didPickerWin(), (dummyChildGuess.equals(dummyFlipResult)));

    }
}