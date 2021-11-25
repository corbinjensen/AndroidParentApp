package ca.sfu.fluorine.parentapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import ca.sfu.fluorine.parentapp.model.coinflip.CoinResult;
import ca.sfu.fluorine.parentapp.model.coinflip.CoinResultDao;
import ca.sfu.fluorine.parentapp.model.composite.CoinResultWithChild;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CoinFlipViewModel extends ViewModel {
    private final CoinResultDao coinResultDao;
    private final LiveData<List<CoinResultWithChild>> liveCoinResultsWithChildren;

    @Inject
    public CoinFlipViewModel(@NonNull CoinResultDao coinResultDao) {
        super();
        this.coinResultDao = coinResultDao;
        liveCoinResultsWithChildren = coinResultDao.getAllCoinResultsWithChildren();
    }

    public LiveData<List<CoinResultWithChild>> getLiveCoinResultsWithChildren() {
        return liveCoinResultsWithChildren;
    }

    public void addNewCoinResult(CoinResult coinResult) {
        coinResultDao.add(coinResult);
    }
}
