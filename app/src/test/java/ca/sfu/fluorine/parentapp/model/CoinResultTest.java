package ca.sfu.fluorine.parentapp.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class CoinResultTest {
    String headString = "HEADS";
    String tailString = "TAILS";
    String childFirst = "FIRST";
    String childLast = "LAST";

    @Test
    public void getDateTimeOfFlip() {
        CoinResult expectedCoinResult = new CoinResult();
        LocalDateTime flipTime = expectedCoinResult.dateTimeOfFlip;

        assertEquals(expectedCoinResult.getDateTimeOfFlip(), flipTime);
    }

    @Test
    public void getFlipResult() {
        CoinResult expectedCoinResult = new CoinResult();
        String actualFlipResult = headString;

        expectedCoinResult.flipResult = actualFlipResult;
        assertEquals(expectedCoinResult.getFlipResult(), actualFlipResult);
    }

    @Test
    public void setFlipResult() {
        CoinResult expectedCoinResult = new CoinResult();

        expectedCoinResult.setFlipResult();

       assertTrue(expectedCoinResult.flipResult.equals(headString)
               || expectedCoinResult.flipResult.equals(tailString) );

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
        expectedCoinResult.flipResult = headString;
        expectedCoinResult.childGuess = headString;
        expectedCoinResult.setDidPickerWin();

        assertEquals(expectedCoinResult.didPickerWin, true);

    }

    @Test
    public void setCoinResult() {
        CoinResult expectedCoinResult = new CoinResult();
        Child child = new Child(childFirst, childLast);
        expectedCoinResult.setCoinResult(child, headString);

        assertTrue(expectedCoinResult.flipResult.equals(headString)
                || expectedCoinResult.flipResult.equals(tailString));
        assertTrue(expectedCoinResult.didPickerWin
                || !(expectedCoinResult.didPickerWin));
        assertTrue(expectedCoinResult.childGuess.equals(headString)
                || expectedCoinResult.childGuess.equals(tailString));
        assertEquals(expectedCoinResult.whoPicked, child);



    }

    @Test
    public void getCoinResult(){
        CoinResult expectedCoinResult = new CoinResult();
        String childFlipGuess = headString;

        expectedCoinResult.setFlipResult();
        expectedCoinResult.setDidPickerWin();
        expectedCoinResult.setChildGuess(childFlipGuess);

        assertEquals(expectedCoinResult.getFlipResult(), expectedCoinResult.flipResult);
        assertEquals(expectedCoinResult.getDidPickerWin(), expectedCoinResult.didPickerWin);
        assertEquals(expectedCoinResult.getChildGuess(), expectedCoinResult.childGuess);

    }

    @Test
    public void setChildGuess(){
        CoinResult coinResult = new CoinResult();
        String flipGuess = headString;
        coinResult.setChildGuess(flipGuess);

        assertEquals(coinResult.childGuess, flipGuess);

    }

    @Test
    public void getChildGuess(){
        CoinResult coinResult = new CoinResult();
        String flipGuess = tailString;
        coinResult.childGuess = flipGuess;

        assertEquals(coinResult.getChildGuess(), flipGuess);

    }

    @Test
    public void editChildGuess(){
        Child child = new Child(childFirst, childLast);
        CoinResult coinResult = new CoinResult();

        coinResult.setCoinResult(child, tailString);
        coinResult.editCoinResult(child, headString);

        assertEquals(coinResult.childGuess, headString);
    }
}