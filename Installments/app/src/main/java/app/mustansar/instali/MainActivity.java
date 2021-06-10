package app.mustansar.instali;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private AdView MainAdView;
    CardView StartNewInstallment,ViewRunningInstallments,AddContacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawableResource(R.drawable.mainbg);
        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        FrameLayout adContainerView = findViewById(R.id.Main_ad_view_container);
        // Step 1 - Create an AdView and set the ad unit ID on it.
        MainAdView = new AdView(this);
        MainAdView.setAdUnitId("ca-app-pub-2166469463160806/7138090628 ");
        adContainerView.addView(MainAdView);
        loadBanner();
        StartNewInstallment=findViewById(R.id.StartNewInstallment);
        ViewRunningInstallments=findViewById(R.id.ViewRunningInstallments);
        AddContacts=findViewById(R.id.AddContacts);
        TextView textView=findViewById(R.id.date);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm aa");
        String currentDateandTime = sdf.format(new Date());
        textView.setText(currentDateandTime);

        StartNewInstallment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,NewPlanActivity.class));
            }
        });
        ViewRunningInstallments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ViewInstallmentsActivity.class));
            }
        });
        AddContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddContactsActivity.class));
            }
        });


    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Are You Want Exit or Share? ");
        builder.setPositiveButton("Share", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
                String body = "Download This App To Manage Your Installments Digitally."+"\n"+""+strAppLink;
                String subject = "Instali";
                intentInvite.putExtra(Intent.EXTRA_SUBJECT, subject);
                intentInvite.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(intentInvite, "Share using"));
            }
        });
        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
    builder.show();
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