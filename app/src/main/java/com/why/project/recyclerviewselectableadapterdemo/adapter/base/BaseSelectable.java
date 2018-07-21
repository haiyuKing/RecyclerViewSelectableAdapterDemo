package com.why.project.recyclerviewselectableadapterdemo.adapter.base;


import com.why.project.recyclerviewselectableadapterdemo.bean.Photo;

/**
 * Used 多选、单选图片的接口
 */

public interface BaseSelectable {

	/**
	 * Indicates if the item at position position is selected
	 *
	 * @param photo Photo of the item to check
	 * @return true if the item is selected, false otherwise
	 */
	boolean isSelected(Photo photo);

	/**
	 * Toggle the selection status of the item at a given position
	 *
	 * @param photo Photo of the item to toggle the selection status for
	 */
	void toggleSelection(Photo photo);

	/**
	 * Clear the selection status for all items
	 */
	void clearSelection();

	/**
	 * Count the selected items
	 *
	 * @return Selected items count
	 */
	int getSelectedItemCount();
}
