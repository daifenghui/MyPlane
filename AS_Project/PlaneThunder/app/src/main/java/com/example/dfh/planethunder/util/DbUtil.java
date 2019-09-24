package com.example.dfh.planethunder.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dfh.planethunder.Factory.GameObjectFactory;
import com.example.dfh.planethunder.Object.LifeGoods;
import com.example.dfh.planethunder.Object.MissileGoods;
import com.example.dfh.planethunder.Other.GameView;
import com.example.dfh.planethunder.Plane.BigPlane;
import com.example.dfh.planethunder.Plane.EnemyPlane;
import com.example.dfh.planethunder.Plane.MiddlePlane;
import com.example.dfh.planethunder.Plane.MyPlane;
import com.example.dfh.planethunder.Plane.SmallPlane;

import java.util.ArrayList;

/**
 * Created by dfh on 19-9-5.
 */

public class DbUtil {

   /*保存游戏数据*/
    public static void insertGame(GameView gameView, Context context){
        SQLiteDatabase db;
        ArrayList<EnemyPlane> mEnemyPlanes;
        MyPlane mMyPlane;
        LifeGoods mLifeGoods;
        MissileGoods mMissileGoods;

        DatabaseHelper myHelper= new DatabaseHelper(context,"mdb",null,1);
        db=myHelper.getWritableDatabase();
        mEnemyPlanes=gameView.getEnemyPlanes();
        mMyPlane=gameView.getMyPlane();
        mLifeGoods=gameView.getLifeGoods();
        mMissileGoods=gameView.getMissileGoods();
        String mlocation;
        db.delete("tb_object",null,null);
        db.delete("tb_myPlane",null,null);
        if(mEnemyPlanes!=null){
            for (EnemyPlane obj : mEnemyPlanes){
                mlocation=obj.getObject_x()+","+obj.getObject_y();
                Log.e("mLocation",mlocation);
                if(obj instanceof SmallPlane){
                    db.execSQL("insert into tb_object(location,object_type,blood) values('"+mlocation+"','"+1+"','"+obj.getBlood()+"')");
                }
                if(obj instanceof MiddlePlane){
                    db.execSQL("insert into tb_object(location,object_type,blood) values('"+mlocation+"','"+2+"','"+obj.getBlood()+"')");

                }
                if(obj instanceof BigPlane){
                    db.execSQL("insert into tb_object(location,object_type,blood) values('"+mlocation+"','"+3+"','"+obj.getBlood()+"')");

                }
            }
        }
        if(mLifeGoods!=null){
            mlocation=mLifeGoods.getObject_x()+","+mLifeGoods.getObject_y();
            db.execSQL("insert into tb_object(location,object_type,direction) values('"+mlocation+"','"+4+"','"+mLifeGoods.getDirection()+"')");

        }
        if(mMissileGoods!=null){
            mlocation=mMissileGoods.getObject_x()+","+mMissileGoods.getObject_y();
            db.execSQL("insert into tb_object(location,object_type,direction) values('"+mlocation+"','"+5+"','"+mMissileGoods.getDirection()+"')");
        }
        if(mMyPlane!=null){
            int isDamaged;
            mlocation=mMyPlane.getMiddle_x()+","+mMyPlane.getObject_y();
            if(mMyPlane.getDamaged()){
                isDamaged=1;
            }
            else{
                isDamaged=0;
            }
            db.execSQL("insert into tb_myPlane(location,bulletType,isDamaged) values('"+mlocation+"','"+mMyPlane.getBulletType()+"','"+isDamaged+"')");

        }

        db.close();
    }


    /*获取上一次保存的数据*/
    public static GameView queryGame(Context context){
        GameView gameView=new GameView();
        SQLiteDatabase db;
        ArrayList<EnemyPlane> mEnemyPlanes =  new ArrayList<>();
        MyPlane mMyPlane =new MyPlane(context.getResources()) ;
        LifeGoods mLifeGoods=new LifeGoods(context.getResources()) ;
        MissileGoods mMissileGoods=new MissileGoods(context.getResources());
        GameObjectFactory factory=new GameObjectFactory();
        float objx;
        float objy;
        DatabaseHelper myHelper= new DatabaseHelper(context,"mdb",null,1);
        db=myHelper.getWritableDatabase();

        /*查询object表*/
        Cursor cursor=db.rawQuery("select * from tb_object ",null);
        while (cursor.moveToNext()){
            String[]  strs=cursor.getString(1).split(",");
            objx=Float.parseFloat(strs[0]);
            objy=Float.parseFloat(strs[1]);
            if(cursor.getInt(2)==1){
                SmallPlane smallPlane = (SmallPlane) factory
                        .createSmallPlane(context.getResources());
                smallPlane.setObject_x(objx);
                smallPlane.setObject_y(objy);
                smallPlane.setBlood(cursor.getInt(3));
                Log.e("smallPlane",smallPlane.getBlood()+"");
                mEnemyPlanes.add(smallPlane);
            }
            if(cursor.getInt(2)==2){
                MiddlePlane middlePlane = (MiddlePlane) factory
                        .createMiddlePlane(context.getResources());
                middlePlane.setObject_x(objx);
                middlePlane.setObject_y(objy);
                middlePlane.setBlood(cursor.getInt(3));
                mEnemyPlanes.add(middlePlane);
            }
            if(cursor.getInt(2)==3){
                BigPlane bigPlane = (BigPlane) factory
                        .createBigPlane(context.getResources());
                bigPlane.setObject_x(objx);
                bigPlane.setObject_y(objy);
                bigPlane.setBlood(cursor.getInt(3));
                mEnemyPlanes.add(bigPlane);
            }
            if(cursor.getInt(2)==4){
                mLifeGoods=(LifeGoods)factory
                        .createLifeGoods(context.getResources());
                mLifeGoods.setObject_x(objx);
                mLifeGoods.setObject_y(objy);
                mLifeGoods.setDirection(cursor.getInt(4));
            }
            if(cursor.getInt(2)==5){
                mMissileGoods=(MissileGoods) factory
                        .createMissileGoods(context.getResources());
                mMissileGoods.setObject_x(objx);
                mMissileGoods.setObject_y(objy);
                mMissileGoods.setDirection(cursor.getInt(4));
            }
        }
        /*查询myPlane表*/
        Cursor cursor1=db.rawQuery("select * from tb_myPlane",null);
        while (cursor1.moveToNext()){
            boolean isDamaged;
            String[]  strs1=cursor1.getString(1).split(",");
            Log.e("gameview",strs1[1]);
            Log.e("gameview1",strs1[0]);
             mMyPlane.setObject_x(Float.parseFloat(strs1[0]));
             mMyPlane.setObject_y(Float.parseFloat(strs1[1]));
             mMyPlane.setBulletType(cursor1.getInt(2));
                if(cursor1.getInt(3)==1){
                    isDamaged=true;
                }else{
                    isDamaged=false;
                }
             mMyPlane.setDamaged(isDamaged);
        }
        db.delete("tb_object",null,null);
        db.delete("tb_myPlane",null,null);
        gameView.setEnemyPlanes(mEnemyPlanes);
        gameView.setLifeGoods(mLifeGoods);
        gameView.setMissileGoods(mMissileGoods);
        gameView.setMyPlane(mMyPlane);


        return gameView;
    }


}
