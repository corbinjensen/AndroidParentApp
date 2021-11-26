package ca.sfu.fluorine.parentapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import ca.sfu.fluorine.parentapp.model.coinflip.CoinResult;
import ca.sfu.fluorine.parentapp.model.coinflip.CoinResultDao;
import ca.sfu.fluorine.parentapp.model.composite.CoinResultWithChild;
import ca.sfu.fluorine.parentapp.service.IconService;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CoinFlipViewModel extends ViewModel {
    private final CoinResultDao coinResultDao;
    private final LiveData<List<CoinResultWithChild>> liveCoinResultsWithChildren;
    private final IconService iconService;

    @Inject
    public CoinFlipViewModel(@NonNull CoinResultDao coinResultDao, @NonNull IconService iconService) {
        super();
        this.coinResultDao = coinResultDao;
        liveCoinResultsWithChildren = coinResultDao.getAllCoinResultsWithChildren();
        this.iconService = iconService;
    }

    public LiveData<List<CoinResultWithChild>> getLiveCoinResultsWithChildren() {
        return liveCoinResultsWithChildren;
    }

    public void addNewCoinResult(CoinResult coinResult) {
        coinResultDao.add(coinResult);
    }

    public IconService getIconService() {
        return iconService;
    }
}
