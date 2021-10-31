package ca.sfu.fluorine.parentapp.model;

import java.time.LocalDateTime;
import java.util.Random;

public class CoinResult {
    // Use enum to store the sides of the coin
    public enum CoinSide {
        HEAD,
        TAIL
    }

    // TODO: Convert these to private (maybe final as well)
    public LocalDateTime dateTimeOfFlip = LocalDateTime.now(); // Sets time to creation of CoinResult
    public String flipResult;
    public Child whoPicked;
    public boolean didPickerWin; // May be not necessary
    public String childGuess;

    // TODO: Add a constructor and remove all setters

    public LocalDateTime getDateTimeOfFlip() {
        return this.dateTimeOfFlip;
    }

    public String getFlipResult() {
        return this.flipResult;
    }

    // TODO: Maybe don't need (CoinResult doesn't have responsibility to flip coins)
    public boolean flipResult(){
        Random rand = new Random();
        return rand.nextBoolean();
    }

    public void setChildGuess(String guess){
        this.childGuess = guess;
    }

    public String getChildGuess(){
        return this.childGuess;
    }

    public void setFlipResult() {

        boolean isHeads = flipResult();

        // TODO: Please remove this hard-coded string
        String headsResult = "HEADS";
        String tailsResult = "TAILS";

        //Assume if isHeads is true result = heads, else false = tails
       String resultOfFlip = isHeads ? headsResult : tailsResult;

        this.flipResult = resultOfFlip;
    }

    public Child getWhoPicked() {
        return this.whoPicked;
    }

    public void setWhoPicked(Child whoPicked) {
        this.whoPicked = whoPicked;
    }

    // TODO: Refer to line 16
    public boolean getDidPickerWin() {
        return this.didPickerWin;
    }

    public void setDidPickerWin() {
        boolean pickerWin = false;
        if (this.childGuess.equals(this.flipResult)){
            pickerWin = true;
        }
        this.didPickerWin = pickerWin;
    }

    public void setCoinResult(Child child, String guess){

        this.setFlipResult();
        this.setChildGuess(guess);
        this.setWhoPicked(child);
        this.setDidPickerWin();

    }

    public void editCoinResult(Child child, String guess){
        this.setWhoPicked(child);
        this.setChildGuess(guess);
        this.setDidPickerWin();

    }
}
