package ca.sfu.fluorine.parentapp.model.coinflip;

/**
 * CoinResult.java
 *
 * This class represents a coin flip.
 */
public class CoinResult {
    final private long dateTimeOfFlip; // Sets time to creation of CoinResult
    final private boolean resultIsHead, guessIsHead;
    final private Child whoPicked;

    //Constructor
    public CoinResult(Child childWhoPicked, boolean guessIsHead, boolean resultIsHead){
        this.dateTimeOfFlip = Calendar.getInstance().getTimeInMillis();
        this.resultIsHead = resultIsHead;
        this.whoPicked = childWhoPicked;
        this.guessIsHead = guessIsHead;

    }

    public long getDateTimeOfFlip() {
        return this.dateTimeOfFlip;
    }

    public boolean getResultIsHead() {
        return resultIsHead;
    }

    public boolean getGuessIsHead(){
        return guessIsHead;
    }

    public Child getWhoPicked() {
        return whoPicked;
    }

    public boolean didPickerWin(){
        return guessIsHead == resultIsHead;
    }
}
