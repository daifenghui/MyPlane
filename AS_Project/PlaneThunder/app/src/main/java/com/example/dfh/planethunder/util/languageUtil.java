package com.example.dfh.planethunder.util;

import android.content.res.Configuration;
import android.util.DisplayMetrics;

import com.example.dfh.planethunder.Activity.MainActivity;
import com.example.dfh.planethunder.Activity.RecordActivity;

import java.util.Locale;

/**
 * Created by dfh on 19-8-29.
 */

public class languageUtil {

    /**
     * @param isEnglish true  ：点击英文，把中文设置未选中
     *                  false ：点击中文，把英文设置未选中
     */
    public  void set(boolean isEnglish) {

        Configuration configuration = MainActivity.instance.getResources().getConfiguration();
        DisplayMetrics displayMetrics = MainActivity.instance.getResources().getDisplayMetrics();
        if (isEnglish) {
            //设置英文
            configuration.locale = Locale.ENGLISH;
        } else {
            //设置中文
            configuration.locale = Locale.SIMPLIFIED_CHINESE;
        }
        //更新配置
        MainActivity.instance.getResources().updateConfiguration(configuration, displayMetrics);
        MainActivity.instance.recreate();
    }

}
