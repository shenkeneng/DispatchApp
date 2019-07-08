package com.frxs.core.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment
{
	
	protected BaseActivity mActivity;
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(getLayoutId(), container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		initViews(view);
		initData();
	}

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		
		mActivity = (BaseActivity) activity;
	}

	protected abstract int getLayoutId();
	protected abstract void initViews(View view);
	protected abstract void initData();

}
