package com.example.dfh.planethunder.View;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.Toast;

import com.example.dfh.planethunder.Activity.MainActivity;
import com.example.dfh.planethunder.Factory.GameObjectFactory;
import com.example.dfh.planethunder.Object.GameObject;
import com.example.dfh.planethunder.Object.LifeGoods;
import com.example.dfh.planethunder.Object.MissileGoods;
import com.example.dfh.planethunder.Object.PurpleBulletGoods;
import com.example.dfh.planethunder.Object.RedBulletGoods;
import com.example.dfh.planethunder.Other.GameView;
import com.example.dfh.planethunder.Plane.BigPlane;
import com.example.dfh.planethunder.Plane.EnemyPlane;
import com.example.dfh.planethunder.Plane.MiddlePlane;
import com.example.dfh.planethunder.Plane.MyPlane;
import com.example.dfh.planethunder.Plane.SmallPlane;
import com.example.dfh.planethunder.R;
import com.example.dfh.planethunder.Sound.GameSoundPool;
import com.example.dfh.planethunder.constant.DebugConstant;
import com.example.dfh.planethunder.constant.GameConstant;
import com.example.dfh.planethunder.util.ConstantUtil;
import com.example.dfh.planethunder.util.DbUtil;
import com.example.dfh.planethunder.util.TimeUtil;

import java.util.ArrayList;

/**
 * 游戏进行的主界面
 */
@SuppressLint("ViewConstructor")
public class MainView extends BaseView {
	private static int speedTime;
	private  MissileGoods missileGoods;
	private  LifeGoods lifeGoods;
	private ArrayList<BigPlane> bigPlanes;
	private ArrayList<EnemyPlane> enemyPlanes;
	private PurpleBulletGoods purpleBulletGoods;
	private RedBulletGoods redBulletGoods;
	public int missileCount; // 导弹的数量
	public boolean isSaved;
	private MediaPlayer mMediaPlayer;
	private boolean isTouchPlane;
	private float play_bt_w;
	private float play_bt_h;
	public boolean isPlay;
	private Bitmap playButton;
	private Bitmap background;
	private Bitmap background2;
	public float bg_y;
	public float bg_y2;
	public MyPlane myPlane;
	public int mLifeAmount;// 生命总数
	private GameObjectFactory factory;
	//计时器
	private Handler mHandler = new Handler();
	public long currentSecond = 0;//当前毫秒数
	private String timmer="";
	private String sscore = getContext().getString(R.string.score);
	private int middlePlaneScore;
	private int bigPlaneScore;
	private int bossPlaneScore;
	private int missileScore;
	private int lifeScore;
	private int bulletScore;
	private int bulletScore2;
	public int sumScore;
	private Bitmap missile_bt;
	private float missile_bt_y;
	private Bitmap life_amount;
	private Bitmap boom;
	private Bitmap plane_shield;
	public  String gameState="READY";
	Thread thread1;
	private GameView gameview=new GameView();
	private  boolean threadflag1=false;
	private float tx=getResources().getDimension(R.dimen.dp300);
	private float ty=getResources().getDimension(R.dimen.dp300);
	private float textSize=getResources().getDimension(R.dimen.t2);
	MyThread myThread = new MyThread();
	class MyThread implements Runnable {
		@Override
		public void run() {
			synchronized (this) {
				if (isPlay) {
					currentSecond = currentSecond + 1000;
					timmer = TimeUtil.getFormatHMS(currentSecond);
					//递归调用本runable对象，实现每隔一秒一次执行任务
					mHandler.postDelayed(this, 1000);
				}
			}
		}

	}
	public MainView(Context context, GameSoundPool sounds) {
		super(context, sounds);
		isPlay = true;
		speedTime = GameConstant.GAMESPEED;
// 背景音乐
		mMediaPlayer = MediaPlayer.create(mainActivity, R.raw.game);
		mMediaPlayer.setLooping(true);
		if (!mMediaPlayer.isPlaying()) {
			mMediaPlayer.start();
		}
		factory = new GameObjectFactory(); // 工厂类
		bigPlanes = new ArrayList<>(); // 大型机集合
		enemyPlanes = new ArrayList<>();// 敌机集合

		initGameView();


}

	// 视图改变的方法
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		super.surfaceChanged(arg0, arg1, arg2, arg3);
	}

	// 视图创建的方法
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		super.surfaceCreated(arg0);
		initBitmap(); // 初始化图片资源
		for (GameObject obj : enemyPlanes) {
			obj.setScreenWH(screen_width, screen_height);
		}
		missileGoods.setScreenWH(screen_width, screen_height);
		lifeGoods.setScreenWH(screen_width, screen_height);

		purpleBulletGoods.setScreenWH(screen_width, screen_height);
		redBulletGoods.setScreenWH(screen_width, screen_height);
		//Log.e("tag2",myPlane.getObject_x()+","+myPlane.getObject_y());
		//根据是否存档初始化不同的myPlane
		if(!isSaved){
			myPlane.setScreenWH(screen_width, screen_height);}
		else{
			myPlane.setScreenWH1(screen_width, screen_height);}
			myPlane.setAlive(true);
			thread = new Thread(this);//绘图线程
			thread1= new Thread(myThread);//计时器线程
		if (thread.isAlive()) {
			thread.start();

		} else {
			thread = new Thread(this);
			thread.start();
		}
           if (thread1.isAlive()){
			thread1.start();
		   }
	else {
			   thread1 = new Thread(myThread);
			   thread1.start();
		   }
	}

	// 视图销毁的方法
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		super.surfaceDestroyed(arg0);
		//release();// 释放资源
		mMediaPlayer.stop();
		threadFlag=false;
		isPlay=false;
	}

	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			isTouchPlane = false;
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
			float x = event.getX();
			float y = event.getY();
			if (x > 10 && x < 10 + play_bt_w && y > 10 && y < 10 + play_bt_h) {
				if (isPlay) {
					isPlay = false;
					threadflag1=false;
				} else {
					isPlay = true;
					threadflag1=true;
					synchronized (thread) {
						thread.notify();
					}

					if(thread1.isAlive()){
						thread1.start();
					}
					else{
						thread1 = new Thread(myThread);
						thread1.start();
					}
				}
				return true;
			}
			// 判断玩家飞机是否被按下
			else if (x > myPlane.getObject_x()
					&& x < myPlane.getObject_x() + myPlane.getObject_width()
					&& y > myPlane.getObject_y()
					&& y < myPlane.getObject_y() + myPlane.getObject_height()) {
				if (isPlay) {
					isTouchPlane = true;
				}
				return true;
			}

			// 判断导弹按钮是否被按下
			else if (x > 10 && x < 10 + missile_bt.getWidth()
					&& y > missile_bt_y
					&& y < missile_bt_y + missile_bt.getHeight()) {
				if (missileCount > 0) {
					missileCount--;
					myPlane.setMissileState(true);
					sounds.playSound(5, 0);

					for (EnemyPlane pobj : enemyPlanes) {
						if (pobj.isCanCollide()) {
							pobj.attacked(GameConstant.MISSILE_HARM); // 敌机增加伤害
							if (pobj.isExplosion()) {
								addGameScore(pobj.getScore());// 获得分数
							}
						}
					}

					// 此线程不能放在绘图函数中，否则当处于无敌exitGame状态或者导弹连续按下时，爆炸效果无法显现
					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								Thread.sleep(GameConstant.MISSILEBOOM_TIME);
							} catch (InterruptedException e) {
								e.printStackTrace();
							} finally {
								myPlane.setMissileState(false);
							}

						}
					}).start();

				}
				return true;
			}
		}

		// 响应手指在屏幕移动的事件
		else if (event.getAction() == MotionEvent.ACTION_MOVE
				&& event.getPointerCount() == 1) {
			// 判断触摸点是否为玩家的飞机
			if (isTouchPlane) {
				float x = event.getX();
				float y = event.getY();
				if (x > myPlane.getMiddle_x() + 20) {
					if (myPlane.getMiddle_x() + myPlane.getSpeed() <= screen_width) {
						myPlane.setMiddle_x(myPlane.getMiddle_x()
								+ myPlane.getSpeed());
					}
				} else if (x < myPlane.getMiddle_x() - 20) {
					if (myPlane.getMiddle_x() - myPlane.getSpeed() >= 0) {
						myPlane.setMiddle_x(myPlane.getMiddle_x()
								- myPlane.getSpeed());
					}
				}
				if (y > myPlane.getMiddle_y() + 20) {
					if (myPlane.getMiddle_y() + myPlane.getSpeed() <= screen_height) {
						myPlane.setMiddle_y(myPlane.getMiddle_y()
								+ myPlane.getSpeed());
					}
				} else if (y < myPlane.getMiddle_y() - 20) {
					if (myPlane.getMiddle_y() - myPlane.getSpeed() >= 0) {
						myPlane.setMiddle_y(myPlane.getMiddle_y()
								- myPlane.getSpeed());
					}
				}
				return true;
			}
		}
		return false;
	}

	// 初始化图片资源方法
	@Override
	public void initBitmap() {
		playButton = BitmapFactory.decodeResource(getResources(), R.drawable.play);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.mbg_01);
		background2 = BitmapFactory.decodeResource(getResources(),
				R.drawable.mbg_02);
		scalex = screen_width / background.getWidth();
		scaley = screen_height / background.getHeight();
		play_bt_w = playButton.getWidth();
		play_bt_h = playButton.getHeight() / 2;


		missile_bt = BitmapFactory.decodeResource(getResources(),
				R.drawable.missile_bt);

		life_amount = BitmapFactory.decodeResource(getResources(),
				R.drawable.life_amount);

		boom = BitmapFactory.decodeResource(getResources(), R.drawable.boom);
		plane_shield = BitmapFactory.decodeResource(getResources(), R.drawable.plane_shield);
		missile_bt_y = screen_height - 10 - missile_bt.getHeight();


		//适配不同尺寸机型
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		float width = displayMetrics.widthPixels;
		if (width <= 320) {
			textSize = 15;
			tx = 200;
			ty = 25;
		}
		if (width <= 480 && width > 320) {
			textSize = 25;
			tx = 300;
			ty = 38;
		}
		if (width <= 720 && width > 480) {
			textSize = 35;
			tx = 450;
			ty = 48;
		}
		if (width <= 1080 && width > 720) {
			textSize = 50;
			tx = 700;
			ty = 60;
		}
		if(width<=1440 && width>1080){
			textSize =getResources().getDimension(R.dimen.t2);
			tx = 1000;
			ty = 90;
		}
	}
	// 初始化游戏对象
	public void initObject() {

		for (EnemyPlane obj : enemyPlanes) {
			// 初始化小型敌机
			if (obj instanceof SmallPlane) {

				if (!obj.isAlive()) {
					obj.initial(speedTime, 0, 0);
					break;
				   }
				}

			// 初始化中型敌机
			else if (obj instanceof MiddlePlane) {
				if (middlePlaneScore >= GameConstant.MIDDLEPLANE_APPEARSCORE) {
					if (!obj.isAlive()) {
						obj.initial(speedTime, 0, 0);
						break;
					}
				}
			}
			// 初始化大型敌机
			else if (obj instanceof BigPlane) {
				if (bigPlaneScore >= GameConstant.BIGPLANE_APPEARSCORE) {
					if (!obj.isAlive()) {
						obj.initial(speedTime, 0, 0);
						break;
					}
				}
			}
			// 初始化BOSS敌机
			else {
				if (bossPlaneScore >= GameConstant.BOSSPLANE_APPEARSCORE) {
					if (!obj.isAlive()) {
						obj.initial(speedTime, 0, 0);
						bossPlaneScore = 0;
						break;
					}
				}
			}
		}

		// 初始化导弹物品
		if (missileScore >= GameConstant.MISSILE_APPEARSCORE) {
			if (!missileGoods.isAlive()) {
				missileScore = 0;
				if (DebugConstant.MISSILEGOODS_APPEAR) {
					missileGoods.initial(0, 0, 0);
				}
			}
		}

		// 初始化生命物品
		if (lifeScore >= GameConstant.LIFE_APPEARSCORE) {
			if (!lifeGoods.isAlive()) {
				lifeScore = 0;
				if (DebugConstant.LIFEGOODS_APPEAR) {
					lifeGoods.initial(0, 0, 0);
				}
			}
		}
		// 初始化子弹1物品
		if (bulletScore >= GameConstant.BULLET1_APPEARSCORE) {
			if (!purpleBulletGoods.isAlive()) {
				bulletScore = 0;
				if (DebugConstant.BULLETGOODS1_APPEAR) {
					purpleBulletGoods.initial(0, 0, 0);
				}
			}
		}
		// 初始化子弹2物品
		if (bulletScore2 >= GameConstant.BULLET2_APPEARSCORE) {
			if (!redBulletGoods.isAlive()) {
				bulletScore2 = 0;
				if (DebugConstant.BULLETGOODS2_APPEAR) {
					redBulletGoods.initial(0, 0, 0);
				}
			}
		}

		// 初始化bigPlane的子弹，遍历所有大型机
		for (BigPlane big_plane : bigPlanes) {
			if (big_plane.isAlive()) {
				if (!myPlane.getMissileState()) {
					big_plane.initBullet();
				}
			}
		}
		myPlane.isBulletOverTime();
		myPlane.initBullet(); // 初始化玩家飞机的子弹
	}

	// 释放图片资源的方法
	@Override
	public void release() {
		for (GameObject obj : enemyPlanes) {
			obj.release();
		}

		myPlane.release();
		/*missileGoods.release();
		lifeGoods.release();*/
		purpleBulletGoods.release();
		redBulletGoods.release();

		if (!playButton.isRecycled()) {
			playButton.recycle();
		}
		if (!background.isRecycled()) {
			background.recycle();
		}
		if (!background2.isRecycled()) {
			background2.recycle();
		}
		if (!missile_bt.isRecycled()) {
			missile_bt.recycle();
		}
		if (!life_amount.isRecycled()) {
			life_amount.recycle();
		}
		if (!boom.isRecycled()) {
			boom.recycle();
		}
		if (!plane_shield.isRecycled()) {
			plane_shield.recycle();
		}
	}
	public void drawSelf() {
		try {

			canvas = sfh.lockCanvas();
			canvas.drawColor(Color.BLACK);
			canvas.save();
			canvas.scale(scalex, scaley, 0, 0);
			Log.e("tag3",bg_y+","+bg_y2);
			canvas.drawBitmap(background, 0, bg_y, paint);
			canvas.drawBitmap(background2, 0, bg_y2, paint);
			canvas.restore();
			// 绘制按钮
			canvas.save();
			canvas.clipRect(10, 10, 10 + play_bt_w, 10 + play_bt_h);
			if (isPlay) {
				canvas.drawBitmap(playButton, 10, 10, paint);
			} else {
				canvas.drawBitmap(playButton, 10, 10 - play_bt_h, paint);
			}
			canvas.restore();

			// 绘制积分文字
			paint.setTextSize(textSize);
			paint.setColor(Color.rgb(100, 100, 80));
			canvas.drawText(sscore +":"+sumScore, play_bt_w+getResources().getDimension(R.dimen.d2),
					getResources().getDimension(R.dimen.d3), paint);
			paint.setColor(Color.rgb(100, 100, 80));
			canvas.drawText(getContext().getString(R.string.time) +":"+ timmer, tx, ty, paint);

			// 绘制生命数值
			if (mLifeAmount > 0) {
				paint.setColor(Color.BLACK);
				canvas.drawBitmap(life_amount, screen_width - getResources().getDimension(R.dimen.px150),
						screen_height - life_amount.getHeight() - getResources().getDimension(R.dimen.px10), paint);
				canvas.drawText("X " + String.valueOf(mLifeAmount),
						screen_width - life_amount.getWidth(),
						screen_height - getResources().getDimension(R.dimen.px25), paint);
			}

			// 绘制爆炸效果图
			if (myPlane.getMissileState()) {
				float boom_x = myPlane.getMiddle_x() - boom.getWidth() / 2;
				float boom_y = myPlane.getMiddle_y() - boom.getHeight() / 2;
				canvas.drawBitmap(boom, boom_x, boom_y, paint);
			}

			// 绘制无敌防护效果图
			if (myPlane.isInvincible() && !myPlane.getDamaged()) {
				float plane_shield_x = myPlane.getMiddle_x() - plane_shield.getWidth() / 2;
				float plane_shield_y = myPlane.getMiddle_y() - plane_shield.getHeight() / 2;

				canvas.drawBitmap(plane_shield, plane_shield_x, plane_shield_y, paint);

			}

			// 绘制导弹按钮
			if (missileCount > 0) {
				paint.setTextSize(getResources().getDimension(R.dimen.sp20));
				paint.setColor(Color.BLACK);
				canvas.drawBitmap(missile_bt, getResources().getDimension(R.dimen.px10), missile_bt_y, paint);
				canvas.drawText("X " + String.valueOf(missileCount),
						getResources().getDimension(R.dimen.px10) + missile_bt.getWidth(), screen_height - getResources().getDimension(R.dimen.px25), paint);// 绘制文字
			}

			// 绘制导弹物品
			if (missileGoods.isAlive()) {
				if (missileGoods.isCollide(myPlane)) {
					if (missileCount < GameConstant.MISSILE_MAXCOUNT) {
						missileCount++;
					}
					missileGoods.setAlive(false);
					sounds.playSound(6, 0);
				} else
					missileGoods.drawSelf(canvas);
			}
			// 绘制生命物品
			if (lifeGoods.isAlive()) {
				if (lifeGoods.isCollide(myPlane)) {
					if (mLifeAmount < GameConstant.LIFE_MAXCOUNT) {
						mLifeAmount++;
					}
					lifeGoods.setAlive(false);
					sounds.playSound(6, 0);
				} else
					lifeGoods.drawSelf(canvas);
			}
			// 绘制子弹物品
			if (purpleBulletGoods.isAlive()) {
				if (purpleBulletGoods.isCollide(myPlane)) {
					purpleBulletGoods.setAlive(false);
					sounds.playSound(6, 0);

					myPlane.setChangeBullet(true);
					myPlane.changeBullet(ConstantUtil.MYBULLET1);
					myPlane.setStartTime(System.currentTimeMillis());

				} else
					purpleBulletGoods.drawSelf(canvas);
			}
			// 绘制子弹2物品
			if (redBulletGoods.isAlive()) {
				if (redBulletGoods.isCollide(myPlane)) {
					redBulletGoods.setAlive(false);
					sounds.playSound(6, 0);

					myPlane.setChangeBullet(true);
					myPlane.changeBullet(ConstantUtil.MYBULLET2);
					myPlane.setStartTime(System.currentTimeMillis());

				} else
					redBulletGoods.drawSelf(canvas);
			}
			// 绘制敌机
			for (EnemyPlane obj : enemyPlanes) {
				if (obj.isAlive()) {
					obj.drawSelf(canvas);
					// 检测敌机是否与玩家的飞机碰撞
					if (obj.isCanCollide() && myPlane.isAlive()) {
						// 检测我方是否处于无敌状态或者导弹爆炸状态
						if (obj.isCollide(myPlane) && !myPlane.isInvincible()
								&& !myPlane.getMissileState()) {
							myPlane.setAlive(false);
						}
					}
				}
			}
			if (!myPlane.isAlive()) {
				sounds.playSound(4, 0); // 飞机炸毁的音效
				// 判断生命总数，数值大于零则-1，直到生命总数小于零，Gameover
				if (mLifeAmount > 0) {
					mLifeAmount--;
					myPlane.setAlive(true);
					new Thread(new Runnable() {

						@Override
						public void run() {
							myPlane.setDamaged(true);
							myPlane.setInvincibleTime(GameConstant.BOOM_TIME);
							myPlane.setDamaged(false);
							myPlane.setInvincibleTime(GameConstant.INVINCIBLE_TIME);
						}
					}).start();

				} else {
					if (DebugConstant.ETERNAL) {
						// 设置不死亡，供游戏测试使用
						threadFlag = true;
						myPlane.setAlive(true);

						// 继续实现机体受损及闪光效果
						new Thread(new Runnable() {

							@Override
							public void run() {
								myPlane.setDamaged(true);
								myPlane.setInvincibleTime(GameConstant.BOOM_TIME);
								myPlane.setDamaged(false);
								myPlane.setInvincibleTime(GameConstant.INVINCIBLE_TIME);
							}
						}).start();

					} else {
						// 正常情况，游戏结束,并停止音乐
						threadFlag = false;
						isSaved=false;
						gameState="OVER";
						if (mMediaPlayer.isPlaying()) {
							mMediaPlayer.stop();
						}
					}

				}

			}

			myPlane.drawSelf(canvas); // 绘制玩家的飞机
			myPlane.shoot(canvas, enemyPlanes);
			sounds.playSound(1, 0); // 子弹音效
		}
		/*+ String.valueOf(sumScore)*/

		catch (Exception err) {
			err.printStackTrace();
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}

	// 增加游戏分数的方法
	public void addGameScore(int score) {
		middlePlaneScore += score; // 中型敌机的积分
		bigPlaneScore += score; // 大型敌机的积分
		bossPlaneScore += score; // boss型敌机的积分
		missileScore += score; // 导弹的积分
		lifeScore += score;// 生命的积分
		bulletScore += score; // 子弹的积分
		bulletScore2 += score; // 子弹的积分
		sumScore += score; // 游戏总得分
	}
	// 播放音效
	public void playSound(int key) {
		sounds.playSound(key, 0);
	}
	public void backgroundMove() {
		if (bg_y > bg_y2) {
			bg_y += 10;
			bg_y2 = bg_y - background.getHeight();
		} else {
			bg_y2 += 10;
			bg_y = bg_y2 - background.getHeight();
		}
		if (bg_y >= background.getHeight()) {
			bg_y = bg_y2 - background.getHeight();
		} else if (bg_y2 >= background.getHeight()) {
			bg_y2 = bg_y - background.getHeight();
		}
	}
	//保存游戏状态
	public void saveGameState(String gameState) {
		SharedPreferences sharedPreferences = getContext().getSharedPreferences("plane", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("GameState", gameState);
		editor.commit();
	}



	@Override
	public void run() {
		while (threadFlag) {
			gameState="RUNNING";
			saveGameState(gameState);
			long startTime = System.currentTimeMillis();
			initObject();
			drawSelf();
			backgroundMove(); // 背景移动的逻辑
			long endTime = System.currentTimeMillis();
			if (!isPlay) {
				mMediaPlayer.pause();// 音乐暂停
				synchronized (thread) {
					try {
						thread.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else {
				if (!mMediaPlayer.isPlaying()) {
					mMediaPlayer.start();
				}
			}
			try {
				if (endTime - startTime < 60)
					Thread.sleep(60 - (endTime - startTime));
			} catch (InterruptedException err) {
				err.printStackTrace();
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		saveGameState(gameState);
		SharedPreferences sharedPreferences=getContext().getSharedPreferences("plane", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor=sharedPreferences.edit();
		editor.putBoolean("isSaved",false);
		if(gameState.equals("OVER")) {
			Message message = new Message();
			message.what = ConstantUtil.TO_SAVE_RECORD;
			message.arg1 = Integer.valueOf(sumScore);
			mainActivity.getHandler().sendMessage(message);
		}
			}
			/*
		* 存储游戏数据
		* */
	public GameView getGameView() {
		GameView gameview = new GameView();
		gameview.setEnemyPlanes(enemyPlanes);
		gameview.setMyPlane(myPlane);
		gameview.setMissileGoods(missileGoods);
		gameview.setLifeGoods(lifeGoods);
		return gameview;
	}

	public void setGameView(GameView gameView){
		enemyPlanes=gameView.getEnemyPlanes();
		myPlane=gameView.getMyPlane();
		missileGoods=gameView.getMissileGoods();
		lifeGoods=gameView.getLifeGoods();
	}
	public void initGameView(){
		SharedPreferences sharedPreferences=getContext().getSharedPreferences("plane",Context.MODE_PRIVATE);
		isSaved=sharedPreferences.getBoolean("isSaved",false);
		gameview= DbUtil.queryGame(getContext());
		if(isSaved&&!gameview.getEnemyPlanes().isEmpty()){
			this.isPlay=false;
			this.sumScore=sharedPreferences.getInt("Score",0);
			this.currentSecond=sharedPreferences.getLong("CurrentSecond",0);
			this.missileCount=sharedPreferences.getInt("MissileAmount",0);
			this.mLifeAmount=sharedPreferences.getInt("LifeAmount",0);
			this.bg_y=sharedPreferences.getFloat("bg_y",0);
			this.bg_y2=sharedPreferences.getFloat("bg_y2",0);
	//Log.e("myTag",bg_y+","+bg_y2);

			purpleBulletGoods = (PurpleBulletGoods) factory
					.createPurpleBulletGoods(getResources());
			redBulletGoods = (RedBulletGoods) factory
					.createRedBulletGoods(getResources());

			this.setGameView(gameview);
			myPlane.setMainView(this);
			//Log.e("myTag",myPlane.getObject_x()+","+myPlane.getObject_y()+isSaved);
		}
		else
		{   isSaved=false;
			bg_y = 0;
			bg_y2 = bg_y - screen_height;
			myPlane = (MyPlane) factory.createMyPlane(getResources());// 生产玩家的飞机
			myPlane.setMainView(this);
			//Log.e("myTag1",myPlane.getObject_x()+""+isSaved);
			mLifeAmount = GameConstant.LIFEAMOUNT;// 初始生命值
			missileCount = GameConstant.MISSILECOUNT;// 初始导弹数
			for (int i = 0; i < SmallPlane.sumCount; i++) {
				// 生产小型敌机
				SmallPlane smallPlane = (SmallPlane) factory
						.createSmallPlane(getResources());
				enemyPlanes.add(smallPlane);
			}
			for (int i = 0; i < MiddlePlane.sumCount; i++) {
				// 生产中型敌机
				MiddlePlane middlePlane = (MiddlePlane) factory
						.createMiddlePlane(getResources());
				enemyPlanes.add(middlePlane);
			}
			for (int i = 0; i < BigPlane.sumCount; i++) {
				BigPlane bigPlane = (BigPlane) factory
						.createBigPlane(getResources());
				enemyPlanes.add(bigPlane);
				bigPlane.setMyPlane(myPlane);

				bigPlanes.add(bigPlane);
			}

			// 生产导弹物品
			missileGoods = (MissileGoods) factory
					.createMissileGoods(getResources());
			// 生产生命物品
			lifeGoods = (LifeGoods) factory.createLifeGoods(getResources());
			// 生产子弹物品
			purpleBulletGoods = (PurpleBulletGoods) factory
					.createPurpleBulletGoods(getResources());
			redBulletGoods = (RedBulletGoods) factory
					.createRedBulletGoods(getResources());
		}
	}

}


