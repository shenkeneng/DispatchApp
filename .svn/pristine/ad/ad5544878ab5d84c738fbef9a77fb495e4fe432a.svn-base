package com.frxs.dispatch.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.frxs.core.utils.CheckUtils;
import com.frxs.core.utils.MathUtils;
import com.frxs.core.utils.SystemUtils;
import com.frxs.dispatch.R;

/**
 * @author cate 2014-12-18 上午10:31:00
 */

public class CountEditText extends LinearLayout implements OnClickListener {

    private double mCount = 0.0;

    private EditText mEdit;

    private ImageView mMin;

    private ImageView mAdd;

    private onCountChangeListener mOnCountChangeListener;

    //    private int maxCount;
    private double maxCount;

    @SuppressLint("NewApi")
    public CountEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public CountEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CountEditText(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LinearLayout view = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.view_countedittext, null);
        mMin = (ImageView) view.findViewById(R.id.count_sub);
        mAdd = (ImageView) view.findViewById(R.id.count_add);
        mEdit = (EditText) view.findViewById(R.id.count_edit);
        mEdit.setText(MathUtils.doubleTrans(mCount));

        mMin.setOnClickListener(this);
        mAdd.setOnClickListener(this);
        this.addView(view);
        if (SystemUtils.getSDKVersion() < 16) {
            // mMin.setBackgroundDrawable(ViewUtils.getStateDrawable(getContext(),
            // normal, active, disable));
        } else {
            // mMin.setBackground(ViewUtils.getStateDrawable(getContext(),
            // normal, active, disable));
        }

        mEdit.addTextChangedListener(new TextWatcher() {
                                         @Override
                                         public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                         }

                                         @Override
                                         public void onTextChanged(CharSequence s, int start, int before, int count) {
                                             Log.i("WMTest", "onTextChanged  count = " + count);
                                         }

                                         @Override
                                         public void afterTextChanged(Editable s) {
                                             String value = s.toString();
                                             mEdit.setSelection(mEdit.getText().length());
                                             if (!TextUtils.isEmpty(value) && CheckUtils.strIsNumber(value)) {
                                                 mCount = Double.valueOf(s.toString());
                                             } else {
                                                 mCount = 1;
                                             }

                                             if (mOnCountChangeListener != null) {
                                                 mOnCountChangeListener.onCountEdit(mCount);
                                             }

                                             // 直接修改数量时判断当前减少数量的标识状态
                                             if (getCount() <= 0) {
                                                 mMin.setEnabled(false);
                                                 mMin.setImageResource(R.mipmap.icon_subtract_disable);
                                             } else {
                                                 mMin.setEnabled(true);
                                                 mMin.setImageResource(R.mipmap.icon_subtract_enable);
                                             }

                                             // 直接修改数量时判断当前增加数量的标识状态
                                             if (getCount() < getMaxCount()) {
                                                 mAdd.setEnabled(true);
                                                 mAdd.setImageResource(R.mipmap.icon_red_cross_enable);
                                             } else {
                                                 mAdd.setEnabled(false);
                                                 mAdd.setImageResource(R.mipmap.icon_red_cross_disable);
                                             }
                                         }
                                     }
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.count_sub: {
                if (getCount() > 0) {
                    mCount--;
                    if (mCount <= 0) {
                        mCount = 0.0;
                        mMin.setEnabled(false);
                        mMin.setImageResource(R.mipmap.icon_subtract_disable);
                    }
                    if (mCount < getMaxCount()) {
                        mAdd.setEnabled(true);
                        mAdd.setImageResource(R.mipmap.icon_red_cross_enable);
                    }
                    mEdit.setText(MathUtils.doubleTrans(mCount));
                    if (mOnCountChangeListener != null) {
                        mOnCountChangeListener.onCountSub(mCount);
                    }
                }
                break;
            }
            case R.id.count_add: {
                if (getCount() < maxCount) {
                    mCount++;
                    if (mCount > 0) {
                        mMin.setEnabled(true);
                        mMin.setImageResource(R.mipmap.icon_subtract_enable);
                    }
                    if (mCount == maxCount) {
                        mAdd.setEnabled(false);
                        mAdd.setImageResource(R.mipmap.icon_red_cross_disable);
                    }
                    mEdit.setText(MathUtils.doubleTrans(mCount));
                    if (mOnCountChangeListener != null) {
                        mOnCountChangeListener.onCountAdd(mCount);
                    }
                }
                break;
            }
            default:
                break;
        }
    }

    public onCountChangeListener getOnCountChangeListener() {
        return mOnCountChangeListener;
    }

    public void setOnCountChangeListener(onCountChangeListener mOnCountChangeListener) {
        this.mOnCountChangeListener = mOnCountChangeListener;
    }

    public double getCount() {
        return mCount;
    }

    public void setCount(double mCount) {
        this.mCount = mCount;
        if (mCount <= 0) {
            mCount = 0.0;
            mMin.setEnabled(false);
            mMin.setImageResource(R.mipmap.icon_subtract_disable);

            mEdit.setText(MathUtils.doubleTrans(mCount));
        } else if (0 < mCount && mCount < getMaxCount()) {
            mMin.setEnabled(true);
            mMin.setImageResource(R.mipmap.icon_subtract_enable);
            mAdd.setEnabled(true);
            mAdd.setImageResource(R.mipmap.icon_red_cross_enable);

            mEdit.setText(MathUtils.doubleTrans(mCount));
        } else {
            // 当前购物车数量与最大库存相等时显示最大库存，且“+”不可操作“-”号可操作|陈铁
            mEdit.setText(MathUtils.doubleTrans(maxCount));
            mMin.setEnabled(true);
            mMin.setImageResource(R.mipmap.icon_subtract_enable);
            mAdd.setEnabled(false);
            mAdd.setImageResource(R.mipmap.icon_red_cross_disable);
        }

    }

    public double getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
        mCount = 0;
        mEdit.setText(MathUtils.doubleTrans(mCount));
        mMin.setEnabled(false);
        mMin.setImageResource(R.mipmap.icon_subtract_disable);
        if (maxCount > 0) {
            mAdd.setEnabled(true);
            mAdd.setImageResource(R.mipmap.icon_red_cross_enable);
        } else {
            mAdd.setEnabled(false);
            mAdd.setImageResource(R.mipmap.icon_red_cross_disable);
        }

    }

    public interface onCountChangeListener {

        void onCountAdd(double count);

        void onCountSub(double count);

        void onCountEdit(double count);
    }

}
