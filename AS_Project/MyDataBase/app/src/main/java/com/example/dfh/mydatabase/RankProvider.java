package com.example.dfh.mydatabase;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by dfh on 19-8-17.
 */

public class RankProvider extends ContentProvider {
    private MyDataBaseHelper mDataBaseHelper=null;
    private static final String AUTHORITY="com.example.dfh.mydatabase.rankprovider";
    private static final String PATH_MULTIPLE="rk1";
    private static final String PATH_SINGEL="rk1/#";
    private static final String CONTENT_URI_STRING="content://"+AUTHORITY+"/"+PATH_MULTIPLE;
    public static final Uri CONTENT_URI= Uri.parse(CONTENT_URI_STRING);
    private  static final UriMatcher uriMathcer;

    static {
        uriMathcer = new UriMatcher(UriMatcher.NO_MATCH);
        uriMathcer.addURI(AUTHORITY,PATH_MULTIPLE,1);
        uriMathcer.addURI(AUTHORITY,PATH_SINGEL,2);
    }

    @Override
    public boolean onCreate() {
        mDataBaseHelper=new MyDataBaseHelper(getContext(),"mydb",null,1);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,  String sortOrder) {
        SQLiteDatabase db=mDataBaseHelper.getReadableDatabase();
        Cursor cursor=null;
        switch(uriMathcer.match(uri)){
            case 1:
                cursor=db.query("tb_rank",//表名
                        projection,//列的数组,null代表所有列
                        selection,//where条件
                        selectionArgs,//where条件的参数值的数组
                        null,//分组
                        null,//having
                        sortOrder);//排序规则
                break;
           /* case 2:
                long id= ContentUris.parseId(uri);
                if(selection==null){
                    selection="_id="+id;
                }
                else{
                    seleinsertction="_id="+id+" and ("+selection+")";
                }
                cursor=db.query("Rank",
                        projection,//列的数组,null代表所有列
                        selection,//where条件
                        selectionArgs,//where条件的参数值的数组
                        null,//分组
                        null,//having
                        sortOrder);
                break;*/
            default:
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMathcer.match(uri)){
            case 1://操作的数据类型为集合类型，返回值以vnd.android.cursor.dir/开头
                //得到person表的所有数据
                return "vnd.android.cursor.dir/rankinfo1";
            case 2://操作的数据类型为非集合类型，返回值以vnd.android.cursor.item/开头
                //得到person表下面，每一条数据
                return "vnd.android.cursor.item/rankinfo1";
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db=mDataBaseHelper.getWritableDatabase();
        db.insert("tb_rank",null,contentValues);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
