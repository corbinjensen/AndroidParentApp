package ca.sfu.fluorine.parentapp.model;

import java.util.LinkedList;

public class CoinFlipHistory {
    //linked list of Coin Flip cases.
    LinkedList<CoinResult> coinResultHistory = new LinkedList<CoinResult>();
    //int num of flips total
    int numCoinFlipsInHistory;

    //add coinflip to list
    public void addCoinResultToHistory(CoinResult flipResult){
        this.coinResultHistory.add(flipResult);
    }

    //remove coinFlip from list at index
    public void removeCoinResultFromHistory(int index){
        this.coinResultHistory.remove(index);
    }

    public void removeLastCoinResultFromHistory(){
        this.coinResultHistory.remove(numCoinFlipsInHistory-1);
    }

    //edit existing coin flip from INDEX (start at 0)
    public void editCoinResultFromHistory(Child child, String guess,int index){
        CoinResult newCoinResult = new CoinResult();
        newCoinResult = coinResultHistory.get(index);
        child = newCoinResult.whoPicked;

        newCoinResult.setCoinResult(child,guess);

    }

}
