package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME="inventory";
    public static final String  DB_VERSION="1";
    public static final String TABLE_NAME="product";
    public static final String COL_1="ID";
    public static final String COL_2="Name";
    public static final String COL_3="Price";
    public static final String COL_4="Quantity";

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, Integer.parseInt(DB_VERSION));
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    String productList="create table product (id integer primary key autoincrement," +
            "name varchar, price number, qty number);";
    db.execSQL(productList);
    }

    public boolean addItems (String name, String price, String qty){
        SQLiteDatabase database = getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("price",name);
        contentValues.put("qty",name);

        database.insert("product",null,contentValues);
        database.close();
        return true;

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String productList = "drop table if exists product";
        db.execSQL(productList);
        onCreate(db);
    }

    public boolean updateItems(String id, String name, String price, String qty){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,price);
        contentValues.put(COL_4,qty);
        database.update(TABLE_NAME,contentValues,"ID = ?", new String[] {id});
        database.close();
        return true;
    }



    public Integer deleteProduct (String id){
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete(TABLE_NAME,"ID = ?", new String[] {id});
    }

    public Cursor viewALlProduct(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor res = database.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }
}
