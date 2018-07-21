package com.why.project.recyclerviewselectableadapterdemo.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.why.project.recyclerviewselectableadapterdemo.bean.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Used 适配器基类，主要用于插入图片界面的适配器，用于多选、单选，以及切换选中状态等功能
 */

public abstract class BaseSelectableAdapter<VH extends RecyclerView.ViewHolder>
		extends RecyclerView.Adapter<VH> implements BaseSelectable {

	private static final String TAG = BaseSelectableAdapter.class.getSimpleName();

	/**上下文*/
	protected Context myContext;

	protected ArrayList<Photo> listitemList;//图片集合
	protected ArrayList<Photo> selectedPhotosPathList;//选中的图片路径集合

	public BaseSelectableAdapter() {
		listitemList = new ArrayList<Photo>();
		selectedPhotosPathList = new ArrayList<Photo>();
	}

	//当前图片是否选中
	@Override
	public boolean isSelected(Photo photo) {
		return containsThisImg(photo, getSelectedPhotos());
	}

	//更换图片的选中状态
	@Override
	public void toggleSelection(Photo photo) {
		if (containsThisImg(photo,selectedPhotosPathList)) {
			selectedPhotosPathList.remove(photo);
		} else {
			selectedPhotosPathList.add(photo);
		}
	}

	//清空所有图片的选中状态
	@Override
	public void clearSelection() {
		selectedPhotosPathList.clear();
	}

	//返回选中图片的数目
	@Override
	public int getSelectedItemCount() {
		return selectedPhotosPathList.size();
	}

	//返回选中图片路径的集合
	public List<Photo> getSelectedPhotos() {
		return selectedPhotosPathList;
	}

	//判断当前图片是否选中状态【根据id值进行判断，这里可以根据实际情况进行判断，比如id值、path路径，名称等】
	public boolean containsThisImg(Photo photo, List<Photo> photoList){
		boolean hasThisImg = false;
		for (Photo photo1 : photoList) {
			if (photo1.getId().equals(photo.getId())) {
				hasThisImg = true;
				break;
			}
		}
		return hasThisImg;
	}

	//返回当前图片集合的路径集合【用于预览，不是用来返回到上一个界面用的】
	public List<String> getCurrentPhotoPaths() {
		List<String> currentPhotoPaths = new ArrayList<String>(listitemList.size());
		for (Photo photo : listitemList) {
			currentPhotoPaths.add( photo.getPath());
		}
		return currentPhotoPaths;
	}

}
