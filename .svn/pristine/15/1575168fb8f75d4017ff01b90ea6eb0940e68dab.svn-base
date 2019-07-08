package com.frxs.dispatch.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.frxs.core.utils.InputUtils;
import com.frxs.core.utils.ToastUtils;
import com.frxs.dispatch.MyApplication;
import com.frxs.dispatch.R;
import com.frxs.dispatch.comms.Config;
import com.frxs.dispatch.greendao.DBHelper;
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
public class LoginActivity extends MyBaseActivity {


    @BindView(R.id.user_name_et)
    EditText userNameEt;
    @BindView(R.id.user_name_layout)
    TextInputLayout userNameLayout;
    @BindView(R.id.password_et)
    EditText passwordEt;
    @BindView(R.id.password_layout)
    TextInputLayout passwordLayout;
    @BindView(R.id.login_btn)
    Button loginBtn;
    int keyDownNum = 0;
    private String[] environments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        iniData();
    }

    protected void iniData() {
        userNameLayout.setHint(getResources().getString(R.string.user_name));
        passwordLayout.setHint(getResources().getString(R.string.password));
        String account = MyApplication.getInstance().getUserAccount();
        if (!TextUtils.isEmpty(account)) {
            userNameEt.setText(account);
            userNameEt.setSelection(userNameEt.length());
        }

        initEnvironment();
    }

    private void initEnvironment() {
        environments = getResources().getStringArray(R.array.run_environments);
        for (int i = 0; i < environments.length; i++) {
            environments[i] = String.format(environments[i], Config.getBaseUrl(Config.TYPE_BASE,i));
        }
    }

    @OnClick({R.id.login_btn, R.id.select_environment})
    public void onClick(View view) {
        if (view.getId() == R.id.login_btn) {
            String username = userNameLayout.getEditText().getText().toString();
            String password = passwordLayout.getEditText().getText().toString();
            if (TextUtils.isEmpty(username)) {
                userNameLayout.setError(getString(R.string.tips_null_account));
                userNameEt.requestFocus();
            } else if (TextUtils.isEmpty(password)) {
                passwordLayout.setError(getString(R.string.tips_null_password));
                passwordEt.requestFocus();
            } else {
                if (InputUtils.isNumericOrLetter(password)) {
                    userNameLayout.setErrorEnabled(false);
                    passwordLayout.setErrorEnabled(false);
                    requestLogin(username, password);
                } else {
                    passwordLayout.setError(getString(R.string.tips_input_limit));
                    passwordEt.requestFocus();
                }
            }
        } else if (view.getId() == R.id.select_environment/* && (Config.networkEnv != 0)*/) {
            keyDownNum++;
            if (keyDownNum == 9) {
                ToastUtils.showLongToast(LoginActivity.this, "再点击1次进入环境选择模式");
            }
            if (keyDownNum == 10) {
                ToastUtils.showLongToast(LoginActivity.this, "已进入环境选择模式");
                AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                final int spEnv = MyApplication.getInstance().getEnvironment();
                String env = spEnv < environments.length ? environments[spEnv] : "";
                dialog.setTitle(getResources().getString(R.string.tips_environment, env));
                dialog.setCancelable(false);
                dialog.setItems(environments, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, final int which) {
                        if (spEnv == which) {
                            return;
                        }
                        if (which != 0) {
                            final AlertDialog verifyMasterDialog = new AlertDialog.Builder(LoginActivity.this).create();
                            View contentView = LayoutInflater.from(LoginActivity.this).inflate(R.layout.dialog_evironments, null);
                            final EditText pswEt = (EditText) contentView.findViewById(R.id.password_et);
                            contentView.findViewById(R.id.confirm_btn).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (TextUtils.isEmpty(pswEt.getText().toString().trim())) {
                                        ToastUtils.show(LoginActivity.this, "密码不能为空！");
                                        return;
                                    }

                                    if (!pswEt.getText().toString().trim().equals(getString(R.string.str_psw))) {
                                        ToastUtils.show(LoginActivity.this, "密码错误！");
                                        return;
                                    }
                                    DBHelper.getProductEntityService().deleteAll();
                                    MyApplication.getInstance().setEnvironment(which);//存储所选择环境
                                    MyApplication.getInstance().resetRestClient();
                                    verifyMasterDialog.dismiss();
                                }
                            });

                            contentView.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    verifyMasterDialog.dismiss();
                                }
                            });
                            verifyMasterDialog.setView(contentView);
                            verifyMasterDialog.show();

                        } else {
                            DBHelper.getProductEntityService().deleteAll();
                            MyApplication.getInstance().setEnvironment(which);//存储所选择环境
                            MyApplication.getInstance().resetRestClient();
                        }

                    }
                });
                dialog.setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                dialog.show();
                keyDownNum = 0;
            }

        }
    }

    private void requestLogin(final String strUserName, String strPassWord) {
        AjaxParams params = new AjaxParams();
        params.put("UserAccount", strUserName);
        params.put("UserPwd", strPassWord);
        params.put("UserType", "7");// 7 调度

        getService().UserLogin(params.getUrlParams())
                .compose(RxSchedulers.compose(this, true))
                .subscribe(new BaseObserver<UserInfo>() {
                    @Override
                    public void onResponse(ApiResponse<UserInfo> result) {
                        if (result.getFlag().equals("0")) {
                            ToastUtils.show(LoginActivity.this, "登录成功");
                            MyApplication application = MyApplication.getInstance();
                            application.setUserAccount(strUserName);

                            UserInfo userInfo = result.getData();
                            if (null != userInfo) {
                                application.setUserInfo(userInfo);

                                gotoActivity(HomeActivity.class, true);
                            }
                        } else {
                            ToastUtils.show(LoginActivity.this, result.getInfo());
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        super.onFailure(t);
                    }
                });
    }
}
