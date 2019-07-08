package com.frxs.dispatch.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.frxs.core.utils.DateUtil;
import com.frxs.core.utils.PrefUtils;
import com.frxs.core.utils.ToastUtils;
import com.frxs.dispatch.MyApplication;
import com.frxs.dispatch.R;
import com.frxs.dispatch.comms.Config;
import com.frxs.dispatch.model.UserInfo;
import com.frxs.dispatch.model.WarehouseSysParam;
import com.frxs.dispatch.rest.model.AjaxParams;
import com.frxs.dispatch.rest.model.ApiResponse;
import com.frxs.dispatch.rest.service.rxjava.BaseObserver;
import com.frxs.dispatch.rest.service.rxjava.RxSchedulers;
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.frxs.core.utils.PrefUtils.getValue;

/**
 * <pre>
 *     author : ewu
 *     e-mail : xxx@xx
 *     time   : 2017/04/11
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */
public class HomeActivity extends MyBaseActivity {

    @BindView(R.id.module_enter_lv)
    RecyclerView moduleEnterLv;
    @BindView(R.id.user_tv)
    TextView userTv;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        MyApplication.getInstance().setHomeActivity(this);
        initData();
    }

    private void initData() {
        UserInfo userInfo = MyApplication.getInstance().getUserInfo();
        if (null != userInfo) {
            userTv.setText(String.format(getString(R.string.dispatcher), userInfo.getEmpName()));
        }
        moduleEnterLv.setHasFixedSize(true);
        moduleEnterLv.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new RecyclerAdapter<String>(this, R.layout.view_textview) {
            @Override
            protected void convert(RecyclerAdapterHelper helper, final String item) {
                TextView itemTv = helper.getView(R.id.item_tv);
                if (getString(R.string.order_in_order).equals(item)) {
                    itemTv.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.h_sort, 0, 0);
                } else if (getString(R.string.assign_deliver).equals(item)) {
                    itemTv.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.h_appoint, 0, 0);
                } else if (getString(R.string.order_track).equals(item)) {
                    itemTv.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.h_track, 0, 0);
                } else if ((getString(R.string.delivery_statistics).equals(item))) {
                    itemTv.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.h_statistics, 0, 0);
                }
                itemTv.setText(item);
                helper.setOnClickListener(R.id.item_tv, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getString(R.string.order_in_order).equals(item)) {
                            gotoActivity(OrderInOrderActivity.class);
                        } else if (getString(R.string.assign_deliver).equals(item)) {
                            gotoActivity(AssignDeliverActivity.class);
                        } else if (getString(R.string.order_track).equals(item)) {
                            gotoActivity(OrderTrackActivity.class);
                        } else if ((getString(R.string.delivery_statistics).equals(item))) {
                            gotoActivity(DeliveryStatisticsActivity.class);
                        }
                    }
                });
            }
        };
        moduleEnterLv.setAdapter(adapter);

        reqGetWarehouseSysParams();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //超过1天同步一次配送员数据
        String lastSyncTime = (String) getValue(this, Config.KEY_SYNC_TIME, "");
        int gapDays = 1;
        if (!TextUtils.isEmpty(lastSyncTime)) {
            gapDays = DateUtil.getGapDays(DateUtil.string2Date(lastSyncTime, "yyyy/MM/dd"), new Date());
        }
        if (gapDays >= 1) {
            reqGetDriver();
        }
    }

    private void initModuleViews() {
        String sweepLoadingMode = (String) getValue(HomeActivity.this, Config.KEY_SWEEP_LOADING_MODE, "0");
        ArrayList<String> itemList;
        if ("1".equals(sweepLoadingMode)) {
            itemList = new ArrayList<>(Arrays.asList(getString(R.string.order_in_order), getString(R.string.assign_deliver), getString(R.string.order_track), getString(R.string.delivery_statistics)));
        } else {
            itemList = new ArrayList<>(Arrays.asList(getString(R.string.order_in_order), getString(R.string.order_track), getString(R.string.delivery_statistics)));
        }

        adapter.replaceAll(itemList);
    }

    protected void reqGetWarehouseSysParams() {
        AjaxParams params = new AjaxParams();
        params.put("WID", getWID());
        params.put("ParamCode", WarehouseSysParam.SYS_SWEEP_LOADING_MODE);

        getService().GetWarehouseSysParams(params.getUrlParams())
                .compose(RxSchedulers.compose(this, true))
                .subscribe(new BaseObserver<List<WarehouseSysParam>>() {
                    @Override
                    public void onResponse(ApiResponse<List<WarehouseSysParam>> result) {
                        if (result.isSuccessful()) {
                            List<WarehouseSysParam> sysParamList = result.getData();
                            if (null != sysParamList) {
                                for (WarehouseSysParam param : sysParamList) {
                                    if (WarehouseSysParam.SYS_SWEEP_LOADING_MODE.equals(param.getParamCode())) {
                                        String sweepLoadingModeValue = param.getParamValue();
                                        PrefUtils.setValue(HomeActivity.this, Config.KEY_SWEEP_LOADING_MODE, sweepLoadingModeValue);
                                        initModuleViews();
                                        break;
                                    }
                                }
                            }
                        } else {
                            ToastUtils.show(HomeActivity.this, result.getInfo());
                        }
                    }
                });
    }

    @OnClick(R.id.person_zone_iv)
    public void onClick() {
        gotoActivity(MineActivity.class);
    }

}
