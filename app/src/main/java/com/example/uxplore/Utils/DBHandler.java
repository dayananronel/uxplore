package com.example.uxplore.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "uxplore";
    private static final String TABEL_USER_PACKAGES = "UserPackages";
    private static final String KEY_ID = "id";
    private static final String KEY_PACKAGENAME = "packagename";
    private static final String KEY_PlaceName = "placename";
    private static final String KEY_PACKAGEFEE = "packagefee";
    private static final String KEY_PACKAGEID = "packageid";



    public DBHandler(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABEL_USER_PACKAGES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_PACKAGENAME + " TEXT,"
                + KEY_PACKAGEFEE + " TEXT,"
                + KEY_PlaceName + " TEXT,"
                + KEY_PACKAGEID + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABEL_USER_PACKAGES);
        // Create tables again
        onCreate(db);
    }
    // **** CRUD (Create, Read, Update, Delete) Operations ***** //

    // Adding new User Details
   public void insertUserDetails(String pakcagename, String placenaame,String packagefee,String packageid){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_PACKAGENAME, pakcagename);
        cValues.put(KEY_PlaceName, placenaame);
        cValues.put(KEY_PACKAGEFEE, packagefee);
        cValues.put(KEY_PACKAGEID,packageid);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABEL_USER_PACKAGES,null, cValues);
        db.close();
    }
    // Get User Details
    public ArrayList<HashMap<String, String>> GetPackages(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABEL_USER_PACKAGES;
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("packagename",cursor.getString(cursor.getColumnIndex(KEY_PACKAGENAME)));
            user.put("placename",cursor.getString(cursor.getColumnIndex(KEY_PlaceName)));
            user.put("packageid",cursor.getString(cursor.getColumnIndex(KEY_PACKAGEID)));
            userList.add(user);
        }
        return  userList;
    }
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABEL_USER_PACKAGES,null);
        return res;
    }
//    // Get User Details based on userid
//    public ArrayList<HashMap<String, String>> GetUserByUserId(int userid){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
//        String query = "SELECT name, location, designation FROM "+ TABEL_USER_PACKAGES;
//        Cursor cursor = db.query(TABEL_USER_PACKAGES, new String[]{KEY_PACKAGENAME, KEY_PlaceName, KEY_DESG}, KEY_ID+ "=?",new String[]{String.valueOf(userid)},null, null, null, null);
//        if (cursor.moveToNext()){
//            HashMap<String,String> user = new HashMap<>();
//            user.put("packagename",cursor.getString(cursor.getColumnIndex(KEY_PACKAGENAME)));
//            user.put("placename",cursor.getString(cursor.getColumnIndex(KEY_PlaceName)));
//            userList.add(user);
//        }
//        return  userList;
//    }
    // Delete User Details
public void deleteAll()
{
    SQLiteDatabase db = this.getWritableDatabase();
    db.delete(TABEL_USER_PACKAGES,null,null);
    db.execSQL("delete from "+ TABEL_USER_PACKAGES);
    db.close();
}
    // Update User Details
    public int UpdateUserDetails(String packagename,String placename){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(KEY_PlaceName, placename);
        int count = db.update(TABEL_USER_PACKAGES, cVals, KEY_PACKAGENAME+" = ?",new String[]{String.valueOf(packagename)});
        return  count;
    }

}