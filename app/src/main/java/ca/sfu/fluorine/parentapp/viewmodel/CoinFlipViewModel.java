package ca.sfu.fluorine.parentapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ca.sfu.fluorine.parentapp.model.AppDatabase;
import ca.sfu.fluorine.parentapp.model.coinflip.CoinResult;
import ca.sfu.fluorine.parentapp.model.coinflip.CoinResultDao;
import ca.sfu.fluorine.parentapp.model.composite.CoinResultWithChild;

public class CoinFlipViewModel extends AndroidViewModel {
    private final CoinResultDao coinResultDao;
    private final LiveData<List<CoinResultWithChild>> liveCoinResultsWithChildren;

    public CoinFlipViewModel(@NonNull Application application) {
        super(application);
        coinResultDao = AppDatabase.getInstance(application).coinResultDao();
        liveCoinResultsWithChildren = coinResultDao.getAllCoinResultsWithChildren();
    }

    public LiveData<List<CoinResultWithChild>> getLiveCoinResultsWithChildren() {
        return liveCoinResultsWithChildren;
    }

    public void addNewCoinResult(CoinResult coinResult) {
        coinResultDao.addNewCoinResult(coinResult);
    }
}
