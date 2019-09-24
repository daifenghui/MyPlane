package com.example.dfh.planethunder.Activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dfh.planethunder.Other.Rank;
import com.example.dfh.planethunder.Other.RankAdapter;
import com.example.dfh.planethunder.R;

import java.util.ArrayList;
import java.util.Locale;

public class RankActivity extends AppCompatActivity {
    private ListView mListView;
    private boolean isEnglish;
    ArrayList<Rank> ranklist=new ArrayList<Rank>();
    int ranknum=1;
    Uri uri = Uri.parse("content://com.example.dfh.mydatabase.rankprovider/rk1");
    private int RESULTCODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        Bundle bundle=intent.getExtras();
       if(bundle==null) {
           isEnglish =true;
       }
       else
       {
           isEnglish=bundle.getBoolean("languageFlag");
       }
       /* Log.e("isEnglish111",bundle.getBoolean("languageFlag")+"");*/
        Configuration configuration =this.getResources().getConfiguration();
        DisplayMetrics displayMetrics =this.getResources().getDisplayMetrics();
        if (isEnglish) {
            //设置英文
            RESULTCODE=3;
            configuration.locale = Locale.ENGLISH;
        } else {
            //设置中文
            RESULTCODE=4;
            configuration.locale = Locale.SIMPLIFIED_CHINESE;
        }
        //更新配置
        this.getResources().updateConfiguration(configuration, displayMetrics);
        setContentView(R.layout.activity_rank);


        ActionBar actionBar =getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        String[] projection={"_id","name","score"};
        Cursor cursor = getContentResolver().query(uri,projection,null,null,"score DESC");
        while(cursor.moveToNext()&&ranknum<6){
            String Pname=cursor.getString(cursor.getColumnIndex("name"));
            int Pscore=cursor.getInt(cursor.getColumnIndex("score"));
            Rank rank=new Rank();
            rank.setRanknum(ranknum);
            rank.setName(Pname);
            rank.setScore(Pscore);
            ranklist.add(rank);
            ranknum++;
        }
        mListView=findViewById(R.id.list_view);
        RankAdapter adapter=new RankAdapter(RankActivity.this,R.layout.toprank_item,ranklist);
        mListView.setAdapter(adapter);
    }
    public void sendResult(){     //发送结果编码,保持语言一致
        Intent intent = new Intent();
        this.setResult(RESULTCODE,intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:      //返回主菜单
                this.finish();
                sendResult();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


