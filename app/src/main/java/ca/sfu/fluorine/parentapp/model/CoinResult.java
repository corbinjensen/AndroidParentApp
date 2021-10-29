package ca.sfu.fluorine.parentapp.model;

import java.time.LocalDateTime;
import java.util.Random;

public class CoinResult {
    public LocalDateTime dateTimeOfFlip = LocalDateTime.now(); // Sets time to creation of CoinResult
    public String flipResult;
    public Child whoPicked;
    public boolean didPickerWin;
    public String childGuess;

    public LocalDateTime getDateTimeOfFlip() {
        return this.dateTimeOfFlip;
    }

    public String getFlipResult() {
        return this.flipResult;
    }

    public boolean flipResult(){
        Random rand = new Random();
        boolean coinFlipResult = rand.nextBoolean();
        return coinFlipResult;
    }

    public void setChildGuess(String guess){
        this.childGuess = guess;
    }

    public String getChildGuess(){
        return this.childGuess;
    }

    public void setFlipResult() {
        String resultOfFlip;
        boolean isHeads = flipResult();
        String headsResult = "HEADS";
        String tailsResult = "TAILS";

        //Assume if isHeads is true result = heads, false = tails
        resultOfFlip = isHeads ? headsResult : tailsResult;

        this.flipResult = resultOfFlip;
    }

    public Child getWhoPicked() { //TODO : Add functionality for Child getter
        return this.whoPicked;
    }

    public void setWhoPicked(Child whoPicked) { //TODO : Add functionality for Child setter
        this.whoPicked = whoPicked;
    }

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
        this.setDidPickerWin();
        this.setWhoPicked(child);
        this.setChildGuess(guess);
    }

    public void editCoinResult(Child child, String guess){
        this.dateTimeOfFlip = LocalDateTime.now();
        this.setWhoPicked(child);
        this.setChildGuess(guess);
        this.setDidPickerWin();

    }
}
