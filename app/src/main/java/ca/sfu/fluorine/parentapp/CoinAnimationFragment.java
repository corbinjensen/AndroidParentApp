package ca.sfu.fluorine.parentapp;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.Random;

import ca.sfu.fluorine.parentapp.databinding.FragmentCoinAnimationBinding;
import ca.sfu.fluorine.parentapp.databinding.FragmentCoinFlipBinding;

public class CoinAnimationFragment extends Fragment {
	private Context context;
	private FragmentCoinAnimationBinding binding;
	private static final Random random = new Random();
	private final boolean result = random.nextBoolean();

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		this.context = context;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		binding = FragmentCoinAnimationBinding.inflate(inflater, container, false);
		return binding.getRoot();
	}

	@Override
	public void onStart() {
		super.onStart();
		flipCoin(result).start();
	}

	private AnimatorSet flipCoin(boolean result) {
		ArrayList<Animator> animationSequence = new ArrayList<>();
		int randomInt = random.nextInt(10) + 10;

		for (int i = 0; i < randomInt; i++) {
			animationSequence.add(halfRotation(binding.heads, binding.tails));
			animationSequence.add(halfRotation(binding.tails, binding.heads));
		}

		if (result) {
			animationSequence.add(halfRotation(binding.heads, binding.tails));
		}

		AnimatorSet flipAnimation = new AnimatorSet();
		flipAnimation.playSequentially(animationSequence);

		// Styling up animation
		flipAnimation.setInterpolator(new AccelerateDecelerateInterpolator(context, null));

		return flipAnimation;
	}

	private AnimatorSet halfRotation(View start, View end) {
		Animator animationOut = AnimatorInflater.loadAnimator(context, R.animator.out_animator);
		Animator animationIn = AnimatorInflater.loadAnimator(context, R.animator.in_animator);
		animationOut.setTarget(start);
		animationIn.setTarget(end);

		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(animationIn, animationOut);
		return animatorSet;
	}
}