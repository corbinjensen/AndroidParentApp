package ca.sfu.fluorine.parentapp.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class CoinResultTest {

    @Test
    public void getDateTimeOfFlip() {
        CoinResult expectedCoinResult = new CoinResult();
        LocalDateTime flipTime = expectedCoinResult.dateTimeOfFlip;

        assertEquals(expectedCoinResult.getDateTimeOfFlip(), flipTime);
    }

    @Test
    public void getFlipResult() {
        CoinResult expectedCoinResult = new CoinResult();
        String actualFlipResult = "HEADS";

        expectedCoinResult.flipResult = actualFlipResult;
        assertEquals(expectedCoinResult.getFlipResult(), actualFlipResult);
    }

    @Test
    public void setFlipResult() {
        CoinResult expectedCoinResult = new CoinResult();
        boolean flipResult = true;
        String headsResult = "HEADS";
        expectedCoinResult.setFlipResult(flipResult);

       assertEquals(expectedCoinResult.flipResult, headsResult);

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
        String kidGuess = "HEADS";
        expectedCoinResult.flipResult = kidGuess;
        expectedCoinResult.setDidPickerWin(kidGuess);

        assertEquals(expectedCoinResult.didPickerWin, true);

    }
}