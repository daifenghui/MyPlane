package com.example.dfh.planethunder.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.icu.text.AlphabeticIndex;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dfh.planethunder.Other.Gamer;
import com.example.dfh.planethunder.R;
import com.example.dfh.planethunder.Sound.GameSoundPool;
import com.example.dfh.planethunder.View.EndView;
import com.example.dfh.planethunder.View.MainView;
import com.example.dfh.planethunder.util.ConstantUtil;

import java.util.Locale;

public class RecordActivity extends Activity {
    private EndView endView;
    private GameSoundPool sounds;
    // 确定按钮
    private Button confirm;
    // 取消按钮
    private Button cancel;
    // 姓名输入框
    private EditText name;
    // 显示分数
    private TextView finalScore;
    private Intent intent;
    // 玩家的分数
    private int nowScore;
    private MainView mainView;
    private boolean isEnglish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        Bundle bundle=intent.getExtras();
        nowScore = bundle.getInt("score");
        isEnglish=bundle.getBoolean("isEnglish");
        Log.e("isEnglish1",isEnglish+"");
        Configuration configuration =this.getResources().getConfiguration();
        DisplayMetrics displayMetrics =this.getResources().getDisplayMetrics();
        if (isEnglish) {
            //设置英文
            configuration.locale = Locale.ENGLISH;
        } else {
            //设置中文
            configuration.locale = Locale.SIMPLIFIED_CHINESE;
        }
        //更新配置
        this.getResources().updateConfiguration(configuration, displayMetrics);
        setContentView(R.layout.activity_record);
        init();

        // 显示分数
        finalScore.setText(nowScore+"");
        // 设置点击其他位置不关闭窗口 ，最低版本API11
        this.setFinishOnTouchOutside(false);

    }

    @SuppressLint("WrongViewCast")
    private void init() {
        name=(EditText) findViewById(R.id.nameEdt);
        finalScore=(TextView)findViewById(R.id.sctxv);
        confirm=(Button)findViewById(R.id.savebtn);
        cancel=(Button)findViewById(R.id.nosavebtn);
        /**
         * 给按钮设置点击事件
         */
        confirm.setOnClickListener(new MyOnClick());
        cancel.setOnClickListener(new MyOnClick());
    }
    public void sendResult(){
        Intent intent = new Intent();
        this.setResult(2,intent);
    }


    private class MyOnClick implements View.OnClickListener {
        ContentResolver resolver =  getContentResolver();
        ContentValues values=new ContentValues();
        Uri uri = Uri.parse("content://com.example.dfh.mydatabase.rankprovider/rk1");

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.savebtn:
                    // 判断输入内容是否为空
                    if(!TextUtils.isEmpty(name.getText().toString())){
                        Gamer gamer = new Gamer(name.getText().toString(), nowScore);
                        // 添加数据到数据库

                        values.put("name",gamer.getName());
                        values.put("score",gamer.getScore());
                        resolver.insert(uri,values);
                        Toast.makeText(RecordActivity.this, R.string.savesuccess,Toast.LENGTH_SHORT).show();
                        // 关闭窗口
                        sendResult();
                        finish();
                    }else{
                        Toast.makeText(RecordActivity.this, R.string.name_is_empty, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.nosavebtn:
                    sendResult();
                    finish();

                    break;
                default:
                    break;
            }

        }
    }
}
