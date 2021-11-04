package ca.sfu.fluorine.parentapp.model;

import java.util.LinkedList;

public class CoinFlipHistory {
    //linked list of Coin Flip cases.
    LinkedList<CoinResult> coinResultHistory = new LinkedList<CoinResult>();
    //int num of flips total
    int numCoinFlipsInHistory;

    //add coin flip to list
    public void addCoinResultToHistory(CoinResult flipResult){
        this.coinResultHistory.add(flipResult);
        this.numCoinFlipsInHistory++;
    }

    //remove coinFlip from list at index
    public void removeCoinResultFromHistory(int index){
        this.coinResultHistory.remove(index);
        this.numCoinFlipsInHistory--;
    }

    public void removeLastCoinResultFromHistory(){
        this.coinResultHistory.removeLast();
        this.numCoinFlipsInHistory--;
    }

}
