package com.example.dfh.planethunder.Interface;

import android.graphics.Canvas;

import com.example.dfh.planethunder.Plane.EnemyPlane;

import java.util.List;

/**
 * Created by dfh on 19-8-28.
 */

public interface IMyPlane {
    /**
     * 获取中间点的x坐标
     * @return
     */
    float getMiddle_x();

    /**
     * 设置中间点的x坐标
     * @param middle_x
     */
    void setMiddle_x(float middle_x);

    /**
     * 获取中间点的y坐标
     * @return
     */
    float getMiddle_y();

    /**
     * 设置中间点的y坐标
     * @param middle_y
     */
    void setMiddle_y(float middle_y);

    /**
     * 判断子弹是否改变状态
     * @return
     */
    boolean isChangeBullet();

    /**
     * 设置是否改变子弹类型
     * @param isChangeBullet
     */
    void setChangeBullet(boolean isChangeBullet);

    /**
     * 射击
     *
     * @param canvas
     * @param planes
     */
    void shoot(Canvas canvas, List<EnemyPlane> planes);

    /**
     * 初始化子弹
     */
    void initBullet();

    /**
     * 改变子弹类型
     *
     * @param type
     */
    void changeBullet(int type);
}
