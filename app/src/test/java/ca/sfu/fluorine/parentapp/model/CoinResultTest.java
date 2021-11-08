package ca.sfu.fluorine.parentapp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ca.sfu.fluorine.parentapp.model.coinflip.CoinResult;
import ca.sfu.fluorine.parentapp.model.children.Child;

class CoinResultTest {
    //local constants
    private final String TEST_CHILD_FIRST = "FIRST";
    private final String TEST_CHILD_LAST = "LAST";

    Child dummyChild = new Child(TEST_CHILD_FIRST, TEST_CHILD_LAST);

    @Test
    public void testCoinResultCreation() {
        CoinResult coinResult = new CoinResult(dummyChild, true, true);
        assertNotNull(coinResult.getDateTimeOfFlip());
        assertEquals(coinResult.getWhoPicked(), dummyChild);
        assertTrue(coinResult.getGuessIsHead());
        assertTrue(coinResult.getResultIsHead());
    }

    @Test
    public void testGuessValidation() {
        CoinResult coinResult = new CoinResult(dummyChild, true, true);
        assertTrue(coinResult.didPickerWin());
        coinResult = new CoinResult(dummyChild, false, false);
        assertTrue(coinResult.didPickerWin());
        coinResult = new CoinResult(dummyChild, true, false);
        assertFalse(coinResult.didPickerWin());
        coinResult = new CoinResult(dummyChild, false, true);
        assertFalse(coinResult.didPickerWin());
    }
}