package ca.sfu.fluorine.parentapp.model.coinflip;

import java.time.LocalDateTime;

import ca.sfu.fluorine.parentapp.model.children.Child;


public class CoinResult {
    final private LocalDateTime dateTimeOfFlip; // Sets time to creation of CoinResult
    final private boolean resultIsHead, guessIsHead;
    final private Child whoPicked;

    //Constructor
    public CoinResult(Child childWhoPicked, boolean guessIsHead, boolean resultIsHead){
        this.dateTimeOfFlip = LocalDateTime.now();
        this.resultIsHead = resultIsHead;
        this.whoPicked = childWhoPicked;
        this.guessIsHead = guessIsHead;

    }

    public LocalDateTime getDateTimeOfFlip() {
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
