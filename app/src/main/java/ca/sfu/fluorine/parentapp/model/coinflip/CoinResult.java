package ca.sfu.fluorine.parentapp.model.coinflip;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.util.Calendar;

import ca.sfu.fluorine.parentapp.model.children.Child;

/**
 * CoinResult.java
 *
 * This class represents a coin flip.
 */
@Entity(tableName = "coin_result")
public class CoinResult {
    @PrimaryKey(autoGenerate = true)
    int id;

    long dateTimeOfFlip; // Sets time to creation of CoinResult
    final private boolean resultIsHead, guessIsHead;

    @ColumnInfo(name = "child_id")
    int childId;

    //Constructor
    public CoinResult(int childId, boolean guessIsHead, boolean resultIsHead){
        this.dateTimeOfFlip = System.currentTimeMillis();
        this.resultIsHead = resultIsHead;
        this.guessIsHead = guessIsHead;
        this.childId = childId;
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

    public int getChildId() {
        return childId;
    }

    public boolean didPickerWin(){
        return guessIsHead == resultIsHead;
    }
}
