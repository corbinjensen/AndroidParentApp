package ca.sfu.fluorine.parentapp.view.coin;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import ca.sfu.fluorine.parentapp.R;

public class CoinFlipActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_flip);
        setTitle(R.string.new_coin_flip);
    }
}