package app.mustansar.instali;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setBackgroundDrawableResource(R.drawable.splashbg);
        final Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } catch (InterruptedException e) {

                    Toast.makeText(SplashActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
        thread.start();
    }
}