package com.example.dfh.planethunder.Object;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import com.example.dfh.planethunder.R;


/**
 * 红色弹药物品
 */
public class RedBulletGoods extends GameGoods {

	public RedBulletGoods(Resources resources) {
		super(resources);
	}

	protected void initBitmap() {
		bmp = BitmapFactory.decodeResource(resources, R.drawable.bullet_goods2);
		object_width = bmp.getWidth();			
		object_height = bmp.getHeight();	
	}
}
