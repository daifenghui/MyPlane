package com.example.dfh.planethunder.Factory;

import android.content.res.Resources;

import com.example.dfh.planethunder.Bullet.BigPlaneBullet;
import com.example.dfh.planethunder.Bullet.MyBlueBullet;
import com.example.dfh.planethunder.Bullet.MyPurpleBullet;
import com.example.dfh.planethunder.Bullet.MyRedBullet;
import com.example.dfh.planethunder.Object.GameObject;
import com.example.dfh.planethunder.Object.LifeGoods;
import com.example.dfh.planethunder.Object.MissileGoods;
import com.example.dfh.planethunder.Object.PurpleBulletGoods;
import com.example.dfh.planethunder.Object.RedBulletGoods;
import com.example.dfh.planethunder.Plane.BigPlane;
import com.example.dfh.planethunder.Plane.MiddlePlane;
import com.example.dfh.planethunder.Plane.MyPlane;
import com.example.dfh.planethunder.Plane.SmallPlane;

/**
 * Created by dfh on 19-8-28.
 */

public class GameObjectFactory {
    /**
     * 小型机
     * @param resources
     * @return
     */
    public GameObject createSmallPlane(Resources resources) {
        return new SmallPlane(resources);
    }

    /**
     * 生产中型机
     * @param resources
     * @return
     */
    public GameObject createMiddlePlane(Resources resources) {
        return new MiddlePlane(resources);
    }

    /**
     * 生产大型机
     * @param resources
     * @return
     */
    public GameObject createBigPlane(Resources resources) {
        return new BigPlane(resources);
    }

    /**
     * 生产boss机体
     * @param resources
     * @return
     */
   /* public GameObject createBossPlane(Resources resources) {
        return new BossPlane(resources);
    }*/

    /**
     * 生产我方机体
     * @param resources
     * @return
     */
    public GameObject createMyPlane(Resources resources) {
        return new MyPlane(resources);
    }

    /**
     * 生产我方的子弹
     * @param resources
     * @return
     */
    public GameObject createMyBlueBullet(Resources resources) {
        return new MyBlueBullet(resources);
    }
    public GameObject createMyPurpleBullet(Resources resources) {
        return new MyPurpleBullet(resources);
    }
    public GameObject createMyRedBullet(Resources resources) {
        return new MyRedBullet(resources);
    }

    /**
     * 生产BOSS的子弹
     * @param resources
     * @return
     */
   /* public GameObject createBossFlameBullet(Resources resources) {
        return new BossFlameBullet(resources);
    }

    public GameObject createBossSunBullet(Resources resources) {
        return new BossSunBullet(resources);
    }

    public GameObject createBossTriangleBullet(Resources resources) {
        return new BossTriangleBullet(resources);
    }

    public GameObject createBossGThunderBullet(Resources resources) {
        return new BossGThunderBullet(resources);
    }

    public GameObject createBossYHellfireBullet(Resources resources) {
        return new BossYHellfireBullet(resources);
    }

    public GameObject createBossRHellfireBullet(Resources resources) {
        return new BossRHellfireBullet(resources);
    }

    public GameObject createBossBulletDefault(Resources resources) {
        return new BossDefaultBullet(resources);
    }*/

    /**
     * 生产BigPlane的子弹
     * @param resources
     * @return
     */
    public GameObject createBigPlaneBullet(Resources resources) {
        return new BigPlaneBullet(resources);
    }

    /**
     * 生产导弹物品
     * @param resources
     * @return
     */
    public GameObject createMissileGoods(Resources resources) {
        return new MissileGoods(resources);
    }

    /**
     * 生产生命物品
     * @param resources
     * @return
     */
    public GameObject createLifeGoods(Resources resources) {
        return new LifeGoods(resources);
    }

    /**
     * 生产子弹物品
     * @param resources
     * @return
     */
    public GameObject createPurpleBulletGoods(Resources resources) {
        return new PurpleBulletGoods(resources);
    }
    public GameObject createRedBulletGoods(Resources resources) {
        return new RedBulletGoods(resources);
    }


}
