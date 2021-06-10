package app.mustansar.instali;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class AddContactsActivity extends AppCompatActivity {
  // MemberDBHelper memberDBHelper;
    Button SaveBT;
    EditText NameET,EmailET,PhoneET;
    private AdView ContactsAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);
        getWindow().setBackgroundDrawableResource(R.drawable.mainbg);
        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        FrameLayout adContainerView = findViewById(R.id.Contacts_ad_view_container);
        // Step 1 - Create an AdView and set the ad unit ID on it.
        ContactsAdView = new AdView(this);
        ContactsAdView.setAdUnitId("ca-app-pub-2166469463160806/7888899664");
        adContainerView.addView(ContactsAdView);
        loadBanner();
        SaveBT = findViewById(R.id.SaveBT);
        NameET = findViewById(R.id.NameET);
        EmailET = findViewById(R.id.EmailET);
        PhoneET = findViewById(R.id.PhoneET);

        SaveBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = NameET.getText().toString();
                if(TextUtils.isEmpty(name)){
                    NameET.setError("Please Enter Your Name");
                    return;
                }
                String email = EmailET.getText().toString();
                if((TextUtils.isEmpty(email)) || !EmailET.getText().toString().contains("@"))
                {

                    EmailET.setError("Please Enter A Valid Email");
                    return;
                }

               // PhoneET.getText().toString();
                int phone= !PhoneET.getText().toString().equals("") ?
                        Integer.parseInt(PhoneET.getText().toString()) : -1;

                    if(phone<=0){
                        PhoneET.setError("Please Enter Your Phone No");
                        return;
                    }

               MemberDBHelper memberDBHelper= new MemberDBHelper(AddContactsActivity.this);
              long result = memberDBHelper.addMember(name, email, phone);
              if(result !=-1){
                Toast.makeText(AddContactsActivity.this, "New Member Saved", Toast.LENGTH_SHORT).show();
            }
              else
              {
                  Toast.makeText(AddContactsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
              }
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
        ContactsAdView.setAdSize(adSize);

        // Step 5 - Start loading the ad in the background.
        ContactsAdView.loadAd(adRequest);
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