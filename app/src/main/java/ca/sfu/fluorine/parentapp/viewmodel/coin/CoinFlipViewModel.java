package ca.sfu.fluorine.parentapp.viewmodel.coin;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ca.sfu.fluorine.parentapp.model.AppDatabase;
import ca.sfu.fluorine.parentapp.model.coinflip.CoinResult;
import ca.sfu.fluorine.parentapp.model.coinflip.CoinResultDao;
import ca.sfu.fluorine.parentapp.model.composite.CoinResultWithChild;
import ca.sfu.fluorine.parentapp.service.IconService;
import ca.sfu.fluorine.parentapp.store.ImageInternalStorage;

public class CoinFlipViewModel extends AndroidViewModel {
    private final CoinResultDao coinResultDao;
    private final LiveData<List<CoinResultWithChild>> liveCoinResultsWithChildren;
    private final IconService service;

    public CoinFlipViewModel(@NonNull Application application) {
        super(application);
        coinResultDao = AppDatabase.getInstance(application).coinResultDao();
        liveCoinResultsWithChildren = coinResultDao.getAllCoinResultsWithChildren();
        service = new IconService(ImageInternalStorage.getInstance(application));
    }

    public LiveData<List<CoinResultWithChild>> getLiveCoinResultsWithChildren() {
        return liveCoinResultsWithChildren;
    }

    public void addNewCoinResult(CoinResult coinResult) {
        coinResultDao.add(coinResult);
    }

    public Bitmap loadChildIconFromCoinResult(CoinResultWithChild coinResult) {
        return service.loadBitmapFrom(coinResult.getChild());
    }
}
