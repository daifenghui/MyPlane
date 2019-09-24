package com.example.dfh.planethunder.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dfh on 19-9-5.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    static final String CREATE_TABLE1="create table tb_object(_id integer primary key autoincrement ,location text,object_type integer,blood integer,direction integer );";
    static final String CREATE_TABLE2="create table tb_myPlane(_id integer primary key autoincrement,location text,bulletType integer,isDamaged integer);";
    private Context mContext;
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE1);
        sqLiteDatabase.execSQL(CREATE_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
