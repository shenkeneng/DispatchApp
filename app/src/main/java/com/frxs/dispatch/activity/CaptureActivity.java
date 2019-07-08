package com.frxs.dispatch.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.frxs.dispatch.R;
import com.google.zxing.Result;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * <pre>
 *     author : ewu
 *     e-mail : xxx@xx
 *     time   : 2017/06/07
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */
public class CaptureActivity extends MyBaseActivity implements ZXingScannerView.ResultHandler {
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.content_frame)
    FrameLayout contentFrame;
    private ZXingScannerView scanView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        ButterKnife.bind(this);

        titleTv.setText(R.string.scan);
        scanView = new ZXingScannerView(this);
        contentFrame.addView(scanView);
    }

    @Override
    public void onResume() {
        super.onResume();
        scanView.setResultHandler(this); // Register ourselves as a handler for scan results.
        scanView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        scanView.stopCamera();           // Stop camera on pause
    }


    @Override
    public void handleResult(Result result) {
        String resultTxt = result.getText();
        Snackbar.make(scanView, "Scan Result: " + resultTxt, Snackbar.LENGTH_LONG).show();
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            boolean gotoResultPage = bundle.getBoolean("to_result_page", false);
            if (gotoResultPage) {
                bundle.putString("Result", resultTxt);
//                gotoActivity(SearchResultRcvProductActivity.class, true, bundle);
                return;
            }
        }

        Intent intent = new Intent();
        intent.putExtra("Result", resultTxt);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @OnClick(R.id.action_back_tv)
    public void onClick() {
        finish();
    }
}
