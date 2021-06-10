package app.mustansar.instali;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.FrameLayout;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import app.mustansar.instali.models.Member;

public class ViewInstallmentsActivity extends AppCompatActivity {

    ListView MemberLV;
    List<Member> list;
    ArrayAdapter<Member>arrayAdapter;
    MemberDBHelper db;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onStart() {
        super.onStart();
        list=db.getAllMembers();
        arrayAdapter=new ArrayAdapter<>(ViewInstallmentsActivity.this,android.R.layout.simple_list_item_1,list);
        MemberLV.setAdapter(arrayAdapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_installments);
        getWindow().setBackgroundDrawableResource(R.drawable.mainbg);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2166469463160806/3506144981");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });

        TextView textView=findViewById(R.id.date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm aa");
        String currentDateandTime = sdf.format(new Date());
        textView.setText(currentDateandTime);
        MemberLV =  findViewById(R.id.listView);
        db=new MemberDBHelper(ViewInstallmentsActivity.this);

        MemberLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Member member=list.get(position);
                Intent intent= new Intent(ViewInstallmentsActivity.this,ShowPlanActivity.class);
                intent.putExtra("MEMBER",member);
                startActivity(intent);
            }
        });
        MemberLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(ViewInstallmentsActivity.this);
                builder.setIcon(R.drawable.ic_baseline_delete_24);
                builder.setMessage("Are You Sure To Delete This Plan?");
                builder.setNegativeButton("No",null);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Member member= list.get(position);
                        int sid =member.getId();
                        int result=db.deleteMember(sid);

                        if (result > 0)
                        {
                            Toast.makeText(ViewInstallmentsActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                            list.remove(member);
                            arrayAdapter.notifyDataSetChanged();
                        }
                        else
                        {
                            Toast.makeText(ViewInstallmentsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

                builder.show();
                return true;
            }
        });

    }
    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }


}