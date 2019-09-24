package com.example.dfh.planethunder.Other;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.dfh.planethunder.Object.LifeGoods;
import com.example.dfh.planethunder.Object.MissileGoods;
import com.example.dfh.planethunder.Plane.BigPlane;
import com.example.dfh.planethunder.Plane.EnemyPlane;
import com.example.dfh.planethunder.Plane.MyPlane;

import java.util.ArrayList;

/**
 * Created by dfh on 19-9-4.
 */

public class GameView  {
    private MyPlane mMyPlane;
    private ArrayList<EnemyPlane> enemyPlanes;
    private MissileGoods missileGoods=null;
    private  LifeGoods lifeGoods=null;
    public MyPlane getMyPlane() {
        return mMyPlane;
    }

    public void setMyPlane(MyPlane myPlane) {
        mMyPlane = myPlane;
    }

    public ArrayList<EnemyPlane> getEnemyPlanes() {
        return enemyPlanes;
    }

    public void setEnemyPlanes(ArrayList<EnemyPlane> enemyPlanes) {
        this.enemyPlanes = enemyPlanes;
    }

    public MissileGoods getMissileGoods() {
        return missileGoods;
    }

    public void setMissileGoods(MissileGoods missileGoods) {
        this.missileGoods = missileGoods;
    }

    public LifeGoods getLifeGoods() {
        return lifeGoods;
    }

    public void setLifeGoods(LifeGoods lifeGoods) {
        this.lifeGoods = lifeGoods;
    }

}
