package com.example.dfh.mydatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView mListView;
    ArrayList<Rank>mRanks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView=(ListView) findViewById(R.id.list_view);
        mRanks=new ArrayList<Rank>();
        MyDataBaseHelper myDataBaseHelper=new MyDataBaseHelper(this,"mydb",null,1);
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select  _id ,name,score from tb_rank",null);
        SimpleCursorAdapter adapter =new SimpleCursorAdapter(this,R.layout.rank_item,cursor,
                new  String[]{"_id","name","score"},
                new int[]{R.id.numbertxv,R.id.nametxv,R.id.scoretxv});
                mListView.setAdapter(adapter);
    }
}
