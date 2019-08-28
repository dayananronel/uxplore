package com.example.uxplore.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.uxplore.model.Favorites;

import java.util.ArrayList;
import java.util.HashMap;

public class FavoritesHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "uxplore_favorites";
    private static final String TABEL_USER_FAVORITES = "UserFavorites";
    private static final String KEY_ID = "id";
    private static final String KEY_PLACENAME = "placename";
    private static final String KEY_ImageURL = "imageurl";

    public FavoritesHandler(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABEL_USER_FAVORITES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_PLACENAME + " TEXT,"
                + KEY_ImageURL + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABEL_USER_FAVORITES);
        // Create tables again
        onCreate(db);
    }
    // **** CRUD (Create, Read, Update, Delete) Operations ***** //

    // Adding new User Details
   public void insertUserDetails(String placename, String imageurl){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_PLACENAME, placename);
        cValues.put(KEY_ImageURL, imageurl);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABEL_USER_FAVORITES,null, cValues);
        db.close();
    }
    // Get User Details
    public ArrayList<HashMap<String, String>> GetPackages(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABEL_USER_FAVORITES;
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("placename",cursor.getString(cursor.getColumnIndex(KEY_PLACENAME)));
            user.put("imageurl",cursor.getString(cursor.getColumnIndex(KEY_ImageURL)));
            userList.add(user);
        }
        return  userList;
    }
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABEL_USER_FAVORITES,null);
        return res;
    }

    public Cursor getData(String placename) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABEL_USER_FAVORITES +" WHERE "+KEY_PLACENAME+" = ?",new String[]{placename});
        return res;
    }

    public Cursor getAllFavourites() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT placename,imageurl FROM " + TABEL_USER_FAVORITES, null);
    }
    
    // Delete User Details
public void deleteAll()
{
    SQLiteDatabase db = this.getWritableDatabase();
    db.delete(TABEL_USER_FAVORITES,null,null);
    db.execSQL("delete from "+ TABEL_USER_FAVORITES);
    db.close();
}
    public void deleteSingle(String placename){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABEL_USER_FAVORITES, KEY_PLACENAME + "=?", new String[]{placename});
        //KEY_NAME is a column name
    }
    // Update User Details
    public int UpdateUserDetails(String packagename,String placename){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(KEY_PLACENAME ,placename);
        int count = db.update(TABEL_USER_FAVORITES, cVals, KEY_PLACENAME +" = ?",new String[]{String.valueOf(packagename)});
        return  count;
    }

}