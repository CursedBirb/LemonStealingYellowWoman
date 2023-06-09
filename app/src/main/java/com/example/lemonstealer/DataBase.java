package com.example.lemonstealer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "lemon_database";
    private static final String TABLE_ARTICLES = "cellected_lemons";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_NR_LIST = "nr_list";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + DataBase.TABLE_ARTICLES + "("+
                DataBase.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DataBase.KEY_NAME + " TEXT,"+
                DataBase.KEY_NR_LIST+" INTEGER"+ ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);
        onCreate(db);
    }

    public void addLemons(LemonsBase lemon, int nrListy) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, lemon.getLemons());
        values.put(KEY_NR_LIST, nrListy);
        db.insertOrThrow(TABLE_ARTICLES, null, values);
        db.close();
    }

    public void deleteLemon(LemonsBase lemon, int listNumber) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ARTICLES, KEY_NAME + " = ? and " + KEY_NR_LIST + " =?", new String[]{String.valueOf(lemon.getLemons()), "" + listNumber});
        db.close();
    }

    public String getAllRecords(int listNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        String string="";
        Cursor cursor = db.query(TABLE_ARTICLES, null, KEY_NR_LIST + "=?",
                new String[] { String.valueOf(listNumber) }, null, null, null, null);
        cursor.moveToFirst();
        int nr=1;
        do{
            boolean check =false;
            if(cursor.getInt(2)>1)
                check=true;
            string+=nr+". "+cursor.getString(1)+" "+check +"\n";
            nr++;
        } while(cursor.moveToNext());
        return string;
    }
}
