package psyblaze.mapme;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeScreen = new Intent(Splash.this, LoginActivity.class);
                Splash.this.startActivity(homeScreen);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGH);
    }
}
