package com.frxs.dispatch;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import com.allenliu.versionchecklib.core.http.HttpParams;
import com.allenliu.versionchecklib.core.http.HttpRequestMethod;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.ForceUpdateListener;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.frxs.core.utils.FileUtil;
import com.frxs.core.utils.JsonParser;
import com.frxs.core.utils.PrefUtils;
import com.frxs.core.utils.SerializableUtil;
import com.frxs.core.utils.SharedPreferencesHelper;
import com.frxs.core.utils.SystemUtils;
import com.frxs.core.utils.ToastUtils;
import com.frxs.core.widget.MaterialDialog;
import com.frxs.dispatch.activity.HomeActivity;
import com.frxs.dispatch.activity.MyBaseActivity;
import com.frxs.dispatch.comms.Config;
import com.frxs.dispatch.comms.GlobelDefines;
import com.frxs.dispatch.greendao.utils.DbCore;
import com.frxs.dispatch.model.AppVersionInfo;
import com.frxs.dispatch.model.GetDeliverList;
import com.frxs.dispatch.model.UserInfo;
import com.frxs.dispatch.rest.RestClient;
import com.frxs.dispatch.rest.model.ApiResponse;
import com.frxs.dispatch.rest.service.apkUpdate.DownloadService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;

/**
 * <pre>
 *     author : ewu
 *     e-mail : xxx@xx
 *     time   : 2017/03/29
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */
public class MyApplication extends Application {

    private static MyApplication mInstance;
    private static SparseArray<RestClient> restClientSparseArray = new SparseArray<RestClient>();
    private UserInfo mUserInfo;// 用户信息
    private List<GetDeliverList.DriversBean> deliverList = new ArrayList<>();
    private boolean needCheckUpgrade = true; // 是否需要检测更新
    private HomeActivity stHomeActivity;
    private Activity mActivity;
    private DownloadBuilder builder;

    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
    }

    public static MyApplication getInstance() {
        if (mInstance == null) {
            throw new IllegalStateException("Not yet initialized");
        }

        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (mInstance != null) {
            throw new IllegalStateException("Not a singleton");
        }

        mInstance = this;

        DbCore.init(this);

        initData();

        initRestClient();
    }

    public static RestClient getRestClient(int clientType) {
        return restClientSparseArray.get(clientType);
    }

    private void initRestClient() {
        int env = getEnvironment();
        restClientSparseArray.put(Config.TYPE_BASE, new RestClient(Config.getBaseUrl(Config.TYPE_BASE, env)));
        restClientSparseArray.put(Config.TYPE_UPDATE, new RestClient(Config.getBaseUrl(Config.TYPE_UPDATE, env)));
    }

    public void resetRestClient() {
        restClientSparseArray.clear();
        initRestClient();
    }

    private void initData() {
        // Get the user Info
        SharedPreferencesHelper helper = SharedPreferencesHelper.getInstance(this, Config.PREFS_NAME);
        String userStr = helper.getString(Config.KEY_USER, "");
        if (!TextUtils.isEmpty(userStr)) {
            Object object = null;
            try {
                object = SerializableUtil.str2Obj(userStr);
                if (null != object) {
                    mUserInfo = (UserInfo) object;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String deliverListStr = FileUtil.readInternalStoragePrivate(this, Config.DELIVERS_FILE_NAME);
        if (null != deliverListStr && deliverListStr.trim().length() > 0) {
            Type listType = new TypeToken<List<GetDeliverList.DriversBean>>() { }.getType();
            List<GetDeliverList.DriversBean> delivers = JsonParser.fromJson(deliverListStr, listType);
            if (null != delivers && delivers.size() > 0) {
                deliverList.addAll(delivers);
            }
        }
    }

    public void saveDeliverList(List<GetDeliverList.DriversBean> delivers) {
        deliverList.clear();

        if (null != delivers) {
            deliverList.addAll(delivers);
        }

        String fileContent = JsonParser.toJson(deliverList);
        FileUtil.writeInternalStoragePrivate(this, Config.DELIVERS_FILE_NAME, fileContent);
    }

    public List<GetDeliverList.DriversBean> getDeliverList() {
        return deliverList;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.mUserInfo = userInfo;

        String userStr = "";
        try {
            userStr = SerializableUtil.obj2Str(userInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SharedPreferencesHelper helper = SharedPreferencesHelper.getInstance(this, Config.PREFS_NAME);
        helper.putValue(Config.KEY_USER, userStr);
    }

    public UserInfo getUserInfo() {
        if (null == mUserInfo) {
            initData();
        }

        return mUserInfo;
    }

    public String getUserAccount() {
        SharedPreferencesHelper helper = SharedPreferencesHelper.getInstance(this, GlobelDefines.PREFS_NAME);
        return helper.getString(GlobelDefines.KEY_USER_ACCOUNT, "");
    }

    public void setUserAccount(String userAccount) {
        SharedPreferencesHelper helper = SharedPreferencesHelper.getInstance(this, GlobelDefines.PREFS_NAME);
        helper.putValue(GlobelDefines.KEY_USER_ACCOUNT, userAccount);
    }

    public void setEnvironment(int environmentId) {
        SharedPreferencesHelper helper = SharedPreferencesHelper.getInstance(this, GlobelDefines.PREFS_NAME);
        helper.putValue(GlobelDefines.KEY_ENVIRONMENT, environmentId);
    }

    public int getEnvironment() {
        SharedPreferencesHelper helper = SharedPreferencesHelper.getInstance(this, GlobelDefines.PREFS_NAME);
        return helper.getInt(GlobelDefines.KEY_ENVIRONMENT, Config.networkEnv);
    }

    public void logout() {
        // 清空用户信息
        setUserInfo(null);
        if (null != stHomeActivity) {
            stHomeActivity.finish();
            stHomeActivity = null;
        }

        deliverList.clear();
        PrefUtils.setValue(this, Config.KEY_SYNC_TIME, "");

        String fileContent = JsonParser.toJson(deliverList);
        FileUtil.writeInternalStoragePrivate(this, Config.DELIVERS_FILE_NAME, fileContent);
    }

    public HomeActivity getHomeActivity() {
        return stHomeActivity;
    }

    public void setHomeActivity(HomeActivity stHomeActivity) {
        this.stHomeActivity = stHomeActivity;
    }

    public void exitApp(int code) {
        System.exit(code);
    }

    public boolean isNeedCheckUpgrade() {
        return needCheckUpgrade;
    }

    /**
     * 更新版本的网路请求
     *
     * @param activity
     */
    public void prepare4Update(final Activity activity, final boolean isShow) {
        if (!SystemUtils.checkNet(this) || !SystemUtils.isNetworkAvailable(this)) {
            ToastUtils.show(this, "网络不可用");
            return;
        }
        if (needCheckUpgrade) {
            needCheckUpgrade = false;
        }

        mActivity = activity;
        ((MyBaseActivity) mActivity).showProgressDialog();
        String url = Config.getBaseUrl(Config.TYPE_UPDATE, getEnvironment()) + "AppVersion/AppVersionUpdateGet";
        HttpParams httpParams = new HttpParams();
        httpParams.put("SysType", 0); // 0:android;1:ios
        httpParams.put("AppType", 7); // 软件类型(0:兴盛店订货平台, 1:拣货APP. 2:兴盛店配送APP,3:装箱APP, 4:采购APP, 5:网络店订货平台,6：网络店配送APP,7:调度APP, 9退货库app)
        builder = AllenVersionChecker
                .getInstance()
                .requestVersion()
                .setRequestUrl(url)
                .setRequestMethod(HttpRequestMethod.POSTJSON)
                .setRequestParams(httpParams)
                .request(new RequestVersionListener() {
                    @Nullable
                    @Override
                    public UIData onRequestVersionSuccess(String result) {
                        ((MyBaseActivity) mActivity).dismissProgressDialog();
                        Type type = new TypeToken<ApiResponse<AppVersionInfo>>() {
                        }.getType();
                        ApiResponse<AppVersionInfo> respData = new Gson().fromJson(result, type);
                        int versionCode = Integer.valueOf(SystemUtils.getVersionCode(getApplicationContext()));
                        if (respData.getData() == null) {
                            ToastUtils.show(activity, "更新接口无数据");
                            return null;
                        }
                        if (versionCode >= respData.getData().getCurCode()) {
                            ToastUtils.show(activity, "已是最新版本");
                            return null;
                        }
                        if (respData.getData().getUpdateFlag() == 0) {
                            return null;
                        }
                        if (respData.getData().getUpdateFlag() == 2) {
                            builder.setForceUpdateListener(new ForceUpdateListener() {
                                @Override
                                public void onShouldForceUpdate() {
                                    forceUpdate();
                                }
                            });
                        }
                        return crateUIData(respData.getData().getDownUrl(), respData.getData().getUpdateRemark());
                    }

                    @Override
                    public void onRequestVersionFailure(String message) {
                        ((MyBaseActivity) mActivity).dismissProgressDialog();
                        ToastUtils.show(activity, "request failed");

                    }
                });
        builder.setShowNotification(true);
        builder.setShowDownloadingDialog(true);
        builder.setShowDownloadFailDialog(true);
        builder.setForceRedownload(true);
        builder.excuteMission(activity);
    }
    /**
     * @return
     * @important 使用请求版本功能，可以在这里设置downloadUrl
     * 这里可以构造UI需要显示的数据
     * UIData 内部是一个Bundle
     */
    private UIData crateUIData(String downloadUrl, String updateRemark) {
        UIData uiData = UIData.create();
        uiData.setTitle(getString(R.string.update_title));
        uiData.setDownloadUrl(downloadUrl);
        uiData.setContent(updateRemark);
        return uiData;
    }

    /**
     * 强制更新操作
     */
    private void forceUpdate() {
        mActivity.finish();
    }
    /**
     * 弹出更新的dialog
     *
     * @param activity
     * @param isForceUpdate 是否强制更新 true是，false否
     * @param downloadUrl   下载链接
     * @param curVersion    最新版本
     * @param updateRemark  更新内容
     * @description
     */
    private void showUpdateDialog(final Activity activity, final boolean isForceUpdate, final String downloadUrl, String curVersion, String updateRemark) {
        final MaterialDialog updateDialog = new MaterialDialog(activity);
        updateDialog.setTitle(R.string.update_title);
        updateDialog.setMessage(String.format(activity.getResources().getString(R.string.updade_content), curVersion,
                updateRemark));
        updateDialog.setCanceledOnTouchOutside(false);// 对话框外点击无效

        // 立即更新
        updateDialog.setPositiveButton("立即更新", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialog.dismiss();
                DownloadService downLoadService = new DownloadService(activity, downloadUrl, isForceUpdate);
                downLoadService.execute();
                if (!isForceUpdate) {
                    ToastUtils.showShortToast(activity, "程序在后台下载，请稍等...");
                }
            }
        });

        // 稍后更新（强制更新）:点击稍后更新也要更新
        updateDialog.setNegativeButton("稍后更新", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialog.dismiss();
                if (isForceUpdate) {
                    activity.finish();
                    System.exit(0);
                }
            }
        });
        updateDialog.show();
    }
}
