package com.why.project.recyclerviewselectableadapterdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.why.project.recyclerviewselectableadapterdemo.adapter.ChannelAdapter;
import com.why.project.recyclerviewselectableadapterdemo.adapter.base.OnItemCheckListener;
import com.why.project.recyclerviewselectableadapterdemo.bean.Photo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

	private RecyclerView mRecyclerView;
	private ArrayList<Photo> mPhotoArrayList;
	private ChannelAdapter mChannelAdapter;

	private int maxCount = 1;//如果值为1，代表单选；如果值>1，代表多选且设置了最大数目


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		initDatas();
		initEvents();

	}

	private void initViews() {
		mRecyclerView = findViewById(R.id.recycler_view);
	}

	private void initDatas() {
		//初始化集合
		mPhotoArrayList = new ArrayList<Photo>();
		for(int i=0; i<10;i++){
			Photo photo = new Photo();
			photo.setId("img"+i);
			int imgIndex = 1 + new Random().nextInt(3) % (1 + (3 - 1));
			photo.setPath("item_img" + imgIndex + ".jpg");

			mPhotoArrayList.add(photo);
		}

		//设置布局管理器
		GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
		mRecyclerView.setLayoutManager(gridLayoutManager);

		//设置适配器
		if(mChannelAdapter == null){
			//设置适配器
			mChannelAdapter = new ChannelAdapter(this, mPhotoArrayList);
			mRecyclerView.setAdapter(mChannelAdapter);
			//添加分割线
			//设置添加删除动画
			//调用ListView的setSelected(!ListView.isSelected())方法，这样就能及时刷新布局
			mRecyclerView.setSelected(true);
		}else{
			mChannelAdapter.notifyDataSetChanged();
		}
	}

	private void initEvents() {
		//列表适配器的点击监听事件
		mChannelAdapter.setOnItemClickLitener(new ChannelAdapter.OnItemClickLitener() {
			@Override
			public void onItemClick(View view, int position) {

			}

			@Override
			public void onItemLongClick(View view, int position) {

			}
		});

		//列表适配器的选择监听事件
		mChannelAdapter.setOnItemCheckListener(new OnItemCheckListener() {
			@Override
			public boolean onItemCheck(int position, Photo photo, int selectedItemCount) {
				//如果单选，那么选中的图片路径集合肯定只有一个
				if (maxCount <= 1) {
					List<Photo> selectedPhotos = mChannelAdapter.getSelectedPhotos();
					if (!mChannelAdapter.containsThisImg(photo,selectedPhotos)) {//如果点击的不是当前选中的图片，则取消选中状态，重新设置选中状态
						selectedPhotos.clear();//清空选中的图片集合
						mChannelAdapter.notifyDataSetChanged();
					}
					return true;
				}
				//如果多选，则特殊处理超过最大数目的情况
				if (selectedItemCount > maxCount) {
					Toast.makeText(MainActivity.this,String.format("最多可选择%1$d张图片",maxCount),Toast.LENGTH_SHORT).show();
					return false;//超过多选的最大数目，就不能选择了，所以需要返回false
				}
				return true;
			}
		});
	}
}
