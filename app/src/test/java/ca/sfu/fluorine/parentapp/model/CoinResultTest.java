package ca.sfu.fluorine.parentapp.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class CoinResultTest {
    //local constants
    private final String HEAD_STRING = "HEADS";
    private final String TAIL_STRING = "TAILS";
    private final String TEST_CHILD_FIRST = "FIRST";
    private final String TEST_CHILD_LAST = "LAST";

    @Test       
    public void getDateTimeOfFlip() {
        CoinResult expectedCoinResult = new CoinResult();
        LocalDateTime flipTime = expectedCoinResult.dateTimeOfFlip;

        assertEquals(expectedCoinResult.getDateTimeOfFlip(), flipTime);
    }

    @Test
    public void getFlipResult() {
        CoinResult expectedCoinResult = new CoinResult();
        String actualFlipResult = HEAD_STRING;

        expectedCoinResult.flipResult = actualFlipResult;
        assertEquals(expectedCoinResult.getFlipResult(), actualFlipResult);
    }

    @Test
    public void setFlipResult() {
        CoinResult expectedCoinResult = new CoinResult();

        expectedCoinResult.setFlipResult();

       assertTrue(expectedCoinResult.flipResult.equals(HEAD_STRING)
               || expectedCoinResult.flipResult.equals(TAIL_STRING) );

    }

    @Test
    public void getDidPickerWin() {
        CoinResult expectedCoinResult = new CoinResult();
        boolean pickerWon = true;
        expectedCoinResult.didPickerWin = pickerWon;

        assertEquals(expectedCoinResult.getDidPickerWin(), pickerWon);

    }

    @Test
    public void setDidPickerWin() {
        CoinResult expectedCoinResult = new CoinResult();
        expectedCoinResult.flipResult = HEAD_STRING;
        expectedCoinResult.childGuess = HEAD_STRING;
        expectedCoinResult.setDidPickerWin();

        assertTrue(expectedCoinResult.didPickerWin);

    }

    @Test
    public void setCoinResult() {
        CoinResult expectedCoinResult = new CoinResult();
        Child child = new Child(TEST_CHILD_FIRST, TEST_CHILD_LAST);
        expectedCoinResult.setCoinResult(child, HEAD_STRING);

        assertEquals(expectedCoinResult.childGuess, HEAD_STRING);
        assertEquals(expectedCoinResult.whoPicked, child);
        assertEquals(expectedCoinResult.didPickerWin, (expectedCoinResult.childGuess.equals(expectedCoinResult.flipResult)));
    }

    @Test
    public void setChildGuess(){
        CoinResult coinResult = new CoinResult();
        String flipGuess = HEAD_STRING;
        coinResult.setChildGuess(flipGuess);

        assertEquals(coinResult.childGuess, flipGuess);

    }

    @Test
    public void getChildGuess(){
        CoinResult coinResult = new CoinResult();
        String flipGuess = TAIL_STRING;
        coinResult.childGuess = flipGuess;

        assertEquals(coinResult.getChildGuess(), flipGuess);

    }

    @Test
    public void getWhoPicked(){
        CoinResult expectedCoinResult = new CoinResult();
        Child child = new Child(TEST_CHILD_FIRST, TEST_CHILD_LAST);
        expectedCoinResult.whoPicked = child;

        assertEquals(expectedCoinResult.getWhoPicked(), child);
    }

    @Test
    public void editChildGuess(){
        Child child = new Child(TEST_CHILD_FIRST, TEST_CHILD_LAST);
        CoinResult coinResult = new CoinResult();

        coinResult.setCoinResult(child, TAIL_STRING);
        coinResult.editCoinResult(child, HEAD_STRING);

        assertEquals(coinResult.childGuess, HEAD_STRING);
    }
}