package android.example.aad_team_38_animation_challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TextView textView = findViewById(R.id.splash_name);
        ImageView imageView = findViewById(R.id.splash_logo);

        Animation animateAppLogo = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        imageView.startAnimation(animateAppLogo);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        textView.startAnimation(animation);


        int SPLASH_TIME_OUT = 2200;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(homeIntent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
