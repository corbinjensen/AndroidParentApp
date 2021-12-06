package ca.sfu.fluorine.parentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;

import ca.sfu.fluorine.parentapp.databinding.ActivityHelpBinding;

/**
 * Represents a helping screen containing all developers' info and citations
 */
public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHelpBinding binding = ActivityHelpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MovementMethod movement = LinkMovementMethod.getInstance();

        // Icons
        binding.appIconCitation.setMovementMethod(movement);
        binding.childIconCitation.setMovementMethod(movement);
        binding.coinIconCitation.setMovementMethod(movement);
        binding.taskIconCitation.setMovementMethod(movement);
        binding.timerIconCitation.setMovementMethod(movement);
        binding.windIconCitation.setMovementMethod(movement);
        binding.tachometerIconCitation.setMovementMethod(movement);

        // Libraries
        binding.pulsatorCitation.setMovementMethod(movement);
        binding.imageCropperCitation.setMovementMethod(movement);

        // Clip arts
        binding.citationBoard.setMovementMethod(movement);
        binding.citationChildren.setMovementMethod(movement);
        binding.citationCoinFlip.setMovementMethod(movement);
        binding.citationBox.setMovementMethod(movement);
    }
}
