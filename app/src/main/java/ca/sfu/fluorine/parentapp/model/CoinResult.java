package ca.sfu.fluorine.parentapp.model;

import java.time.LocalDateTime;

public class CoinResult {
    public LocalDateTime dateTimeOfFlip = LocalDateTime.now(); // Sets time to creation of CoinResult
    public String flipResult;
    //public Child whoPicked; //TODO : Add Child functionality
    public boolean didPickerWin;

    public LocalDateTime getDateTimeOfFlip() {
        return this.dateTimeOfFlip;
    }

    public String getFlipResult() {
        return this.flipResult;
    }

    public void setFlipResult(boolean flipResult) {
        String resultOfFlip;
        boolean isHeads = flipResult; // TODO : Add coinFlip() function ie bool return of random number 0/1
        String headsResult = "HEADS";
        String tailsResult = "TAILS";

        //Assume if isHeads is true result = heads, false = tails
        resultOfFlip = isHeads ? headsResult : tailsResult;

        this.flipResult = resultOfFlip;
    }

    /*
    public Child getWhoPicked() { //TODO : Add functionality for Child getter
        return this.whoPicked;
    }

    public void setWhoPicked(Child whoPicked) { //TODO : Add functionality for Child setter
        this.whoPicked = whoPicked;
    }
     */
    public boolean getIfPickerWon() {
        return didPickerWin;
    }

    public void setDidPickerWin(String childGuess) {
        boolean pickerWin = false;
        if (childGuess.equals(this.flipResult)){
            pickerWin = true;
        }
        this.didPickerWin = pickerWin;
    }
}
