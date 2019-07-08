package com.frxs.dispatch.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.frxs.core.utils.InputUtils;
import com.frxs.core.utils.ToastUtils;
import com.frxs.dispatch.MyApplication;
import com.frxs.dispatch.R;
import com.frxs.dispatch.model.UserInfo;
import com.frxs.dispatch.rest.model.AjaxParams;
import com.frxs.dispatch.rest.model.ApiResponse;
import com.frxs.dispatch.rest.service.rxjava.BaseObserver;
import com.frxs.dispatch.rest.service.rxjava.RxSchedulers;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <pre>
 *     author : ewu
 *     e-mail : xxx@xx
 *     time   : 2017/03/29
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */
public class UpdatePswActivity extends MyBaseActivity {


    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.old_password_et)
    EditText oldPasswordEt;
    @BindView(R.id.old_password_layout)
    TextInputLayout oldPasswordLayout;
    @BindView(R.id.new_pw_et)
    EditText newPwEt;
    @BindView(R.id.new_pw_layout)
    TextInputLayout newPwLayout;
    @BindView(R.id.repeat_new_pw_et)
    EditText repeatNewPwEt;
    @BindView(R.id.repeat_new_pw_layout)
    TextInputLayout repeatNewPwLayout;
    @BindView(R.id.confirm_btn)
    Button confirmBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_psw);
        ButterKnife.bind(this);

        iniData();
    }

    protected void iniData() {
        titleTv.setText(R.string.title_update_pwd);
    }

    private void reqUpdatePws(String oldPassword, String newPassword) {
        AjaxParams params = new AjaxParams();
        UserInfo userInfo = MyApplication.getInstance().getUserInfo();
        params.put("WID", getWID());
        params.put("UserType", 7);
        params.put("OldUserPwd", oldPassword);
        params.put("NewUserPwd", newPassword);
        params.put("UserAccount", userInfo.getUserAccount());
        getService().UpdatePwd(params.getUrlParams())
                .compose(RxSchedulers.compose(this, true))
                .subscribe(new BaseObserver() {
                    @Override
                    public void onResponse(ApiResponse result) {
                        if (result.isSuccessful()) {
                            ToastUtils.show(UpdatePswActivity.this, "修改密码成功");
                            finish();
                        } else {
                            ToastUtils.show(UpdatePswActivity.this, result.getInfo());
                        }
                    }
                });
    }

    private void actionSubmitNewPsw() {
        String oldPassword = oldPasswordEt.getText().toString();
        String newPassword = newPwEt.getText().toString();
        String repeatNewPassword = repeatNewPwEt.getText().toString();
        if (TextUtils.isEmpty(oldPassword)) {
            oldPasswordLayout.setError(getString(R.string.tips_null_password));
            oldPasswordEt.requestFocus();
        } else if (TextUtils.isEmpty(newPassword)) {
            newPwLayout.setError(getString(R.string.tips_null_password));
            newPwEt.requestFocus();
        } else {
            if (newPassword.equals(repeatNewPassword)) {
                if (InputUtils.isNumericOrLetter(newPassword)) {
                    oldPasswordLayout.setErrorEnabled(false);
                    newPwLayout.setErrorEnabled(false);
                    reqUpdatePws(oldPassword, newPassword);
                } else {
                    newPwLayout.setError(getString(R.string.tips_input_limit));
                    newPwEt.requestFocus();
                }
            } else {
                ToastUtils.show(this, getString(R.string.tips_new_password_error));
                newPwEt.requestFocus();
            }
        }
    }

    @OnClick({R.id.action_back_tv, R.id.confirm_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_back_tv:
                finish();
                break;
            case R.id.confirm_btn:
                actionSubmitNewPsw();
                break;
        }
    }
}
