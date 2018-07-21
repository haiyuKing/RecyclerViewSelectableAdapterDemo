package com.why.project.recyclerviewselectableadapterdemo.adapter.base;

import com.why.project.recyclerviewselectableadapterdemo.bean.Photo;

/**
 * Created by HaiyuKing
 * Used 图片的选择监听事件
 */

public interface OnItemCheckListener {
	/***
	 *
	 * @param position 所选图片的位置
	 * @param photo     所选的图片
	 * @param selectedItemCount  已选数量
	 * @return enable check
	 */
	boolean onItemCheck(int position, Photo photo, int selectedItemCount);
}
