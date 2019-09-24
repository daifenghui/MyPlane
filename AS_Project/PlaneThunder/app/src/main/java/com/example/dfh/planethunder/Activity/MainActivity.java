package com.example.dfh.planethunder.Activity;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;


import com.example.dfh.planethunder.Other.GameView;
import com.example.dfh.planethunder.Sound.GameSoundPool;
import com.example.dfh.planethunder.View.MainView;
import com.example.dfh.planethunder.View.EndView;
import com.example.dfh.planethunder.View.ReadyView;
import com.example.dfh.planethunder.constant.GameConstant;
import com.example.dfh.planethunder.util.ConstantUtil;
import com.example.dfh.planethunder.util.DbUtil;
import com.example.dfh.planethunder.util.languageUtil;

import static android.support.v7.view.menu.ListMenuPresenter.VIEWS_TAG;

public class MainActivity extends AppCompatActivity {
    private ReadyView readyView;
    private GameSoundPool sounds;
    private MainView mainView;
    private EndView endView;
    public static MainActivity instance = null;
    private static boolean isEnglish=true;
    private long startTime;
    private long endTime;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == ConstantUtil.TO_MAIN_VIEW) {
                toMainView();
            }
            else if (msg.what==01){
                if (msg.arg1==1)
                    isEnglish=false;
                else
                    isEnglish=true;
                languageUtil mLanguageUtil=new languageUtil();
                mLanguageUtil.set(isEnglish);
            }
            else if (msg.what == ConstantUtil.TO_SAVE_RECORD) {
                saveRecord(msg.arg1,isEnglish);
            } else if (msg.what == ConstantUtil.END_GAME) {
                endGame();
            }
            else if(msg.what==ConstantUtil.TO_Ready_VIEW){
                toReadyView();
            }
            else if(msg.what == ConstantUtil.TO_SCORE_VIEW){
                toRankView();
            }
            else if(msg.what == 1){
                finish();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        sounds = new GameSoundPool(this);
        sounds.initGameSound();
        readyView = new ReadyView(this, sounds);
        setContentView(readyView);
        instance=this;
    }
    /**
     * 进入游戏界面
     */
    public void toMainView() {

        if (mainView == null) {
            mainView = new MainView(this, sounds);
            Log.e("isEnglish2",isEnglish+"");
            setContentView(mainView);
            readyView = null;
            endView = null;
        }

    }
    /**
     * 进入结束分数统计界面
     */
    public void toEndView() {
        if (endView == null) {
            endView = new EndView(this, sounds);
        }
        setContentView(endView);
        mainView = null;
    }

    /**
     * 结束游戏
     */
    public void endGame() {
        if (readyView != null) {
            readyView.setThreadFlag(false);
        } else if (mainView != null) {
            mainView.setThreadFlag(false);
        } else if (endView != null) {
            endView.setThreadFlag(false);
        }
        this.finish();
    }

    private void toReadyView() {
        if(readyView==null) {
            readyView = new ReadyView(this, sounds);
        }
        setContentView(readyView);
        endView=null;
    }
    /*进入排行榜*/
    public void toRankView(){
        Intent intent = new Intent(MainActivity.this,RankActivity.class);
        Bundle bundle =new Bundle();
        bundle.putBoolean("languageFlag",isEnglish);
        Log.e("isEnglish",isEnglish+"");
        intent.putExtras(bundle);
        startActivityForResult(intent,2);
    }
    public void saveRecord(int arg,boolean isEnglish){
        Intent intent = new Intent(MainActivity.this,RecordActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("score",arg);
        bundle.putBoolean("isEnglish",isEnglish);
        intent.putExtras(bundle);
        startActivityForResult(intent,1);
    }
    protected void onActivityResult(int requestCode ,int resultCode,Intent intent ){
        if(resultCode==2&&requestCode==1){
            toEndView();
        }
        if(resultCode==3&&requestCode==2){
            isEnglish=true;
        }
        if(resultCode==4&&requestCode==2){
            isEnglish=false;
        }
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void onRestart() {
        super.onRestart();
         endTime=System.currentTimeMillis();
         if(endTime-startTime>5000){
             recreate();
         }
    }
    public void onPause() {
        super.onPause();
        startTime= System.currentTimeMillis();

    }
    /**
     * Back键退出
     */
    private long firstTime = 0;

    @Override
    public void onBackPressed() {
        onPause();
        mainView.isPlay=false;
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("确认退出游戏?");
        builder.setPositiveButton("是",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mainView.gameState="PAUSE";
                mainView.isSaved=true;
                saveGame(mainView);
                finish();
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onResume();

            }
        });
        builder.setCancelable(false);
        builder.show();
    }
    /**
     * 用于保存进度
     */
        public void saveGame(MainView mainView){
            SharedPreferences sharedPreferences=getSharedPreferences("plane", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putInt("Score",mainView.sumScore);
            editor.putInt("MissileAmount",mainView.missileCount);
            editor.putInt("LifeAmount",mainView.mLifeAmount);
            editor.putLong("CurrentSecond",mainView.currentSecond);
            editor.putBoolean("isSaved",mainView.isSaved);
            editor.putFloat("bg_y",mainView.bg_y);
            editor.putFloat("bg_y2",mainView.bg_y2);
            editor.commit();
            Toast.makeText(this, "已存档"+sharedPreferences.getBoolean("isSaved",false), Toast.LENGTH_SHORT).show();
            GameView gameView= mainView.getGameView();//存储飞机物品
            DbUtil.insertGame(gameView,MainActivity.this);
        }
}

