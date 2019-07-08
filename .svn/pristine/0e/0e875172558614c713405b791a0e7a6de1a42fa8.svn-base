package com.frxs.dispatch.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.frxs.core.utils.ToastUtils;
import com.frxs.core.widget.EmptyView;
import com.frxs.core.widget.MaterialDialog;
import com.frxs.dispatch.R;
import com.frxs.dispatch.listener.InitUIListener;
import com.frxs.dispatch.model.OrderBean;
import com.frxs.dispatch.model.OrderInOrderList;
import com.frxs.dispatch.model.ShelfAreaBean;
import com.frxs.dispatch.rest.model.AjaxParams;
import com.frxs.dispatch.rest.model.ApiResponse;
import com.frxs.dispatch.rest.service.rxjava.BaseObserver;
import com.frxs.dispatch.rest.service.rxjava.RxSchedulers;
import com.ms.square.android.expandabletextview.ExpandableTextView;
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
 *     time   : 2017/08/05
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */
public class OrderInOrderActivity extends MyBaseActivity implements InitUIListener {

    private static final int ACTION_TOP = 3;
    private static final int ACTION_BOTTOM = 4;
    private static final int ACTION_PAUSE = 5;
    private static final int ACTION_NOMAL = 6;

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.action_right_tv)
    TextView actionRightTv;
    @BindView(R.id.search_type_sp)
    Spinner searchTypeSp;
    @BindView(R.id.search_content_et)
    EditText searchContentEt;
    @BindView(R.id.order_lv)
    ListView orderLv;
    @BindView(R.id.empty)
    EmptyView emptyView;
    @BindView(R.id.read_more_etv)
    ExpandableTextView readMoreEtv;
    @BindView(R.id.fab_switch_tab)
    FloatingActionButton floatingActionButton;
    private String[] searchTypeArray;
    private String searchContent;
    private Adapter adapter;
    private List<OrderBean> searchResultList = new ArrayList<>();
    private List<OrderBean> orderList = new ArrayList<>();
    private int showPosition = -1; //长按菜单的显示位置

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_in_order);
        ButterKnife.bind(this);

        initData();
    }

    @Override
    public void initData() {
        floatingActionButton.setVisibility(View.VISIBLE);
        actionRightTv.setVisibility(View.VISIBLE);
        titleTv.setText(R.string.order_in_order);
//        orderAreaSumTv.setText("不止公司应该有专属口号，任何一个组织，国家，甚至个人都应该有自己独有的口号或者标签。当年，邓爷爷提出的建设有中国特色的社会主义的口号，贯穿了我们成长的整个过程，一提到祖国，我们脑海里就会崩出这句话，瞬间自豪感爆棚。 ");
        searchTypeArray = getResources().getStringArray(R.array.order_in_order_search_type);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, searchTypeArray);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchTypeSp.setAdapter(arrayAdapter);
        searchTypeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getAdapter().getItem(position);
                if (!TextUtils.isEmpty(item) && null != searchTypeArray && position < searchTypeArray.length) {
                    searchContentEt.setText("");
                    searchContentEt.setHint(String.format(getString(R.string.search_hint), item));
                    if (item.equals(searchTypeArray[0])) {
                        searchContentEt.setInputType(InputType.TYPE_CLASS_NUMBER);
                    } else if (item.equals(searchTypeArray[1])){
                        searchContentEt.setInputType(InputType.TYPE_CLASS_NUMBER);
                    } else if (item.equals(searchTypeArray[2])) {
                        searchContentEt.setInputType(InputType.TYPE_CLASS_TEXT);
                    } else if (item.equals(searchTypeArray[3])) {
                        searchContentEt.setInputType(InputType.TYPE_CLASS_NUMBER);
                    }
                    doSearch();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchContentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchContent = s.toString();
                doSearch();
            }
        });

        adapter = new Adapter<OrderBean>(this, R.layout.item_order_in) {
            @Override
            protected void convert(AdapterHelper helper, final OrderBean item) {
                final boolean statusNormal = TextUtils.isEmpty(item.getSerialNumberName()) || item.getSerialNumberName().equals("正常");
                helper.setBackgroundColor(R.id.item_layout, statusNormal ? ContextCompat.getColor(OrderInOrderActivity.this, R.color.transparent) : ContextCompat.getColor(OrderInOrderActivity.this, R.color.yellow_light));

                if (showPosition == helper.getPosition()) {
                    final View menuView = helper.getView(R.id.menu_layout);
                    menuView.setVisibility(View.VISIBLE);
                    menuView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hideMenuLayout(menuView);
                        }
                    });

                    if (statusNormal) {
                        helper.setVisible(R.id.action_top_layout, View.VISIBLE);
                        helper.setVisible(R.id.action_bottom_layout, View.VISIBLE);
                        helper.setText(R.id.action_pause_view, R.string.action_pause);
                    } else {
                        helper.setVisible(R.id.action_top_layout, View.GONE);
                        helper.setVisible(R.id.action_bottom_layout, View.GONE);
                        helper.setText(R.id.action_pause_view, R.string.action_resume);
                    }

                    View.OnClickListener clickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showConfirmDialog(item, (TextView) v, menuView);
                        }
                    };

                    helper.setOnClickListener(R.id.action_top_view, clickListener);
                    helper.setOnClickListener(R.id.action_bottom_view, clickListener);
                    helper.setOnClickListener(R.id.action_pause_view, clickListener);
                } else {
                    helper.setVisible(R.id.menu_layout, View.GONE);
                }

                helper.setText(R.id.shop_name_tv, item.getShopName());
                helper.setText(R.id.order_status_tv, item.getSerialNumberName());
                helper.setText(R.id.order_id_tv, item.getOrderId());
                helper.setText(R.id.order_line_tv, String.format(getString(R.string.order_line), item.getLineName()));
                helper.setText(R.id.order_amount_tv, String.format(getString(R.string.order_amount), item.getPayAmount()));
                helper.setText(R.id.order_station_tv, String.format(getString(R.string.order_station), item.getStationNumber()));
                helper.setText(R.id.order_delivery_no_tv, String.valueOf(item.getShopSerialNumber()));
                helper.setText(R.id.order_area_status_tv, item.getPackingStatusStr());

                helper.setOnClickListener(R.id.order_delivery_no_tv, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showModifyDeliveryNoDialog(item.getOrderId(), String.valueOf(item.getShopSerialNumber()));
                    }
                });
            }

            private void hideMenuLayout(View menuLayout) {
                menuLayout.setVisibility(View.GONE);
                showPosition = -1;
            }

            private void showConfirmDialog(final OrderBean item, final TextView view, final View menuView) {
                final MaterialDialog dialog = new MaterialDialog(OrderInOrderActivity.this);
                final View contentView = LayoutInflater.from(OrderInOrderActivity.this).inflate(R.layout.dialog_confirm, null);
                dialog.setContentView(contentView);
                ((TextView) contentView.findViewById(R.id.confirm_info_tv)).setText(String.format(getString(R.string.confirm_4_order), view.getText().toString()));
                contentView.findViewById(R.id.confirm_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int changType;
                        if (view.getId() == R.id.action_top_view) {
                            changType = ACTION_TOP;
                        } else if (view.getId() == R.id.action_bottom_view) {
                            changType = ACTION_BOTTOM;
                        } else {
                            if (view.getText().toString().equals(getString(R.string.action_pause))) {
                                changType = ACTION_PAUSE;
                            } else {
                                changType = ACTION_NOMAL;
                            }
                        }
                        reqModifyDeliveryState(item.getOrderId(), changType);
                        hideMenuLayout(menuView);
                        dialog.dismiss();
                    }
                });
                contentView.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        };
        orderLv.setAdapter(adapter);

        orderLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showPosition = position;
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        orderLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrderBean orderBean = (OrderBean) adapter.get(position);
                if (orderBean != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("ORDER_ID", orderBean.getOrderId());
                    bundle.putString("SHOP_NAME", orderBean.getShopName());
                    bundle.putDouble("ORDER_AMT", orderBean.getPayAmount());
                    gotoActivity(OrderDetailsActivity.class, false, bundle);
                }
            }
        });

        orderLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // 这个地方主要处理选中item被滑出界面时隐藏按钮组的逻辑
                if (showPosition > (firstVisibleItem + visibleItemCount) || showPosition < firstVisibleItem) {
                    showPosition = -1;
                }
            }
        });

        reqOrderInOrderList();
    }

    private void showModifyDeliveryNoDialog(final String orderId, String serialNumber) {
        final MaterialDialog dialog = new MaterialDialog(this);
        final View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_modify_delivery_no, null);
        final EditText deliveryNoEt = (EditText) contentView.findViewById(R.id.delivery_no_et);
        deliveryNoEt.setText(serialNumber);
        dialog.setContentView(contentView);
        contentView.findViewById(R.id.confirm_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newNo = deliveryNoEt.getText().toString();
                if (!TextUtils.isEmpty(newNo)) {
                    reqModifyDeliveryNo(orderId, Integer.valueOf(newNo), dialog);
                }
            }
        });
        contentView.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void doSearch() {
        if (!TextUtils.isEmpty(searchContent)) {
            searchResultList.clear();
            int selectedPosition = searchTypeSp.getSelectedItemPosition();
            switch (selectedPosition) {
                case 0:  //门店编号
                    for (OrderBean item : orderList) {
                        String shopCode = item.getShopCode();
                        if (!TextUtils.isEmpty(shopCode) && shopCode.contains(searchContent)) {
                            searchResultList.add(item);
                        }
                    }
                    break;
                case 1: //订单编号
                    for (OrderBean item : orderList) {
                        String orderId = item.getOrderId();
                        if (!TextUtils.isEmpty(orderId) && orderId.contains(searchContent)) {
                            searchResultList.add(item);
                        }
                    }
                    break;
                case 2: //配送线路
                    for (OrderBean item : orderList) {
                        String deliveryLine = item.getLineName();
                        if (!TextUtils.isEmpty(deliveryLine) && deliveryLine.contains(searchContent)) {
                            searchResultList.add(item);
                        }
                    }
                    break;
                case 3: //待装区
                    for (OrderBean item : orderList) {
                        String stationNumber = String.valueOf(item.getStationNumber());
                        if (!TextUtils.isEmpty(stationNumber) && stationNumber.contains(String.valueOf(searchContent))) {
                            searchResultList.add(item);
                        }
                    }
                    break;
                default:
                    break;
            }

           setEmptyPage(searchResultList);
        } else {
            setEmptyPage(orderList);
        }
    }

    private void setEmptyPage(List<OrderBean> list) {
        if (list != null && list.size() > 0) {
            emptyView.setVisibility(View.GONE);
            adapter.replaceAll(list);
        } else {
            initEmptyView(EmptyView.MODE_NODATA);
        }
    }

    private void reqModifyDeliveryState(String orderId, int changType) {
        AjaxParams params = new AjaxParams();
        params.put("WID", getWID());
        params.put("UserId", getUserID());
        params.put("UserName", getUserName());
        params.put("OrderId", orderId);
        params.put("ChangeType", changType);

        getService().ChangeSaleOrderShopOrderState(params.getUrlParams())
                .compose(RxSchedulers.compose(this, true))
                .subscribe(new BaseObserver() {
                    @Override
                    public void onResponse(ApiResponse result) {
                        if (result.isSuccessful()) {
                            reqOrderInOrderList();
                        } else {
                            ToastUtils.show(OrderInOrderActivity.this, result.getInfo());
                        }
                    }
                });
    }

    private void reqModifyDeliveryNo(String orderId, int newNo, final MaterialDialog dialog) {
        AjaxParams params = new AjaxParams();
        params.put("WID", getWID());
        params.put("UserId", getUserID());
        params.put("UserName", getUserName());
        params.put("OrderId", orderId);
        params.put("ChangeType", newNo);

        getService().ChangeShopSerialNumber(params.getUrlParams())
                .compose(RxSchedulers.compose(this, true))
                .subscribe(new BaseObserver() {
                    @Override
                    public void onResponse(ApiResponse result) {
                        if (result.isSuccessful()) {
                            reqOrderInOrderList();
                            dialog.dismiss();
                        } else {
                            ToastUtils.show(OrderInOrderActivity.this, result.getInfo());
                        }
                    }
                });
    }

    private void reqOrderInOrderList() {
        AjaxParams params = new AjaxParams();
        params.put("WID", getWID());
        params.put("UserId", getUserID());
        params.put("UserName", getUserName());
//        params.put("ShopCode", shopCode);
//        params.put("OrderId", orderId);
//        params.put("LineID", lineID);
//        params.put("OrderStatus", orderStatus);

        getService().SaleOrderSendList(params.getUrlParams())
                .compose(RxSchedulers.compose(this, true))
                .subscribe(new BaseObserver<OrderInOrderList>() {
                    @Override
                    public void onResponse(ApiResponse<OrderInOrderList> result) {
                        if (result.isSuccessful()) {
                            OrderInOrderList orders = result.getData();
                            if (null != orders) {
                                showPosition = -1;
                                orderList = orders.getOrders();

                                List<ShelfAreaBean> shelfAreaStatistics = orders.getShelfArea();
                                if (null != shelfAreaStatistics) {
                                    StringBuilder statisticsSb = new StringBuilder();
                                    for (int i = 0; i < shelfAreaStatistics.size(); i++) {
                                        statisticsSb.append(shelfAreaStatistics.get(i).getShelfAreaName() + ":<font color=\"#e60012\">" +
                                                shelfAreaStatistics.get(i).getPackedOrderCount() + "/" + shelfAreaStatistics.get(i).getTotalOrderCount() + "</font>");
                                        statisticsSb.append(" \t\t");
                                    }
                                    readMoreEtv.setVisibility(View.VISIBLE);
                                    readMoreEtv.setText(Html.fromHtml(statisticsSb.toString()));
                                }
                                doSearch();
                            } else {
                                readMoreEtv.setVisibility(View.GONE);
                                initEmptyView(EmptyView.MODE_NODATA);
                            }
                        } else {
                            orderList.clear();
                            readMoreEtv.setVisibility(View.GONE);
                            initEmptyView(EmptyView.MODE_NODATA);
                            ToastUtils.show(OrderInOrderActivity.this, result.getInfo());
                        }
                    }
                });
    }

    @OnClick({R.id.action_right_tv, R.id.action_back_tv, R.id.fab_switch_tab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_back_tv:
                finish();
                break;
            case R.id.action_right_tv:
                reqOrderInOrderList();
                break;
            case R.id.fab_switch_tab:
                orderLv.setSelection(0);
                break;
            default:
                break;
        }
    }

    private void initEmptyView(int mode) {
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setMode(mode);
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reqOrderInOrderList();
            }
        });
    }
}
