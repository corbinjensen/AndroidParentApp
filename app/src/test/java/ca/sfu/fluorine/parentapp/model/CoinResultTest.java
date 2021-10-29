package ca.sfu.fluorine.parentapp.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class CoinResultTest {
    String headString = "HEADS";
    String tailString = "TAILS";

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
    public void getIfPickerWon() {
        CoinResult expectedCoinResult = new CoinResult();
        boolean pickerWon = true;
        expectedCoinResult.didPickerWin = pickerWon;

        assertEquals(expectedCoinResult.getIfPickerWon(), pickerWon);

    }

    @Test
    public void setDidPickerWin() {
        CoinResult expectedCoinResult = new CoinResult();
        String kidGuess = headString;
        expectedCoinResult.flipResult = kidGuess;
        expectedCoinResult.setDidPickerWin();

        assertEquals(expectedCoinResult.didPickerWin, true);

    }

    @Test
    public void setCoinResult() {
        CoinResult expectedCoinResult = new CoinResult();
        Child child = new Child(); // TODO : Add init & loose data for child.
        expectedCoinResult.setCoinResult(child);

        assertTrue(expectedCoinResult.flipResult == headString
                || expectedCoinResult.flipResult == tailString);
        assertTrue(expectedCoinResult.didPickerWin == true
                || expectedCoinResult.didPickerWin == false);
        assertTrue(expectedCoinResult.childGuess == headString
                || expectedCoinResult.childGuess == tailString);
        assertTrue(expectedCoinResult.whoPicked, child); // TODO : CHILD



    }

    @Test
    public void getCoinResult(){
        CoinResult expectedCoinResult = new CoinResult();
        Child child = new Child();
        child.first = "FIRST";
        child.last = "LAST";

        expectedCoinResult.setFlipResult();
        expectedCoinResult.setDidPickerWin(child);
        expectedCoinResult.setChildGuess(child);

        assertEquals(expectedCoinResult.getFlipResult(), expectedCoinResult.flipResult);
        assertEquals(expectedCoinResult.getIfPickerWon(), expectedCoinResult.didPickerWin);
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
}