package ca.sfu.fluorine.parentapp.model;

import java.time.LocalDateTime;


public class CoinResult {
    // Use enum to store the sides of the coin
    public enum CoinSide {
        HEAD,
        TAIL
    }

    final private LocalDateTime dateTimeOfFlip; // Sets time to creation of CoinResult
    final private CoinSide flipResult;
    final private Child whoPicked;
    final private CoinSide childGuess;

    //Constructor
    public CoinResult(Child childWhoPicked, CoinSide childGuess, CoinSide flipResult){
        this.dateTimeOfFlip = LocalDateTime.now();
        this.flipResult = flipResult;
        this.whoPicked = childWhoPicked;
        this.childGuess = childGuess;

    }

    public LocalDateTime getDateTimeOfFlip() {
        return this.dateTimeOfFlip;
    }

    public CoinSide getFlipResult() {
        return this.flipResult;
    }

    public CoinSide getChildGuess(){
        return this.childGuess;
    }

    public Child getWhoPicked() {
        return this.whoPicked;
    }

    public boolean didPickerWin(){
        return (this.childGuess.equals(this.flipResult));
    }
}
