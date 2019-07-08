package com.frxs.dispatch.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;

import com.frxs.core.base.BaseActivity;
import com.frxs.core.utils.DateUtil;
import com.frxs.core.utils.EasyPermissionsEx;
import com.frxs.core.utils.PrefUtils;
import com.frxs.core.utils.ToastUtils;
import com.frxs.core.widget.MaterialDialog;
import com.frxs.dispatch.MyApplication;
import com.frxs.dispatch.R;
import com.frxs.dispatch.comms.Config;
import com.frxs.dispatch.comms.GlobelDefines;
import com.frxs.dispatch.model.GetDeliverList;
import com.frxs.dispatch.model.UserInfo;
import com.frxs.dispatch.rest.model.AjaxParams;
import com.frxs.dispatch.rest.model.ApiResponse;
import com.frxs.dispatch.rest.service.ApiService;
import com.frxs.dispatch.rest.service.rxjava.BaseObserver;
import com.frxs.dispatch.rest.service.rxjava.RxSchedulers;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 *     author : ewu
 *     e-mail : xxx@xx
 *     time   : 2017/03/29
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */
public abstract class MyBaseActivity extends BaseActivity {
    protected ApiService mService;
    private static final int MY_PERMISSIONS_REQUEST_WES = 2;// 请求文件存储权限的标识码
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 3;// 请求相机权限的标识码
    private Bundle bundleThis;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(this instanceof SplashActivity)) {
            // 判断当前用户是否允许存储权限
            if (EasyPermissionsEx.hasPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
                // 允许 - 执行更新方法
                if (MyApplication.getInstance().isNeedCheckUpgrade()) {
                    MyApplication.getInstance().prepare4Update(this, false);
                }
            } else {
                // 不允许 - 弹窗提示用户是否允许放开权限
                EasyPermissionsEx.executePermissionsRequest(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WES);
            }
        }
    }

    public ApiService getService() {
        return MyApplication.getRestClient(Config.TYPE_BASE).getApiService();
    }

    public String getUserID() {
        UserInfo userInfo = MyApplication.getInstance().getUserInfo();
        if (null != userInfo) {
            return userInfo.getEmpID();
        } else {
            return "";
        }
    }

    public String getUserName() {
        UserInfo userInfo = MyApplication.getInstance().getUserInfo();
        if (null != userInfo) {
            return userInfo.getEmpName();
        } else {
            return "";
        }
    }

    public String getWID() {
        UserInfo userInfo = MyApplication.getInstance().getUserInfo();

        if (null != userInfo) {
            return String.valueOf(userInfo.getWareHouseWID());
        } else {
            return "";
        }
    }

    public String getSubWID() {
        UserInfo userInfo = MyApplication.getInstance().getUserInfo();

        if (null != userInfo) {
            return String.valueOf(userInfo.getSubWID());
        } else {
            return "";
        }
    }

    /**
     * 调用相机时判断是否有相机权限
     * @param bundle
     */
    public void hasCameraPermissions(Bundle bundle){
        this.bundleThis = bundle;
        // 判断当前用户是否允许相机权限
        if (EasyPermissionsEx.hasPermissions(this, new String[]{Manifest.permission.CAMERA})) {
            // 允许 - 调起相机
            gotoActivityForResult(CaptureActivity.class, false, bundleThis, GlobelDefines.REQ_CODE_SCAN);
        } else {
            // 不允许 - 弹窗提示用户是否允许放开权限
            EasyPermissionsEx.executePermissionsRequest(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }
    }

    /**
     * 请求用户是否放开权限的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WES: {// 版本更新存储权限
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // 已获取权限 继续运行应用
                        if (MyApplication.getInstance().isNeedCheckUpgrade()) {
                            MyApplication.getInstance().prepare4Update(this, false);
                        }
                    } else {
                        // 不允许放开权限后，提示用户可在去设置中跳转应用设置页面放开权限。
                        if (!EasyPermissionsEx.somePermissionPermanentlyDenied(this, permissions)) {
                            EasyPermissionsEx.goSettings2PermissionsDialog(this, "需要文件存储权限来下载更新的内容,但是该权限被禁止,你可以到设置中更改");
                        }
                    }
                break;
            }

            case MY_PERMISSIONS_REQUEST_CAMERA: {// 扫码相机权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 已获取权限 继续运行应用
                    gotoActivityForResult(CaptureActivity.class, false, bundleThis, GlobelDefines.REQ_CODE_SCAN);
                } else {
                    ToastUtils.show(MyBaseActivity.this, getString(R.string.hasCameraPermission));
                }
                break;
            }
        }
    }

    protected void reqGetDriver() {
        AjaxParams params = new AjaxParams();
        params.put("WID", getWID());
        params.put("UserId", getUserID());
        params.put("UserName", getUserName());

        getService().GetDriver(params.getUrlParams())
                .compose(RxSchedulers.compose(this, false))
                .subscribe(new BaseObserver<GetDeliverList>() {
                    @Override
                    public void onResponse(ApiResponse<GetDeliverList> result) {
                        if (result.isSuccessful()) {
                            GetDeliverList delivers = result.getData();
                            if (null != delivers) {
                                List<GetDeliverList.DriversBean> driversList = delivers.getDrivers();
                                if (null != driversList) {
                                    MyApplication.getInstance().saveDeliverList(driversList);
                                    PrefUtils.setValue(MyBaseActivity.this, Config.KEY_SYNC_TIME, DateUtil.format(new Date(), "yyyy/MM/dd"));
                                }
                            }
                        } else {
                            ToastUtils.show(MyBaseActivity.this, result.getInfo());
                        }
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (this instanceof LoginActivity || this instanceof HomeActivity) {
                showExitConfirmDialog();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showExitConfirmDialog() {
        final MaterialDialog materialDialog = new MaterialDialog(this);
        materialDialog.setMessage("是否退出应用？");
        materialDialog.setPositiveButton(R.string.confirm, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        materialDialog.dismiss();
                        finish();
                        System.exit(0);
                    }
                }
        );
        materialDialog.setNegativeButton(R.string.cancel, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDialog.dismiss();
            }
        });
        materialDialog.show();
    }

}
