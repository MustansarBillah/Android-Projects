package app.mustansar.creditrepairapt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    SwitchCompat switchCompat;
    private AdView MainAdView;
    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Share This Book");
        builder.setIcon(getResources().getDrawable(R.drawable.ic_round_menu_book_24, getTheme()));
        builder.setMessage("Spread Knowledge.!");
        builder.setPositiveButton("Share", (dialog, id) -> {

            Intent intentInvite = new Intent(Intent.ACTION_SEND);
            final String appPackageName=getApplicationContext().getPackageName();
            String strAppLink;
            try {
                strAppLink="https://play.google.com/store/apps/details?id="+appPackageName;
            }
            catch (android.content.ActivityNotFoundException anfe)
            {
                strAppLink="https://play.google.com/store/apps/details?id="+appPackageName;
            }
            intentInvite.setType("text/Link");
            String body = "Download This App And Read Your Favourite Books."+"\n"+""+strAppLink;
            String subject = "Book";
            intentInvite.putExtra(Intent.EXTRA_SUBJECT, subject);
            intentInvite.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(intentInvite, "Share using"));
        });
        builder.setNegativeButton("Exit", (dialog, id) -> finish());
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, initializationStatus -> {
        });
        FrameLayout adContainerView = findViewById(R.id.Main_ad_view_container);
        // Step 1 - Create an AdView and set the ad unit ID on it.
        MainAdView = new AdView(this);
        MainAdView.setAdUnitId("ca-app-pub-8330184551845108/5106472979");
        adContainerView.addView(MainAdView);
        loadBanner();

        imageView = findViewById(R.id.imageView);
        switchCompat = findViewById(R.id.switch1);
        imageView.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), pdfview.class);
            startActivity(i);
        });
        switchCompat.setTextOff("Off");
        switchCompat.setTextOn("ON");

        switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                Toast.makeText(MainActivity.this, "Night Mode On", Toast.LENGTH_SHORT).show();
                imageView.setOnClickListener(v -> {
                    Intent i = new Intent(getApplicationContext(), NightActivity.class);
                    startActivity(i);
                }); }

            else {
                Toast.makeText(MainActivity.this, "Night Mode Off", Toast.LENGTH_SHORT).show();
                imageView.setOnClickListener(v -> {
                    Intent i = new Intent(getApplicationContext(), pdfview.class);
                    startActivity(i);
                });
            }
        });

    }

    private void loadBanner() {
        // Create an ad request. Check your logcat output for the hashed device ID
        // to get test ads on a physical device, e.g.,
        // "Use AdRequest.Builder.addTestDevice("ABCDE0123") to get test ads on this
        // device."
        AdRequest adRequest =
                new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .build();

        AdSize adSize = getAdSize();
        // Step 4 - Set the adaptive ad size on the ad view.
        MainAdView.setAdSize(adSize);

        // Step 5 - Start loading the ad in the background.
        MainAdView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }
}