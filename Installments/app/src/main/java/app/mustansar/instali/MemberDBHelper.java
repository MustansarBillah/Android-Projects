package app.mustansar.instali;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import app.mustansar.instali.models.Member;

public class MemberDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="instali.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME = "members";
    private static final String TABLE_INSTALLMENTS = "installment";

    public static final String COLUMN_ID="_id";
    public static final String COLUMN_NAME="name";
    public static final String COLUMN_EMAIL="email";
    public static final String COLUMN_Phone="phone";

    public static final String COLUMN_PLAN_ID="pid";
    public static final String COLUMN_PLAN_NAME="planname";
    public static final String COLUMN_TOTAL_INSTALLMENTS="totalinstallments";
    public static final String COLUMN_TOTAL_AMOUNT="totalamount";
    public static final String COLUMN_DATE="plandate";
    public static final String COLUMN_MEMBER="member";


    public MemberDBHelper(@Nullable Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    String createTable = "CREATE TABLE " + TABLE_NAME + " ( "
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_EMAIL + " TEXT, "
            + COLUMN_Phone + " INTEGER );";

    String installmentTable= " CREATE TABLE " + TABLE_INSTALLMENTS + " ( "
            + COLUMN_PLAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PLAN_NAME + " Text, "
            + COLUMN_TOTAL_INSTALLMENTS + " INTEGER, "
            + COLUMN_TOTAL_AMOUNT + " INTEGER, "
            + COLUMN_DATE + " Text, "
            + COLUMN_MEMBER + " Text )";



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
        db.execSQL(installmentTable);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTALLMENTS);
        onCreate(db);
    }



    public long addMember(String name, String email, Integer phone) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(COLUMN_NAME,name);
        values.put(COLUMN_EMAIL,email);
        values.put(COLUMN_Phone,phone);

        return db.insert(TABLE_NAME,null,values);
    }
    public long addPlan(Member member1) {
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PLAN_NAME,member1.getPlanname());
        values.put(COLUMN_TOTAL_INSTALLMENTS,member1.getTotalinstallments());
        values.put(COLUMN_TOTAL_AMOUNT,member1.getTotalamount());
        values.put(COLUMN_DATE,member1.getPlandate());
        values.put(COLUMN_MEMBER,member1.getPlanMembers());
        return db.insert(TABLE_INSTALLMENTS,null,values);
    }
    //getting all users from database
    public List<String> getAllUsers()
    {
        List<String> userlist=new ArrayList<>();
        //get readable database
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT name FROM members",null);
        if(cursor.moveToFirst())
        {
            userlist.add("Select Two Or More Members ");
            do {

                userlist.add(cursor.getString(0));
               }
            while (cursor.moveToNext());
        }
        //close the cursor
        cursor.close();
        //close the database
        db.close();
        return userlist;
    }


    public List<Member> getAllMembers() {
        List<Member> memberList= new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from "+ TABLE_INSTALLMENTS,null);
        if(cursor.moveToFirst())
        {
            do{
                int id=cursor.getInt(0);
                String planname=cursor.getString(1);
                int totalinstallments=cursor.getInt(2);
                int totalamount=cursor.getInt(3);
                String plandate=cursor.getString(4);
                String planmembers=cursor.getString(5);
                Member member =new Member(id,planname,totalinstallments,totalamount,plandate,planmembers);
                memberList.add(member);
            }
            while (cursor.moveToNext());
        }
        return memberList;
    }


    public int deleteMember(int sid) {
        SQLiteDatabase db = getWritableDatabase();

        return db.delete(TABLE_INSTALLMENTS,COLUMN_PLAN_ID + "=?",new String[]{String.valueOf(sid)}) ;
    }


}
