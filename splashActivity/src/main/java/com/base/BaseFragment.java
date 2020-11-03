package com.base;

import com.base.listeners.IBaseActivity;
import com.base.util.BaseCommTool;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public abstract class BaseFragment  extends Fragment implements IBaseActivity  {
	public int scW; //屏幕宽度
	public int scH; //屏幕高度
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		scW = BaseCommTool.getScreenWidth(getActivity());
		scH = BaseCommTool.getScreenHeight(getActivity());
	}
}
