package com.base.listeners;

import android.widget.AbsListView;

/**
 * ListView滚动监听器
 */
public interface Base_OnLVScrollListener {

	void onScroll (AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount);
}
