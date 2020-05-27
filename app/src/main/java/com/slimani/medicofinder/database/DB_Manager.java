package com.slimani.medicofinder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DB_Manager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MedicoFinder";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "SymptomeService";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME1 = "symptome";
    private static final String COLUMN_NAME2 = "service";


    public DB_Manager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (\n" +
                " " + COLUMN_ID + " INTEGER NOT NULL CONSTRAINT mot_pk PRIMARY KEY AUTOINCREMENT,\n" +
                " " + COLUMN_NAME1 + " varchar(200) NOT NULL,\n" +
                " " + COLUMN_NAME2 + " varchar(200) NOT NULL\n" + ");";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public boolean addSymptome(String symptome, String maladie){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME1, symptome);
        contentValues.put(COLUMN_NAME2, maladie);

        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABLE_NAME, null, contentValues) != -1;
    }

    public Cursor getSymptome(String symptome){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM SymptomeService WHERE symptome = '"+symptome+"' ";
        return db.rawQuery(sql, null);
    }

    public Cursor getAll(){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
