package com.frxs.dispatch.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import com.frxs.core.utils.SystemUtils;
import com.frxs.core.widget.MaterialDialog;
import com.frxs.dispatch.MyApplication;
import com.frxs.dispatch.R;
import com.frxs.dispatch.model.UserInfo;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <pre>
 *     author : ewu
 *     e-mail : xxx@xx
 *     time   : 2017/08/02
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */
public class MineActivity extends MyBaseActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.user_tv)
    TextView userTv;
    @BindView(R.id.tv_version_number)
    TextView tvVersionNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        ButterKnife.bind(this);

        iniData();
    }

    private void iniData() {
        titleTv.setText(R.string.title_mine);
        tvVersionNumber.setText(String.format(getString(R.string.version), SystemUtils.getAppVersion(MineActivity.this)));
        UserInfo userInfo = MyApplication.getInstance().getUserInfo();
        if (null != userInfo) {
            userTv.setText(userInfo.getEmpName() + "(" + userInfo.getUserAccount() + ")");
        }
    }

    @OnClick({R.id.action_back_tv, R.id.update_psw_view, R.id.check_version_view, R.id.logout_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_back_tv:
                finish();
                break;
            case R.id.update_psw_view:
                gotoActivity(UpdatePswActivity.class);
                break;
            case R.id.check_version_view:
                MyApplication.getInstance().prepare4Update(MineActivity.this, true);
                break;
            case R.id.logout_btn:
                loginOut();
                break;
            default:
                break;
        }
    }

    private void loginOut() {
        final MaterialDialog loginOutDialog = new MaterialDialog(MineActivity.this);
        loginOutDialog.setMessage(getString(R.string.exit_query));
        loginOutDialog.setPositiveButton(R.string.confirm, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().logout();
                MineActivity.this.finish();
                gotoActivity(LoginActivity.class);
                loginOutDialog.dismiss();
            }
        });

        loginOutDialog.setNegativeButton(R.string.cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginOutDialog.dismiss();
            }
        });
        loginOutDialog.show();
    }
}
