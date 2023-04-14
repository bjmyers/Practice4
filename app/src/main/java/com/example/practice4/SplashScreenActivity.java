package com.example.practice4;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class SplashScreenActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private TextView mSplashText;

    private final int delayMs = 4000;

    // Whimsical splash texts for loading, will pick a random one each time
    private final String[] splashTexts = new String[] {"Launching Invasion", "Recharging Warp Drive",
        "Setting phasers to stun", "Violating laws of robotics", "Abducting cows"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Pick a splash text randomly
        final Random rand = new Random();
        final String splashText = splashTexts[rand.nextInt(splashTexts.length)];
        mSplashText = findViewById(R.id.splash_text);
        mSplashText.setText(splashText);

        // Wire and start the progress bar
        mProgressBar = findViewById(R.id.splash_progress_bar);
        // Make an ObjectAnimator to animate our progress bar, have it vary its progress between 0
        // and 100
        ObjectAnimator animation = ObjectAnimator.ofInt(mProgressBar, "progress", 0, 100);
        // Set the splash screen to load on a delay
        animation.setDuration(delayMs);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();

        // Wait to launch the main activity until the splash screen is finished
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the next activity
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, delayMs);
    }
}