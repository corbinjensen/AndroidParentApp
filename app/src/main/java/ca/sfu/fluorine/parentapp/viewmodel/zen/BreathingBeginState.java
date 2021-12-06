package ca.sfu.fluorine.parentapp.viewmodel.zen;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.view.calm.zen.ZenActivity;

public class BreathingBeginState extends BreathingState {
    private int breathCount, minBreathCount, maxBreathCount;
    private final BreathingViewModel viewModel;

    public BreathingBeginState(ZenActivity activity) {
        super(activity);
        minBreathCount = activity.getResources().getInteger(R.integer.min_breath_count);
        maxBreathCount = activity.getResources().getInteger(R.integer.max_breath_count);
        viewModel = new ViewModelProvider(activity).get(BreathingViewModel.class);
        breathCount = viewModel.getBreathCount();
        updateBreathCount();
        binding.addBreath.setOnClickListener(incrementBreathCount);
        binding.subtractBreath.setOnClickListener(decrementBreathCount);

    }

    @Override
    public void onEnter() {
        binding.breatheButton.setText(R.string.begin);
        binding.breathCountSelection.setVisibility(View.VISIBLE);
    }

    @Override
    public void onExit() {
        activity.getBinding().breathCountSelection.setVisibility(View.GONE);
    }

    @Override
    public void onButtonDown() {
        activity.setState(new InhalingState(activity, viewModel.getBreathCount()));
    }

    private final View.OnClickListener incrementBreathCount = (View v) -> {
        if (breathCount >= maxBreathCount) {
            breathCount = maxBreathCount;
        } else {
            breathCount++;
        }
        updateBreathCount();
    };

    private final View.OnClickListener decrementBreathCount = (View v) -> {
        if (breathCount <= minBreathCount) {
            breathCount = minBreathCount;
        } else {
            breathCount--;
        }
        updateBreathCount();
    };

    private void updateBreathCount() {
        String breathCountDisplay = activity.getResources()
                .getQuantityString(R.plurals.breath_count, breathCount, breathCount);
        binding.breathCount.setText(breathCountDisplay);
        viewModel.setBreathCount(breathCount);
    }
}
