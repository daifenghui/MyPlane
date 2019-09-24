package com.example.dfh.planethunder.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.example.dfh.planethunder.Activity.MainActivity;
import com.example.dfh.planethunder.R;
import com.example.dfh.planethunder.Sound.GameSoundPool;
import com.example.dfh.planethunder.util.ConstantUtil;

/**
 * Created by dfh on 19-8-13.
 */
@SuppressLint("ViewConstructor")
public class EndView extends BaseView {

    private float button_x;
    private float button_y;
    private float button_y2;
    private float button_y1;
    private float strwid;
    private float strhei;
    private boolean isBtChange;
    private boolean isBtChange1;
    private boolean isBtChange2;
    private String gobackMenu = getContext().getString(R.string.goback_menu);
    private String startGame = getContext().getString(R.string.restart);
    private String exitGame = getContext().getString(R.string.exitGame);
    private String loseGame = getContext().getString(R.string.game_over);
    private Bitmap button;
    private Bitmap button2;
    private Bitmap background;
    private Rect rect;
    private MainActivity mainActivity;
    private float textSize=15;

    public EndView(Context context, GameSoundPool sounds) {
        super(context, sounds);
        this.mainActivity = (MainActivity) context;
        rect = new Rect();
        thread = new Thread(this);
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

    /**
     * 触摸事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && event.getPointerCount() == 1) {
            float x = event.getX();
            float y = event.getY();

            if (x > button_x && x < button_x + button.getWidth()
                    && y > button_y && y < button_y + button.getHeight()) {
                sounds.playSound(7, 0);
                isBtChange = true;
                drawSelf();
                mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_MAIN_VIEW);
            }
            else if (x > button_x && x < button_x + button.getWidth()
                    && y > button_y1 && y < button_y1 + button.getHeight()) {
                sounds.playSound(7, 0);
                isBtChange1 = true;
                drawSelf();
                threadFlag = false;
                mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_Ready_VIEW);
            }
            else if (x > button_x && x < button_x + button.getWidth()
                    && y > button_y2 && y < button_y2 + button.getHeight()) {
                sounds.playSound(7, 0);
                isBtChange2 = true;
                drawSelf();
                threadFlag = false;
                mainActivity.getHandler().sendEmptyMessage(ConstantUtil.END_GAME);
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
                    && y > button_y1 && y < button_y1 + button.getHeight()) {
                isBtChange1 = true;
            } else {
                isBtChange1 = false;
            }
            if (x > button_x && x < button_x + button.getWidth()
                    && y > button_y2 && y < button_y2 + button.getHeight()) {
                isBtChange2 = true;
            } else {
                isBtChange2 = false;
            }
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            isBtChange = false;
            isBtChange1 = false;
            isBtChange2 = false;
            return true;
        }
        return false;
    }
    public int getTextMiddle(String text){
        int textWidth = (int) paint.measureText(text);
        int x = (getMeasuredWidth() - textWidth) / 2;
        return x;
    }
    @Override
    public void initBitmap() {
        //适配不同尺寸机型
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float width = displayMetrics.widthPixels;
            textSize  = getResources().getDimension(R.dimen.sp25);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.mbg_01);
        button = BitmapFactory.decodeResource(getResources(), R.drawable.button);
        button2 = BitmapFactory.decodeResource(getResources(), R.drawable.button2);
        scalex = screen_width / background.getWidth();
        scaley = screen_height / background.getHeight();
        button_x = screen_width / 2 - button.getWidth() / 2;
        button_y = screen_height / 2 ;
        button_y1 = button_y + button.getHeight() + 10;
        button_y2 = button_y1 + button.getHeight() + 10;
        paint.setTextSize(textSize);
        paint.getTextBounds(startGame, 0, startGame.length(), rect);
        strwid = rect.width();
        strhei = rect.height();
    }

    @Override
    public void release() {
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

    @Override
    public void drawSelf() {
        try {
            canvas = sfh.lockCanvas();
            canvas.drawColor(Color.BLACK);                        // 颜色
            canvas.save();
            canvas.scale(scalex, scaley, 0, 0);                    // 缩放
            canvas.drawBitmap(background, 0, 0, paint);        // 图片
            canvas.restore();
            if (isBtChange) {
                canvas.drawBitmap(button2, button_x, button_y, paint);
            } else {
                canvas.drawBitmap(button, button_x, button_y, paint);
            }
            if (isBtChange1) {
                canvas.drawBitmap(button2, button_x, button_y1, paint);
            } else {
                canvas.drawBitmap(button, button_x, button_y1, paint);
            }
            if (isBtChange2) {
                canvas.drawBitmap(button2, button_x, button_y2, paint);
            } else {
                canvas.drawBitmap(button, button_x, button_y2, paint);
            }
            paint.setTextSize(getResources().getDimension(R.dimen.sp20));
            paint.getTextBounds(startGame, 0, startGame.length(), rect);
            canvas.drawText(startGame, getTextMiddle(startGame), button_y + button.getHeight() / 2 + strhei / 2, paint);
            canvas.drawText(gobackMenu,  getTextMiddle(gobackMenu), button_y1 + button.getHeight() / 2 + strhei / 2, paint);
            canvas.drawText(exitGame,  getTextMiddle(exitGame), button_y2 + button.getHeight() / 2 + strhei / 2, paint);

            paint.setTextSize(getResources().getDimension(R.dimen.sp25));
          /*  paint.setStyle(Paint.Style.FILL_AND_STROKE);*/
            paint.setStrokeWidth(5);
            float textlong = paint.measureText(loseGame);
            canvas.drawText(loseGame,screen_width / 2 - textlong / 2, screen_height / 2 - 100, paint);
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
