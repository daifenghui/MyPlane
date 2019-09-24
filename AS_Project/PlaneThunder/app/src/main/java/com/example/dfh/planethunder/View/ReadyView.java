package com.example.dfh.planethunder.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.example.dfh.planethunder.Activity.MainActivity;
import com.example.dfh.planethunder.R;
import com.example.dfh.planethunder.Sound.GameSoundPool;
import com.example.dfh.planethunder.util.ConstantUtil;
import com.example.dfh.planethunder.util.DensityUtil;
import com.example.dfh.planethunder.util.languageUtil;

/**
 * Created by dfh on 19-8-12.
 */

public class ReadyView extends BaseView {
    private Bitmap background =BitmapFactory.decodeResource(getResources(), R.drawable.mbg_04);                // 背景图
    private Rect rect;
    private Bitmap text;
    private Bitmap button;
    private Bitmap button2;
    private float text_x;
    private float text_y;
    private float button_x;
    private float button_y;
    private float button_y2;
    private float button_y3;
    private float button_y4;
    private float strhei;
    private String startGame = getContext().getString(R.string.startGame);
    private String rank = getContext().getString(R.string.rank_name);
    private String exitGame = getContext().getString(R.string.exitGame);
    private String changeLanguage = getContext().getString(R.string.changeLanguage);
    private boolean isBtChange;
    private boolean isBtChange2;
    private boolean isBtChange3;
    private boolean isBtChange4;
    Message message = new Message();

    public ReadyView(Context context, GameSoundPool sounds){
        super(context, sounds);
        thread=new Thread(this);
        paint.setTextSize(getResources().getDimension(R.dimen.t1));
        rect = new Rect();
    }
    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        super.surfaceChanged(arg0, arg1, arg2, arg3);
    }
    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        super.surfaceCreated(arg0);
        initBitmap();
        if (thread.isAlive()) {
            thread.start();
        } else {
            thread = new Thread(this);
            thread.start();
        }
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        super.surfaceDestroyed(arg0);
        release();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN
                && event.getPointerCount() == 1) {
            float x = event.getX();
            float y = event.getY();
            if (x > button_x && x < button_x + button.getWidth()
                    && y > button_y && y < button_y + button.getHeight()) {
                sounds.playSound(7, 0);
                isBtChange = true;
                drawSelf();
                mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_MAIN_VIEW);
            } else if (x > button_x && x < button_x + button.getWidth()
                    && y > button_y2 && y < button_y2 + button.getHeight()) {
                sounds.playSound(7, 0);
                isBtChange2 = true;
                drawSelf();
                mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_SCORE_VIEW);
            }
            else if(x > button_x && x < button_x + button.getWidth()
                    && y > button_y3 && y < button_y3 + button.getHeight()){
                sounds.playSound(7, 0);
                isBtChange3 = true;
                drawSelf();
                mainActivity.getHandler().sendEmptyMessage(ConstantUtil.END_GAME);
            }
            //切换语言
            else if(x > button_x && x < button_x + button.getWidth()
                    && y > button_y4 && y < button_y4 + button.getHeight()){
                sounds.playSound(7, 0);
                isBtChange4 = true;
                if(changeLanguage.equals("Language")){
                    message.arg1=1;
                }
                if(changeLanguage.equals("切换语言")){
                    message.arg1=0;
                }
                message.what=01;
                mainActivity.getHandler().sendMessage(message);

                drawSelf();


            }
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float x = event.getX();
            float y = event.getY();
            if (x > button_x && x < button_x + button.getWidth()
                    && y > button_y && y < button_y + button.getHeight()) {
                isBtChange = true;
            } else {
                isBtChange = false;
            }
            if (x > button_x && x < button_x + button.getWidth()
                    && y > button_y2 && y < button_y2 + button.getHeight()) {
                isBtChange2 = true;
            } else {
                isBtChange2 = false;
            }
            if (x > button_x && x < button_x + button.getWidth()
                    && y > button_y3 && y < button_y3 + button.getHeight()) {
                isBtChange3 = true;
            } else {
                isBtChange3 = false;
            }
            if (x > button_x && x < button_x + button.getWidth()
                    && y > button_y4 && y < button_y4 + button.getHeight()) {
                isBtChange4 = true;
            } else {
                isBtChange4 = false;
            }

            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            isBtChange = false;
            isBtChange2 = false;
            isBtChange3 = false;
            isBtChange4 = false;

            return true;
        }
        return false;
    }
    @Override
    public void initBitmap() {
        background = BitmapFactory.decodeResource(getResources(), R.drawable.mbg_04);
        scalex = screen_width / background.getWidth();
        scaley = screen_height / background.getHeight();
        text = BitmapFactory.decodeResource(getResources(), R.drawable.text);
        button = BitmapFactory.decodeResource(getResources(), R.drawable.button);
        button2 = BitmapFactory.decodeResource(getResources(), R.drawable.button2);
        text_x = screen_width / 2 - text.getWidth() / 2;
        text_y = screen_height / 2 - text.getHeight()-button.getHeight();
        button_x = screen_width / 2 - button.getWidth() / 2;
        button_y = screen_height / 2 ;
        button_y2 = button_y + button.getHeight() + getResources().getDimension(R.dimen.d1) ;
        button_y3 = button_y2 + button.getHeight() +  getResources().getDimension(R.dimen.d1);
        button_y4 = button_y3 + button.getHeight() +  getResources().getDimension(R.dimen.d1);
        // 返回包围整个字符串的最小的一个Rect区域
        paint.getTextBounds(startGame, 0, startGame.length(), rect);
        strhei = rect.height();
    }
   @Override
    public void release() {
        if (!text.isRecycled()) {
            text.recycle();
        }
        if (!button.isRecycled()) {
            button.recycle();
        }
        if (!button2.isRecycled()) {
            button2.recycle();
        }

        if (!background.isRecycled()) {
            background.recycle();
        }
    }
    public int getTextMiddle(String text){
        int textWidth = (int) paint.measureText(text);
        int x = (getMeasuredWidth() - textWidth) / 2;
        return x;
    }
    @Override
    public void drawSelf() {
        try {
            canvas = sfh.lockCanvas();
            canvas.drawColor(Color.BLACK);
            canvas.save();
            canvas.scale(scalex, scaley, 0, 0);
            canvas.drawBitmap(background, 0, 0, paint);
            canvas.restore();
            canvas.drawBitmap(text, text_x, text_y, paint);
            canvas.drawBitmap(button, button_x, button_y, paint);
            canvas.drawBitmap(button, button_x, button_y2, paint);
            canvas.drawBitmap(button, button_x, button_y3, paint);
            canvas.drawBitmap(button, button_x, button_y4, paint);

            if (isBtChange) {
                canvas.drawBitmap(button2, button_x, button_y, paint);
            } else {
                canvas.drawBitmap(button, button_x, button_y, paint);
            }
            if (isBtChange2) {
                canvas.drawBitmap(button2, button_x, button_y2, paint);
            } else {
                canvas.drawBitmap(button, button_x, button_y2, paint);
            }
            if (isBtChange3) {
                canvas.drawBitmap(button2, button_x, button_y3, paint);
            } else {
                canvas.drawBitmap(button, button_x, button_y3, paint);
            }
            if (isBtChange4) {
                canvas.drawBitmap(button2, button_x, button_y4, paint);
            } else {
                canvas.drawBitmap(button, button_x, button_y4, paint);
            }

            //开始游戏的按钮
            paint.setColor(Color.RED);
            canvas.drawText(startGame, getTextMiddle(startGame), button_y
                    + button.getHeight() / 2 + strhei / 2, paint);
            //排行榜的按钮
            canvas.drawText(rank,getTextMiddle(rank),button_y2
                    + button.getHeight() / 2 + strhei / 2, paint);
            //退出游戏的按钮
            canvas.drawText(exitGame, getTextMiddle(exitGame), button_y3
                    + button.getHeight() / 2 + strhei / 2, paint);
            canvas.drawText(changeLanguage, getTextMiddle(changeLanguage), button_y4
                    + button.getHeight() / 2 + strhei / 2, paint);

        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void run() {
        while (threadFlag) {
            long startTime = System.currentTimeMillis();
            drawSelf();
            long endTime = System.currentTimeMillis();
            try {
                if (endTime - startTime < 400)
                    Thread.sleep(400 - (endTime - startTime));
            } catch (InterruptedException err) {
                err.printStackTrace();
            }
        }
    }
}
