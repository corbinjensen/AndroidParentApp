package ca.sfu.fluorine.parentapp.view.coin;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.Random;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.databinding.FragmentCoinAnimationBinding;
import ca.sfu.fluorine.parentapp.model.coinflip.CoinResult;
import ca.sfu.fluorine.parentapp.view.utils.NoActionBarFragment;
import ca.sfu.fluorine.parentapp.viewmodel.CoinFlipViewModel;
import dagger.hilt.android.AndroidEntryPoint;

/**
 * CoinAnimationFragment
 *
 * Represents the UI for the coin flip animation.
 */
@AndroidEntryPoint
public class CoinAnimationFragment extends NoActionBarFragment {
	private FragmentCoinAnimationBinding binding;
	private static final Random random = new Random();
	private final boolean resultIsHead = random.nextBoolean();
	private CoinFlipViewModel viewModel;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		binding = FragmentCoinAnimationBinding.inflate(inflater, container, false);
		viewModel = new ViewModelProvider(this).get(CoinFlipViewModel.class);
		return binding.getRoot();
	}

	@Override
	public void onStart() {
		super.onStart();

		// Set up camera distance
		float scale = requireContext().getResources()
				.getDisplayMetrics().density;
		binding.heads.setCameraDistance(8000 * scale);
		binding.tails.setCameraDistance(8000 * scale);

		// Add listeners to the buttons
		binding.buttonDone.setOnClickListener(btnView ->
				requireActivity().finish()
		);

		binding.buttonNewTurn.setOnClickListener(btnView ->
				NavHostFragment.findNavController(this)
						.navigate(R.id.new_coin_flip_action)
		);

		// Start the animation
		flipCoin(resultIsHead).start();
	}

	private AnimatorSet flipCoin(boolean resultIsHead) {
		ArrayList<Animator> animationSequence = new ArrayList<>();
		int randomInt = random.nextInt(5) + 5;

		for (int i = 0; i < randomInt; i++) {
			animationSequence.add(flipView(binding.heads, binding.tails));
			animationSequence.add(flipView(binding.tails, binding.heads));
		}

		if (!resultIsHead) {
			animationSequence.add(flipView(binding.heads, binding.tails));
		}

		AnimatorSet flipAnimation = new AnimatorSet();
		flipAnimation.playSequentially(animationSequence);

		// Styling up animation
		flipAnimation.setInterpolator(
				new AccelerateDecelerateInterpolator(requireContext(), null));

		// Set up action for the animation
		flipAnimation.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animator) {
			}

			@Override
			public void onAnimationEnd(Animator animator) {
				// Show the button
				binding.buttonDone.setVisibility(View.VISIBLE);
				binding.buttonNewTurn.setVisibility(View.VISIBLE);
				binding.resultTitle.setVisibility(View.VISIBLE);
				binding.resultTitle.setText(resultIsHead ? R.string.head : R.string.tail);

				saveCoinFlip();
			}

			@Override
			public void onAnimationCancel(Animator animator) {
			}

			@Override
			public void onAnimationRepeat(Animator animator) {
			}
		});

		return flipAnimation;
	}

	private AnimatorSet flipView(View start, View end) {
		Animator animationOut = AnimatorInflater.loadAnimator(requireContext(), R.animator.out_animator);
		Animator animationIn = AnimatorInflater.loadAnimator(requireContext(), R.animator.in_animator);
		animationOut.setTarget(start);
		animationIn.setTarget(end);

		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(animationIn, animationOut);
		return animatorSet;
	}

	private void saveCoinFlip() {
		CoinAnimationFragmentArgs arguments =
				CoinAnimationFragmentArgs.fromBundle(getArguments());
		if (!arguments.getWithoutChild()) {
			CoinResult newResult = new CoinResult(
					arguments.getChildId(), arguments.getCoinSide(), resultIsHead);
			viewModel.addNewCoinResult(newResult);
		}
	}
}
