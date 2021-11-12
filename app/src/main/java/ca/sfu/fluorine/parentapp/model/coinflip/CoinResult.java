package ca.sfu.fluorine.parentapp.model.coinflip;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import ca.sfu.fluorine.parentapp.model.children.Child;

/**
 * CoinResult.java
 *
 * This class represents a coin flip.
 */
@Entity(
        tableName = "coin_result",
        foreignKeys = @ForeignKey(
                entity = Child.class,
                parentColumns = "child_id",
                childColumns = "selected_child_id",
                onDelete = ForeignKey.CASCADE
        ))
public class CoinResult {
    @ColumnInfo(name = "coin_result_id")
    @PrimaryKey(autoGenerate = true)
    int id;

    long dateTimeOfFlip; // Sets time to creation of CoinResult
    final private boolean resultIsHead, guessIsHead;

    @ColumnInfo(name = "selected_child_id", index = true)
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
