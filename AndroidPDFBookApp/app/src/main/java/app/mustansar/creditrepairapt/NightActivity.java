package app.mustansar.creditrepairapt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class NightActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener, OnPageErrorListener {
        PDFView testpdf;
        Integer pageNumber= 0;
    private InterstitialAd InterstitialAd;

    @Override
    public void onBackPressed() {
        InterstitialAd = new InterstitialAd(this);
        InterstitialAd.setAdUnitId("ca-app-pub-8330184551845108/5265081617");
        InterstitialAd.loadAd(new AdRequest.Builder().build());
        InterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });
        super.onBackPressed();
        //startActivity(new Intent(NightActivity.this,MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        InterstitialAd = new InterstitialAd(this);
        InterstitialAd.setAdUnitId("ca-app-pub-8330184551845108/5265081617");
        InterstitialAd.loadAd(new AdRequest.Builder().build());
        InterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });
        testpdf = findViewById(R.id.nightViewer);
        testpdf.fromAsset("book.pdf")
                .defaultPage(0)
                .enableAnnotationRendering(true)
                .scrollHandle(new DefaultScrollHandle(NightActivity.this))
                .spacing(0)


                //  .swipeHorizontal(true)
                .pageSnap(true)
                //  .autoSpacing(true)
                //  .pageFling(true)

                .nightMode(true)
                .load();


    }
    private void showInterstitial() {
        if (InterstitialAd.isLoaded()) {
            InterstitialAd.show();
        }
    }
    @Override
    public void loadComplete(int nbPages) {

    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", testpdf, page + 1, pageCount));
    }

    @Override
    public void onPageError(int page, Throwable t) {
        Toast.makeText(this, "Cannot load page" + page , Toast.LENGTH_SHORT).show();
    }
}