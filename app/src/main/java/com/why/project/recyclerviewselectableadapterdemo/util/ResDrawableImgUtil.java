package com.why.project.recyclerviewselectableadapterdemo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.why.project.recyclerviewselectableadapterdemo.R;

import java.lang.reflect.Field;

/**
 * @Created HaiyuKing
 * @Used  根据图片名称获取resID值或者Bitmap对象
 * @参考资料 http://topmanopensource.iteye.com/blog/1600321
 * http://blog.csdn.net/wuzhipeng1991/article/details/17374561
 */
public class ResDrawableImgUtil {

	public final static String FILE_EXTENSION_SEPARATOR = ".";//文件扩展名分割器

	/**
	 * 根据图片名称获取图片的resID值（方案一）
	 * @param imgName 图片名称*/
	public static int getResourceIdByIdentifier(Context context, String imgName){

		//判断imgName是否含有后缀
		int extenPosi = imgName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		if(extenPosi != -1){
			imgName = imgName.substring(0, extenPosi);
		}

		int imgResourceId = -1;
		imgResourceId = context.getResources().getIdentifier(imgName, "drawable" , context.getPackageName());

		return imgResourceId;
	}

	/**
	 * 根据图片名称获取图片的resID值（方案二）
	 * @param imgName 图片名称*/
	public static int getResourceIdByReflect(String imgName){

		//判断imgName是否含有后缀
		int extenPosi = imgName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		if(extenPosi != -1){
			imgName = imgName.substring(0, extenPosi);
		}

		int imgResourceId = -1;
		Class drawable = R.drawable.class;
		Field field = null;
		try {
			field = drawable.getField(imgName);
			imgResourceId = field.getInt(field.getName());
		} catch (Exception e) {
		}
		return imgResourceId;
	}

	/**
	 * 根据图片的resID值获取图片名称
	 * @param imgResId 图片的resID值*/
	public static String getResourceName(Context context,int imgResId){
		String imgName = "";
		imgName = context.getResources().getResourceName(imgResId);

		return imgName;
	}

	/**
	 * 根据图片名称获取图片的Drawable
	 * @param imgName 图片名称*/
	public static Drawable getDrawableByImgName(Context context, String imgName){
		//int imgResourceId = R.drawable.ic_launcher;//Eclipse写法
		int imgResourceId = R.mipmap.ic_launcher;//Android Studio写法
		imgResourceId = getResourceIdByIdentifier(context,imgName);
		//解析资源文件夹下，id为resID的图片
		return ContextCompat.getDrawable(context,imgResourceId);
	}

	/**
	 * 根据图片名称获取图片的Bitmap
	 * @param imgName 图片名称*/
	public static Bitmap getBitmapByImgName(Context context, String imgName){
		//int imgResourceId = R.drawable.ic_launcher;//Eclipse写法
		int imgResourceId = R.mipmap.ic_launcher;//Android Studio写法
		imgResourceId = getResourceIdByIdentifier(context,imgName);
		//解析资源文件夹下，id为resID的图片
		return BitmapFactory.decodeResource(context.getResources(),imgResourceId);
	}

}