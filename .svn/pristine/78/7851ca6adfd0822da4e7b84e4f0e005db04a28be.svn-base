package com.frxs.core.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.frxs.core.R;


public class SpecifiedProportionLinearLayout extends LinearLayout {

	private int mWeight = 0;

	public SpecifiedProportionLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray t = getContext().obtainStyledAttributes(attrs, R.styleable.Weight);
		mWeight = t.getInteger(R.styleable.Weight_weight, 1);
		t.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSpec = widthMeasureSpec;

		if (mWeight > 0) {
			int screenWidth = getScreenWidth();
			int width = screenWidth / mWeight;
			widthSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
		}

		super.onMeasure(widthSpec, heightMeasureSpec);
	}

	private int getScreenWidth() {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}
}
