package com.frxs.dispatch.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import com.frxs.core.utils.MathUtils;
import com.frxs.core.utils.ToastUtils;
import com.frxs.core.widget.EmptyView;
import com.frxs.dispatch.R;
import com.frxs.dispatch.listener.InitUIListener;
import com.frxs.dispatch.model.DeliveryStatistics;
import com.frxs.dispatch.rest.model.AjaxParams;
import com.frxs.dispatch.rest.model.ApiResponse;
import com.frxs.dispatch.rest.service.rxjava.BaseObserver;
import com.frxs.dispatch.rest.service.rxjava.RxSchedulers;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <pre>
 *     author : ewu
 *     e-mail : xxx@xx
 *     time   : 2017/08/25
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */
public class DeliveryStatisticsActivity extends MyBaseActivity implements InitUIListener {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.action_right_tv)
    TextView actionRightTv;
    @BindView(R.id.delivery_date_tv)
    TextView deliveryDateTv;
    @BindView(R.id.order_shop_num_tv)
    TextView orderShopNumTv;
    @BindView(R.id.no_order_shop_num_tv)
    TextView noOrderShopNumTv;
    @BindView(R.id.order_num_shop_type_tv)
    TextView orderNumShopTypeTv;
    @BindView(R.id.delivery_order_lv)
    ListView deliveryOrderLv;
    @BindView(R.id.check_all_ck)
    CheckBox checkAllCk;
    @BindView(R.id.total_selected_order_num_tv)
    TextView totalSelectedOrderNumTv;
    @BindView(R.id.total_shop_num_tv)
    TextView totalShopNumTv;
    @BindView(R.id.delivery_amount_tv)
    TextView deliveryAmountTv;
    @BindView(R.id.oos_amount_tv)
    TextView oosAmountTv;
    @BindView(R.id.empty)
    EmptyView emptyView;
    private Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_statistics);
        ButterKnife.bind(this);

        initData();
    }

    @Override
    public void initData() {
        titleTv.setText(R.string.delivery_statistics);
        actionRightTv.setVisibility(View.VISIBLE);

        adapter = new Adapter<DeliveryStatistics.OrdersBean>(this, R.layout.item_delivery_statistics) {
            @Override
            protected void convert(AdapterHelper helper, final DeliveryStatistics.OrdersBean item) {
                CheckBox selectCb = helper.getView(R.id.order_status_ck);
                selectCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        item.setSelected(isChecked);
                        List<DeliveryStatistics.OrdersBean> selectedRows = getSelectedRowList();
                        boolean allSelected;
                        if (selectedRows.size() < adapter.getAll().size()) {
                            allSelected = false;
                        } else {
                            allSelected = true;
                        }
                        totalSelectedOrderNumTv.setText(String.format(getString(R.string.total_order), selectedRows.size()));
                        checkAllCk.setChecked(allSelected);
                        initSelectedDataViews();
                    }
                });

                selectCb.setChecked(item.isSelected());
                helper.setText(R.id.order_status_ck, getStatusName(item.getStatus()));
                helper.setText(R.id.shop_num_tv, String.valueOf(item.getAmount()));
                helper.setText(R.id.order_amount_tv, MathUtils.doubleTrans(MathUtils.round(item.getBuyAmt(), 2)));
                helper.setText(R.id.delivery_amount_tv, MathUtils.doubleTrans(MathUtils.round(item.getSaleAmt(), 2)));
                helper.setText(R.id.oos_amount_tv, MathUtils.doubleTrans(MathUtils.round(item.getLackAmt(), 2)));
//                helper.setText(R.id.order_amount_tv, String.valueOf(MathUtils.round(item.getBuyAmt(), 2)));
//                helper.setText(R.id.delivery_amount_tv, String.valueOf(MathUtils.round(item.getSaleAmt(), 2)));
//                helper.setText(R.id.oos_amount_tv, String.valueOf(MathUtils.round(item.getLackAmt(), 2)));
            }

            private String getStatusName(String status) {
                String statusName = status;
                if ("0".equals(status)) {
                    statusName = "草稿"; //代客下单才有
                } else if ("1".equals(status)) {
                    statusName = "等待确认";
                } else if ("2".equals(status)) {
                    statusName = "等待拣货";
                } else if ("3".equals(status)) {
                    statusName = "正在拣货";
                } else if ("4".equals(status)) {
                    statusName = "等待装箱";
                } else if ("5".equals(status)) {
                    statusName = "等待配送";
                } else if ("6".equals(status)) {
                    statusName = "正在配送";
                } else if ("7".equals(status)) {
                    statusName = "交易完成";
                } else if ("8".equals(status)) {
                    statusName = "客户交易取消";
                } else if ("9".equals(status)) {
                    statusName = "客服交易关闭";
                }

                return statusName;
            }
        };

        deliveryOrderLv.setAdapter(adapter);

        initDeliveryStatisticsViews(null);
        initSelectedDataViews();

        reqDeliveryStatistics();
    }

    private List<DeliveryStatistics.OrdersBean> getSelectedRowList() {
        List<DeliveryStatistics.OrdersBean> selectedOrderList = new ArrayList<DeliveryStatistics.OrdersBean>();
        ArrayList<DeliveryStatistics.OrdersBean> srcOrderList = (ArrayList<DeliveryStatistics.OrdersBean>) adapter.getAll();
        for (DeliveryStatistics.OrdersBean item : srcOrderList) {
            if (item.isSelected()) {
                selectedOrderList.add(item);
            }
        }

        return selectedOrderList;
    }

    private void initDeliveryStatisticsViews(DeliveryStatistics statistics) {
        orderNumShopTypeTv.setText(statistics != null ? statistics.getDeclaration() : "");
        deliveryDateTv.setText(String.format(getString(R.string.delivery_date), null != statistics ? statistics.getReportDate().split(" ")[0] : ""));

        CharSequence orderShopNumStr;
        CharSequence noOrderShopNumStr;
        String sumOrderShopNum = null != statistics ? statistics.getTotalDeclaration() : "0/0";
        String noOrderShopNum = null != statistics ? String.valueOf(statistics.getNotDeclaration()) : "0";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            orderShopNumStr = Html.fromHtml(String.format(getString(R.string.sum_order_shop_num), sumOrderShopNum), Html.FROM_HTML_MODE_LEGACY);
            noOrderShopNumStr = Html.fromHtml(String.format(getString(R.string.no_order_shop_num), noOrderShopNum), Html.FROM_HTML_MODE_LEGACY);
        } else {
            orderShopNumStr = Html.fromHtml(String.format(getString(R.string.sum_order_shop_num), sumOrderShopNum));
            noOrderShopNumStr = Html.fromHtml(String.format(getString(R.string.no_order_shop_num), noOrderShopNum));
        }
        orderShopNumTv.setText(orderShopNumStr);
        noOrderShopNumTv.setText(noOrderShopNumStr);
    }

    private void initSelectedDataViews(){
        List<DeliveryStatistics.OrdersBean> selectedRows = getSelectedRowList();

        int totalShopNum = 0;
        double deliveryAmount = 0.0;
        double oosAmount = 0.0;
        for (DeliveryStatistics.OrdersBean item: selectedRows) {
            totalShopNum += item.getAmount();
            deliveryAmount = MathUtils.add(deliveryAmount, item.getSaleAmt());
            oosAmount = MathUtils.add(oosAmount, item.getLackAmt());
        }

        totalShopNumTv.setText(String.format(getString(R.string.total_shop_num), totalShopNum));
        deliveryAmountTv.setText(String.format(getString(R.string.delivery_amount), MathUtils.doubleTrans(MathUtils.round(deliveryAmount, 2))));
        oosAmountTv.setText(String.format(getString(R.string.oos_amount), MathUtils.doubleTrans(MathUtils.round(oosAmount, 2))));
    }

    @OnClick({R.id.action_back_tv, R.id.action_right_tv, R.id.check_all_ck})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_back_tv:
                finish();
                break;
            case R.id.action_right_tv:
                reqDeliveryStatistics();
                break;
            case R.id.check_all_ck:
                if (null != adapter && adapter.getCount() > 0) {
                    List<DeliveryStatistics.OrdersBean> dataList = adapter.getAll();
                    for (DeliveryStatistics.OrdersBean item : dataList) {
                        item.setSelected(checkAllCk.isChecked());
                    }
                    adapter.notifyDataSetChanged();
                    initSelectedDataViews();
                }
                break;
            default:
                break;
        }
    }

    private void reqDeliveryStatistics() {
        AjaxParams params = new AjaxParams();
        params.put("WID", getWID());
        params.put("UserId", getUserID());
        params.put("UserName", getUserName());
        params.put("ParentSubWarehouses", getSubWID());

        getService().SaleOrderDeliveryReport(params.getUrlParams())
                .compose(RxSchedulers.compose(this, true))
                .subscribe(new BaseObserver<DeliveryStatistics>() {
                    @Override
                    public void onResponse(ApiResponse<DeliveryStatistics> result) {
                        if (result.isSuccessful()) {
                            if (result.getData() != null) {
                                emptyView.setVisibility(View.GONE);
                                DeliveryStatistics deliveryStatistics = result.getData();
                                initDeliveryStatisticsViews(deliveryStatistics);
                                adapter.replaceAll(deliveryStatistics.getOrders());
                            } else {
                                initEmptyView(EmptyView.MODE_NODATA);
                            }
                        } else {
                            initEmptyView(EmptyView.MODE_NODATA);
                            ToastUtils.show(DeliveryStatisticsActivity.this, result.getInfo());
                        }
                    }
                });
    }

    private void initEmptyView(int mode) {
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setMode(mode);
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reqDeliveryStatistics();
            }
        });
    }
}
