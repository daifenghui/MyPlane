package com.example.dfh.planethunder.util;

/**
 * Created by dfh on 19-8-26.
 */

public class TimeUtil {
    /**
     * 根据毫秒返回时分秒
     * @param time
     * @return
     */
    public static String getFormatHMS(long time){
        time=time/1000;//总秒数
        int s= (int) (time%60);//秒
        int m= (int) (time/60);//分
        int h=(int) (time/3600);//时间
        return String.format("%02d:%02d:%02d",h,m,s);
    }

}
