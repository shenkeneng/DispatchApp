package com.frxs.core.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;

import com.frxs.core.widget.MyProgressDialog;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * Created by ewu on 2016/2/18.
 */
public abstract class BaseActivity extends RxAppCompatActivity implements View.OnClickListener{

    private MyProgressDialog progressDialog;
    private int dialogCount = 0; //等待框计数， 大于0时显示，小于等于0不显示

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        progressDialog = new MyProgressDialog(this);
        progressDialog.setCancelable(false); //设置等待按钮不能手动取消
    }

//    protected abstract int getLayoutId();
//    protected abstract void initViews();
//    protected abstract void initEvent();
//    protected abstract void initData();

    /**
     * 统一初始化titlebar
     */
//    protected Toolbar initToolBar(String title) {
//        ImageView ivBack = (ImageView) findViewById(R.id.tool_bar_back);
//        TextView tvTitle = (TextView) findViewById(R.id.tv_toolbar_title);
//        tvTitle.setText(title);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
//        toolbar.setTitle("");
//        setSupportActionBar(toolbar);
//        toolbar.setContentInsetsRelative(10, 0);
//        ivBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        return toolbar;
//    }

    @Override
    public void onClick(View view) {

    }

    public void gotoActivity(Class<?> clz) {
        gotoActivity(clz, false, null);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity) {
        gotoActivity(clz, isCloseCurrentActivity, null);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity, Bundle ex) {
        Intent intent = new Intent(this, clz);
        if (ex != null) {
            intent.putExtras(ex);
        }
        startActivity(intent);
        if (isCloseCurrentActivity) {
            finish();
        }
    }

    public void gotoActivityForResult(Class<?> clz, boolean isCloseCurrentActivity, Bundle ex, int requestCode) {
        Intent intent = new Intent(this, clz);
        if (ex != null) {
            intent.putExtras(ex);
        }
        startActivityForResult(intent, requestCode);
        if (isCloseCurrentActivity) {
            finish();
        }
    }

    public boolean isShowingProgressDialog() {
        return progressDialog.isShowing();
    }

    public void showProgressDialog() {
        dialogCount++;
        if (!progressDialog.isShowing()) {
            progressDialog.showProgress();
        }
    }

    public void dismissProgressDialog() {
        dialogCount--;
        if (dialogCount <=  0) {
            dialogCount = 0;
            progressDialog.dismissProgress();
        }
    }
}
