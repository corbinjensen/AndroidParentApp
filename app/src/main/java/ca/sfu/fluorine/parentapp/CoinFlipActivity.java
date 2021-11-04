package ca.sfu.fluorine.parentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

public class CoinFlipActivity extends AppCompatActivity {

    public static final Random RANDOM = new Random();
    private ImageView heads;
    private ImageView tails;
    private Button btn;
    Random random = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_flip);
        heads = findViewById(R.id.heads);
        tails = findViewById(R.id.tails);
        btn = findViewById(R.id.flip_coin_btn);
        float scale = getApplicationContext()
                .getResources()
                .getDisplayMetrics().density;

        heads.setCameraDistance(8000 * scale);
        tails.setCameraDistance(8000 * scale);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipCoin(random.nextBoolean()).start();
            }
        });
    }

    private AnimatorSet flipCoin(boolean result) {
        ArrayList<Animator> animationSequence = new ArrayList<>();
        int randomInt = random.nextInt(10) + 10;

        for (int i = 0; i < randomInt; i++) {
            animationSequence.add(halfRotation(heads, tails));
            animationSequence.add(halfRotation(tails, heads));
        }

        if (result) {
            animationSequence.add(halfRotation(heads, tails));
        }

        AnimatorSet flipAnimation = new AnimatorSet();
        flipAnimation.playSequentially(animationSequence);

        // Styling up animation
        flipAnimation.setInterpolator(new AccelerateDecelerateInterpolator(this, null));

        // Define accompanying action for the animation
        flipAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                btn.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                btn.setEnabled(true);
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

    private AnimatorSet halfRotation(View start, View end) {
        Animator animationOut = AnimatorInflater.loadAnimator(this, R.animator.out_animator);
        Animator animationIn = AnimatorInflater.loadAnimator(this, R.animator.in_animator);
        animationOut.setTarget(start);
        animationIn.setTarget(end);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animationIn, animationOut);
        return animatorSet;
    }
  }