package com.example.dfh.mydatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.service.notification.NotificationListenerService;
import android.widget.Toast;

/**
 * Created by dfh on 19-8-14.
 */

public class MyDataBaseHelper extends SQLiteOpenHelper {
    public static  final String CREATE_RANK1="create table tb_rank("
            +"_id integer primary key autoincrement,"
            +"name text,"
            +"score integer)";
    private Context mContext;
    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RANK1);
        db.execSQL("insert into tb_rank values(1,'Shy',1111);");
        db.execSQL("insert into tb_rank values(2,'Uzi',2222);");
        db.execSQL("insert into tb_rank values(3,'ClearLove',3333);");
        db.execSQL("insert into tb_rank values(4,'Shy',12);");
        db.execSQL("insert into tb_rank values(5,'Shy',123);");
        db.execSQL("insert into tb_rank values(6,'Shy',1234);");
        Toast.makeText(mContext,"Create succeeded",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
