package ca.sfu.fluorine.parentapp.model.coinflip;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CoinFlipHistory {
    private static CoinFlipHistory instance;
    private SharedPreferences preferences;
    private static final Gson gson = new Gson();
    private static final Type COIN_FLIP_LIST = new TypeToken<ArrayList<CoinResult>>(){}.getType();

    private static final String KEY = "flip";

    List<CoinResult> coinResultHistory = new ArrayList<>();

    public static CoinFlipHistory getInstance(Context context){
        if(instance == null) {
            instance = new CoinFlipHistory();
        }
        instance.preferences = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return instance;
    }

    public List<CoinResult> getCoinFlip(){
        loadCoinFlipFromPreferences();
        return coinResultHistory;
    }

    public CoinResult getCoinFlipAtIndex(int index){
        if (index < 0 || index > coinResultHistory.size()){
            return null;
        }
        return coinResultHistory.get(index);
    }

    //add coin flip to list
    public void addCoinResultToHistory(CoinResult flipResult){
        coinResultHistory.add(flipResult);
        saveCoinResultToPreferences();
    }

    //remove coinFlip from list at index
    public void removeCoinResultFromHistory(int index){
        this.coinResultHistory.remove(index);
        saveCoinResultToPreferences();
    }

    private void saveCoinResultToPreferences() {
        String jsonData = gson.toJson(coinResultHistory);
        preferences.edit().putString(KEY, jsonData).apply();
    }

    private void loadCoinFlipFromPreferences() {
        coinResultHistory.clear();
        String jsonData = preferences.getString(KEY, "[]");
        coinResultHistory = gson.fromJson(jsonData, COIN_FLIP_LIST);
    }
}
