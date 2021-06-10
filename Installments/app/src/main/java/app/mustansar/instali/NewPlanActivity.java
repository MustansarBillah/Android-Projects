package app.mustansar.instali;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import app.mustansar.instali.models.Member;

public class NewPlanActivity extends AppCompatActivity {
    EditText PlanNameET,TotalInstallmentsET,TotalAmountET,StartDate;
    TextView AddNewMemberTV;
    Calendar myCalendar;
    Spinner spinner;
    Button SubmitBT;

    ArrayList<String> mylist = new ArrayList<>();
    List<String> users=new ArrayList<>();
    ArrayAdapter<String> adapter;
    MemberDBHelper db;

    @Override
    protected void onStart() {
        prepareData();
        super.onStart();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_plan);
        getWindow().setBackgroundDrawableResource(R.drawable.mainbg);
        db=new MemberDBHelper(NewPlanActivity.this);
        users=db.getAllUsers();
        PlanNameET=findViewById(R.id.PlanNameET);
        TotalInstallmentsET=findViewById(R.id.TotalInstallmentsET);
        TotalAmountET=findViewById(R.id.TotalAmountET);
        StartDate= findViewById(R.id.StartDate);
        spinner=findViewById(R.id.spinner);
        AddNewMemberTV=findViewById(R.id.AddNewMemberTV);
        AddNewMemberTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewPlanActivity.this,AddContactsActivity.class));
            }
        });
        SubmitBT=findViewById(R.id.SubmitBT);
        myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(NewPlanActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        SubmitBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String plan_name = PlanNameET.getText().toString();
                if(TextUtils.isEmpty(plan_name)){
                    PlanNameET.setError("Please Enter Plan Name");
                    return;
                }
                TotalInstallmentsET.getText().toString();
                int total_installments= !TotalInstallmentsET.getText().toString().equals("") ?
                        Integer.parseInt(TotalInstallmentsET.getText().toString()) : -1;
                if(total_installments <= 0)
                {
                    TotalInstallmentsET.setError("Please Enter No of Installments");
                    return;
                }
               // Integer total_amount = Integer.parseInt(TotalAmountET.getText().toString());
                TotalAmountET.getText().toString();
                int total_amount= !TotalAmountET.getText().toString().equals("") ?
                        Integer.parseInt(TotalAmountET.getText().toString()) : -1;
                if(total_amount <= 0)
                {
                    TotalAmountET.setError("Please Enter Total Amount");
                    return;
                }
                String plandate=StartDate.getText().toString();
                if(TextUtils.isEmpty(plandate)){
                    StartDate.setError("Please Enter Date");
                    return;
                }
                String member=mylist.toString();
                //String member=spinner.getSelectedItem().toString();
                MemberDBHelper dbHelper= new MemberDBHelper(NewPlanActivity.this);
                Member member1=new Member(plan_name, total_installments, total_amount, plandate, member);

                long result = dbHelper.addPlan(member1);
                if(result !=-1){
                    Toast.makeText(NewPlanActivity.this, "New Plan Saved", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(NewPlanActivity.this,MainActivity.class));
                }
                else
                {
                    Toast.makeText(NewPlanActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
                SubmitBT.setClickable(false);

            }
        });



        prepareData();
        //handle click of spinner item

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // clicked item will be shown as spinner
                if(position>=1) {
                    mylist.add((String) parent.getItemAtPosition(position));
                    Toast.makeText(getApplicationContext(), "Member Added " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    public void prepareData()
    {
        users=db.getAllUsers();
        //adapter for spinner
        adapter= new ArrayAdapter<>(NewPlanActivity.this, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, users);
        //attach adapter to spinner
        spinner.setAdapter(adapter);

    }
    public void updateLabel() {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        StartDate.setText(sdf.format(myCalendar.getTime()));
    }
}