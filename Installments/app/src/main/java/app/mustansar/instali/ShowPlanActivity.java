package app.mustansar.instali;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import app.mustansar.instali.models.Member;

public class ShowPlanActivity extends AppCompatActivity {

    private TextView PlanNameTV,TotalInstallmentsTV,TotalAmountTV,StartDateTV,planMembers;
    int id;
    MemberDBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_plan);
        //   btnSave = (Button) findViewById(R.id.btnSave);
        getWindow().setBackgroundDrawableResource(R.drawable.mainbg);
        PlanNameTV = findViewById(R.id.PlanNameTV);
        TotalInstallmentsTV = findViewById(R.id.TotalInstallmentsTV);
        TotalAmountTV = findViewById(R.id.TotalAmountTV);
        StartDateTV = findViewById(R.id.StartDateTV);
        planMembers=findViewById(R.id.planMembers);
        db = new MemberDBHelper(this);
        Member member = (Member) getIntent().getExtras().getSerializable("MEMBER");
        id=member.getId();
        PlanNameTV.setText(member.getPlanname());
        TotalInstallmentsTV.setText(String.valueOf(member.getTotalinstallments()));
        TotalAmountTV.setText(String.valueOf(member.getTotalamount()));
        StartDateTV.setText(member.getPlandate());
        planMembers.setText(member.getPlanMembers());

        //get the intent extra from the ListDataActivity
        //Intent receivedIntent = getIntent();

        //now get the itemID we passed as an extra
       // selectedID = receivedIntent.getIntExtra("id",-1); //NOTE: -1 is just the default value

        //now get the name we passed as an extra
       // selectedName = receivedIntent.getStringExtra("name");


        //set the text to show the current selected name
       // totalInstallmentsTV.setText(selectedInstallments);
       // editable_item.setText(selectedName);

       /* btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = editable_item.getText().toString();
                if(!item.equals("")){
                    db.updateName(item,selectedID,selectedName);
                }else{
                    toastMessage("You must enter a name");
                }
            }
        });*/

    /*  btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteName(selectedID,selectedName);
                PlanNameTV.setText("");

                toastMessage("removed from database");
                //startActivity( new Intent(ShowPlanActivity.this,ViewInstallmentsActivity.class));
            }
        });*/


    }
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }



}