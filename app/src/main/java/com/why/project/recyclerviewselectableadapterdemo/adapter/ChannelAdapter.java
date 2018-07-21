package com.why.project.recyclerviewselectableadapterdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.why.project.recyclerviewselectableadapterdemo.R;
import com.why.project.recyclerviewselectableadapterdemo.adapter.base.BaseSelectableAdapter;
import com.why.project.recyclerviewselectableadapterdemo.adapter.base.OnItemCheckListener;
import com.why.project.recyclerviewselectableadapterdemo.bean.Photo;
import com.why.project.recyclerviewselectableadapterdemo.util.ResDrawableImgUtil;

import java.util.ArrayList;

/**
 * Created by HaiyuKing
 * Used 列表适配器
 */

public class ChannelAdapter extends BaseSelectableAdapter<RecyclerView.ViewHolder> {

	/**上下文*/
	//private Context myContext;//因为BaseSelectableAdapter中定义了，所以此处需要注释掉
	/**频道集合*/
	//private ArrayList<Photo> listitemList;//因为BaseSelectableAdapter中定义了，所以此处需要注释掉

	/**
	 * 构造函数
	 */
	public ChannelAdapter(Context context, ArrayList<Photo> itemlist) {
		myContext = context;
		listitemList = itemlist;
	}

	/**
	 * 获取总的条目数
	 */
	@Override
	public int getItemCount() {
		return listitemList.size();
	}

	/**
	 * 创建ViewHolder
	 */
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(myContext).inflate(R.layout.channel_list_item, parent, false);
		ItemViewHolder itemViewHolder = new ItemViewHolder(view);
		return itemViewHolder;
	}

	/**
	 * 声明grid列表项ViewHolder*/
	static class ItemViewHolder extends RecyclerView.ViewHolder
	{
		public ItemViewHolder(View view)
		{
			super(view);

			listItemLayout = view.findViewById(R.id.griditemLayout);
			gridImg =  view.findViewById(R.id.pic_gridimage);
			choseImg = view.findViewById(R.id.pic_chose);
		}

		RelativeLayout listItemLayout;
		ImageView gridImg;
		ImageView choseImg;
	}

	/**
	 * 将数据绑定至ViewHolder
	 */
	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int index) {

		//判断属于列表项还是上拉加载区域
		if(viewHolder instanceof ItemViewHolder){
			final Photo photo = listitemList.get(index);
			final ItemViewHolder itemViewHold = ((ItemViewHolder)viewHolder);

			String imgPath = photo.getPath();
			Log.e("ChannelAdapter","imgPath="+imgPath);
			itemViewHold.gridImg.setImageResource(ResDrawableImgUtil.getResourceIdByIdentifier(myContext,imgPath));

			//如果设置了回调，则设置点击事件
			if (mOnItemClickLitener != null)
			{
				itemViewHold.listItemLayout.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						int position = itemViewHold.getLayoutPosition();//在增加数据或者减少数据时候，position和index就不一样了
						mOnItemClickLitener.onItemClick(itemViewHold.listItemLayout, position);
					}
				});
				//长按事件
				itemViewHold.listItemLayout.setOnLongClickListener(new View.OnLongClickListener() {
					@Override
					public boolean onLongClick(View view) {
						int position = itemViewHold.getLayoutPosition();//在增加数据或者减少数据时候，position和index就不一样了
						mOnItemClickLitener.onItemLongClick(itemViewHold.listItemLayout, position);
						return false;
					}
				});
			}

			//初始化列表项的是否选中状态
			boolean isChecked = isSelected(photo);
			if(isChecked) {//选中状态
				itemViewHold.choseImg.setImageResource(R.drawable.radio_img_selected);
			}else{//未选中状态
				itemViewHold.choseImg.setImageResource(R.drawable.radio_img_noselect);
			}

			//单选框图标的点击事件
			itemViewHold.choseImg.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					int position = itemViewHold.getLayoutPosition();//在增加数据或者减少数据时候，position和index就不一样了

					//注意，这里是根据isEnable判断是否可切换选择状态， 如果可以的话，就进行切换（通过toggleSelection方法添加/移除photo实体类），然后展现出来
					boolean isEnable = true;//默认可选择，而且是可选择任意数目
					if (mOnItemCheckListener != null) {//对于单选、多选（设置最大个数）的情况，是在onItemCheck中特殊处理的，并返回Boolean结果（超过多选的最大数目，就不能选择了，所以需要返回false）
						isEnable = mOnItemCheckListener.onItemCheck(position, photo,getSelectedPhotos().size() + (isSelected(photo) ? -1: 1));
					}
					if(isEnable){//如果可选择，则可切换选中状态
						toggleSelection(photo);//添加/移除到选择的集合中
						notifyItemChanged(position);
					}
				}
			});

		}
	}

	/**
	 * 添加Item--用于动画的展现*/
	public void addItem(int position,Photo listitemBean) {
		listitemList.add(position,listitemBean);
		notifyItemInserted(position);
	}
	/**
	 * 删除Item--用于动画的展现*/
	public void removeItem(int position) {
		listitemList.remove(position);
		notifyItemRemoved(position);
	}

	/*=====================添加OnItemClickListener回调================================*/
	public interface OnItemClickLitener
	{
		void onItemClick(View view, int position);
		void onItemLongClick(View view, int position);
	}

	private OnItemClickLitener mOnItemClickLitener;

	public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
	{
		this.mOnItemClickLitener = mOnItemClickLitener;
	}

	/*=====================添加OnItemCheckListener回调================================*/
	private OnItemCheckListener mOnItemCheckListener;

	public void setOnItemCheckListener(OnItemCheckListener mOnItemCheckListener)
	{
		this.mOnItemCheckListener = mOnItemCheckListener;
	}
}
