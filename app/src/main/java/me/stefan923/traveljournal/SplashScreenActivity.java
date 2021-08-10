package me.stefan923.traveljournal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {

    private static final long SPLASH_SCREEN = 5000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Animation travelJournalAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_screen_animation);
        ImageView travelJournalImage = findViewById(R.id.splashScreenText);
        travelJournalImage.setAnimation(travelJournalAnimation);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            finish();
        }, SPLASH_SCREEN);
    }
}