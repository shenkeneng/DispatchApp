package com.frxs.dispatch.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.frxs.core.utils.DateUtil;
import com.frxs.core.utils.MathUtils;
import com.frxs.core.utils.ToastUtils;
import com.frxs.core.widget.CustomPopWindow;
import com.frxs.core.widget.EmptyView;
import com.frxs.dispatch.R;
import com.frxs.dispatch.comms.GlobelDefines;
import com.frxs.dispatch.model.TrachOrder;
import com.frxs.dispatch.rest.model.AjaxParams;
import com.frxs.dispatch.rest.model.ApiResponse;
import com.frxs.dispatch.rest.service.rxjava.BaseObserver;
import com.frxs.dispatch.rest.service.rxjava.RxSchedulers;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shenpei on 2017/8/3.
 */

public class OrderTrackActivity extends MyBaseActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.action_right_tv)
    TextView refreshTv;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.order_lv)
    ListView orderLv;
    @BindView(R.id.filter_layout)
    LinearLayout filterLayout;
    @BindView(R.id.date_choose_tv)
    TextView dateChooseTv;
    @BindView(R.id.status_choose_tv)
    TextView chooseContextTv;
    @BindView(R.id.ll_assign_delivery)
    LinearLayout assignDeliveryLl;
    @BindView(R.id.tv_choose_status)
    TextView ChooseStatusTv;
    @BindView(R.id.tv_orders_info)
    TextView ordersInfoTv;
    @BindView(R.id.ll_order_track)
    LinearLayout orderTrackLl;
    @BindView(R.id.empty)
    EmptyView emptyView;
    private ArrayList<String> dateFilterList;
    private ArrayList<String> statusFilterList;
    private String AllOrders = "全部订单";
    private int PageIndex = 1;
    private int PageSize = 100;
    private int dataType = 0;// 时间筛选条件 初始化0：今天
    private String orderStatus = GlobelDefines.ORDER_TRACK_STATUS;// 状态筛选条件 初始化 2,3,4,5,6,7：等待拣货、正在拣货、等待装箱、等待配送、正在配送、交易完成
    private String chooseContext = AllOrders; // 订单状态栏显示文字内容
    private Adapter<TrachOrder.OrdersBean> orderAdatper;
    private TextView footerView;
    private String tempStatus;
    private String tempContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_deliver);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        titleTv.setText(R.string.order_track);
        orderTrackLl.setVisibility(View.VISIBLE);
        refreshTv.setVisibility(View.VISIBLE);
        ChooseStatusTv.setVisibility(View.GONE);
        llSearch.setVisibility(View.GONE);
        assignDeliveryLl.setVisibility(View.GONE);
        // 时间筛选内容
        dateFilterList = new ArrayList<>(Arrays.asList(getString(R.string.today), getString(R.string.yesterday), getString(R.string.the_day_before_yesterday), getString(R.string.in_the_first_seven_days)));
        // 状态筛选内容
        statusFilterList = new ArrayList<>(Arrays.asList(getString(R.string.wait_pick), getString(R.string.being_pick), getString(R.string.wait_check), getString(R.string.wait_delivery), getString(R.string.being_delivery), getString(R.string.finish)));
        // 初始化时间选择标题
        dateChooseTv.setText(dateFilterList.get(dataType));
        // 初始化状态选择标题
        chooseContextTv.setText(chooseContext);
        // 初始化订单信息栏
        ordersInfoTv.setText(String.format(getString(R.string.orders_info), 0, 0, "0"));
        orderAdatper = new Adapter<TrachOrder.OrdersBean>(this, R.layout.item_order_track) {
            @Override
            protected void convert(AdapterHelper helper, TrachOrder.OrdersBean item) {
                Date date = DateUtil.string2Date(item.getOrderDate(), "yyyy-MM-dd HH:mm");
                String orderDate = DateUtil.format(date, "yyyy-MM-dd HH:mm");
                helper.setText(R.id.tv_order_time, orderDate);
                helper.setText(R.id.tv_order_status, item.getStatusName());
                helper.setText(R.id.shop_name_tv, item.getShopName());
                helper.setText(R.id.order_id_tv, item.getOrderId());
                helper.setText(R.id.order_delivery_tv, String.format(getString(R.string.order_delivery), item.getShippingUserName()));
                helper.setText(R.id.order_amount_tv, String.format(getString(R.string.order_amount), item.getPayAmount()));
                helper.setText(R.id.order_station_tv, String.format(getString(R.string.order_station), item.getStationNumber()));
            }
        };
        orderLv.setAdapter(orderAdatper);
        reqDeliveryStatistics();
    }

    private void reqDeliveryStatistics() {
        showProgressDialog();
        AjaxParams params = new AjaxParams();
        params.put("WID", getWID());
        params.put("UserId", getUserID());
        params.put("UserName", getUserName());
        params.put("PageIndex", PageIndex);
        params.put("PageSize", PageSize);
        params.put("DateType", dataType);// DataType 0：今天1：昨天2：前天3：前七天
        String status = orderStatus;
        if (orderStatus.endsWith(",")) {// 去除最后的逗号
            status = orderStatus.substring(0, orderStatus.length() - 1);
        }
        params.put("OrderStatus", status); // OrderStatus 0:草稿(代客下单才有);1:等待确认;2:等待拣货;3:正在拣货;4:拣货完成/等待装箱;5:打印完成/等待配送;6:正在配送中;7:交易完成;8:客户交易取消;9:客服交易关闭
        getService().GetTraceOrders(params.getUrlParams())
                .compose(RxSchedulers.compose(this, true))
                .subscribe(new BaseObserver<TrachOrder>() {
                    @Override
                    public void onResponse(ApiResponse<TrachOrder> result) {
                        dismissProgressDialog();
                        if (result.isSuccessful()) {
                            if (result.getData() != null) {
                                TrachOrder ordersInfo = result.getData();
                                ordersInfoTv.setText(String.format(getString(R.string.orders_info), ordersInfo.getTotalCount(), ordersInfo.getShopCount(), MathUtils.twolittercountString(ordersInfo.getTotalAmt())));
                                List<TrachOrder.OrdersBean> orderLlist = ordersInfo.getOrders();
                                if (orderLlist != null) {
                                    emptyView.setVisibility(View.GONE);
                                    if (orderLlist.size() > 0) {
                                        if (PageIndex == 1) {
                                            orderAdatper.replaceAll(orderLlist);
                                            orderLv.setSelection(0);
                                        } else {
                                            orderAdatper.addAll(orderLlist);
                                        }
                                    }
                                } else {
                                    initEmptyView(EmptyView.MODE_NODATA);
                                }

                                boolean hasMoreItems = (orderAdatper.getCount() < ordersInfo.getTotalCount());
                                addFooterView(hasMoreItems);
                            }else {
                                if (PageIndex == 1) {
                                    initEmptyView(EmptyView.MODE_NODATA);
                                } else {
                                    ToastUtils.show(OrderTrackActivity.this, "已经是最后一页了");
                                }
                            }
                        } else {
                            initEmptyView(EmptyView.MODE_NODATA);
                            ToastUtils.show(OrderTrackActivity.this, result.getInfo());
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        super.onFailure(t);
                        dismissProgressDialog();
                        initEmptyView(EmptyView.MODE_ERROR);
                        ToastUtils.show(OrderTrackActivity.this, t.getMessage());
                    }
                });
    }

    /**
     * 添加、显示、隐藏脚布局
     */
    private void addFooterView(boolean isAdd) {
        if (footerView == null) {
            footerView = new TextView(OrderTrackActivity.this);
            footerView.setText("查看更多");
            footerView.setPadding(10, 10, 10, 10);
            footerView.setGravity(Gravity.CENTER);
            footerView.setBackgroundResource(R.drawable.shape_bg_item);
            orderLv.addFooterView(footerView);
        }

        if (isAdd && footerView != null) {
            footerView.setVisibility(View.VISIBLE);
        } else {
            footerView.setVisibility(View.GONE);
        }

        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageIndex += 1;
                reqDeliveryStatistics();
            }
        });

    }

    @OnClick({R.id.action_back_tv, R.id.date_choose_layout, R.id.status_choose_layout, R.id.action_right_tv})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.action_back_tv:
                finish();
                break;

            case R.id.date_choose_layout:
                showDropDownPopWindow(dateFilterList, R.id.date_choose_layout);
                break;

            case R.id.status_choose_layout:
                showDropDownPopWindow(statusFilterList, R.id.status_choose_layout);
                break;

            case R.id.action_right_tv:
                PageIndex = 1;
                reqDeliveryStatistics();
                break;
            default:
                break;
        }
    }

    private void showDropDownPopWindow(final ArrayList<String> itemList, final int responseViewId) {
        int spanCount;
        if (responseViewId == R.id.date_choose_layout) {
            spanCount = 4;
            dateChooseTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.navi_up, 0);
        } else {
            // 将当前选择状态赋给临时值
            tempContext = chooseContext;
            tempStatus = orderStatus;
            spanCount = 3;
            chooseContextTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.navi_up, 0);
        }
        final View contentView = LayoutInflater.from(OrderTrackActivity.this).inflate(R.layout.view_pop_down, null);
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));
        final CustomPopWindow filterPopWindow = new CustomPopWindow.PopupWindowBuilder(OrderTrackActivity.this)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .enableBackgroundDark(true)
                .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        dateChooseTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.navi_down, 0);
                        chooseContextTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.navi_down, 0);
                    }
                })
                .create();
        final RecyclerAdapter recyclerAdapter = new RecyclerAdapter<String>(this, R.layout.item_filter_menu) {
            @Override
            protected void convert(final RecyclerAdapterHelper helper, final String item) {
                final TextView itemTv = helper.getView(R.id.item_tv);
                itemTv.setSelected(false);
                if (responseViewId == R.id.date_choose_layout) {
                    int adapterPosition = helper.getAdapterPosition();
                    if (dataType == adapterPosition) {
                        itemTv.setSelected(true);
                    }
                } else {
                    if (tempContext.contains(item)) {
                        itemTv.setSelected(true);
                    }
                }
                helper.setText(R.id.item_tv, item);
                helper.setOnClickListener(R.id.item_tv, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (responseViewId == R.id.status_choose_layout) {
                                    if (tempContext.equals("全部订单")) {
                                        tempContext = "";
                                        tempStatus = "";
                                    }
                                }
                                if (getString(R.string.today).equals(item)) {
                                    dataType = 0;
                                } else if (getString(R.string.yesterday).equals(item)) {
                                    dataType = 1;
                                } else if (getString(R.string.the_day_before_yesterday).equals(item)) {
                                    dataType = 2;
                                } else if (getString(R.string.in_the_first_seven_days).equals(item)) {
                                    dataType = 3;
                                } else if ((getString(R.string.wait_pick).equals(item))) {
                                    tempContext = (tempStatus.contains("2")) ? tempContext.replace(getString(R.string.wait_pick) + ",", "") : tempContext + getString(R.string.wait_pick) + ",";
                                    tempStatus = (tempStatus.contains("2")) ? tempStatus.replace("2,", "") : tempStatus + "2,";
                                    itemTv.setSelected(tempStatus.contains("2") ? false : true);
                                } else if ((getString(R.string.being_pick).equals(item))) {
                                    tempContext = (tempStatus.contains("3")) ? tempContext.replace(getString(R.string.being_pick) + ",", "") : tempContext + getString(R.string.being_pick) + ",";
                                    tempStatus = (tempStatus.contains("3")) ? tempStatus.replace("3,", "") : tempStatus + "3,";
                                    itemTv.setSelected(tempStatus.contains("3") ? false : true);
                                } else if ((getString(R.string.wait_check).equals(item))) {
                                    tempContext = (tempStatus.contains("4")) ? tempContext.replace(getString(R.string.wait_check) + ",", "") : tempContext + getString(R.string.wait_check) + ",";
                                    tempStatus = (tempStatus.contains("4")) ? tempStatus.replace("4,", "") : tempStatus + "4,";
                                    itemTv.setSelected(tempStatus.contains("4") ? false : true);
                                } else if ((getString(R.string.wait_delivery).equals(item))) {
                                    tempContext = (tempStatus.contains("5")) ? tempContext.replace(getString(R.string.wait_delivery) + ",", "") : tempContext + getString(R.string.wait_delivery) + ",";
                                    tempStatus = (tempStatus.contains("5")) ? tempStatus.replace("5,", "") : tempStatus + "5,";
                                    itemTv.setSelected(tempStatus.contains("5") ? false : true);
                                } else if ((getString(R.string.being_delivery).equals(item))) {
                                    tempContext = (tempStatus.contains("6")) ? tempContext.replace(getString(R.string.being_delivery) + ",", "") : tempContext + getString(R.string.being_delivery) + ",";
                                    tempStatus = (tempStatus.contains("6")) ? tempStatus.replace("6,", "") : tempStatus + "6,";
                                    itemTv.setSelected(tempStatus.contains("6") ? false : true);
                                } else if ((getString(R.string.finish).equals(item))) {
                                    tempContext = (tempStatus.contains("7")) ? tempContext.replace(getString(R.string.finish) + ",", "") : tempContext + getString(R.string.finish) + ",";
                                    tempStatus = (tempStatus.contains("7")) ? tempStatus.replace("7,", "") : tempStatus + "7,";
                                    itemTv.setSelected(tempStatus.contains("7") ? false : true);
                                }

                                if (responseViewId == R.id.date_choose_layout) {
                                    filterPopWindow.dissmiss();
                                    dateChooseTv.setText(dateFilterList.get(dataType));// 设置最新时间条件
                                    PageIndex = 1;//更改条件后页码设置为1
                                    reqDeliveryStatistics();
                                }
                                notifyDataSetChanged();
                            }
                        }
                );
            }
        };
        if (responseViewId != R.id.date_choose_layout) {
            contentView.findViewById(R.id.ll_button).setVisibility(View.VISIBLE);
            contentView.findViewById(R.id.tv_reset).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tempContext = "全部订单";
                    recyclerAdapter.notifyDataSetChanged();
                }
            });
            contentView.findViewById(R.id.tv_affrim).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filterPopWindow.dissmiss();
                    orderStatus = tempStatus;
                    chooseContext = tempContext;
                    /**
                     * 未选择任何订单状态
                     */
                    if (chooseContext.equals("全部订单") || TextUtils.isEmpty(orderStatus)) {
                        orderStatus = GlobelDefines.ORDER_TRACK_STATUS;
                        chooseContext = "全部订单";
                        ChooseStatusTv.setVisibility(View.GONE);// 隐藏已选状态栏
                    } else {
                        ChooseStatusTv.setVisibility(View.VISIBLE);// 显示已选状态栏
                    }
                    /**
                     * 设置选择标题及选择状态栏内容
                     */
                    chooseContextTv.setText((!chooseContext.equals("全部订单") ? chooseContext.substring(0, 4) + "…" : chooseContext));
                    String context = chooseContext;
                    if (chooseContext.endsWith(",")) {// 去除最后的逗号
                        context = chooseContext.substring(0, chooseContext.length() - 1);
                    }
                    ChooseStatusTv.setText("已选：" + context);
                    PageIndex = 1;//更改条件后页码设置为1
                    reqDeliveryStatistics();
                }
            });
        }
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.replaceAll(itemList);
        filterPopWindow.showAsDropDown(filterLayout, 0, 1);
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
