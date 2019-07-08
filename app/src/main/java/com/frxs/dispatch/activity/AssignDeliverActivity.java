package com.frxs.dispatch.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import com.frxs.core.utils.StringUtil;
import com.frxs.core.utils.ToastUtils;
import com.frxs.core.widget.CustomPopWindow;
import com.frxs.core.widget.EmptyView;
import com.frxs.core.widget.MaterialDialog;
import com.frxs.dispatch.MyApplication;
import com.frxs.dispatch.R;
import com.frxs.dispatch.listener.InitUIListener;
import com.frxs.dispatch.listener.ParentUIListener;
import com.frxs.dispatch.model.AssignDeliverOrderBean;
import com.frxs.dispatch.model.AssignDeliverOrderList;
import com.frxs.dispatch.model.GetDeliverList;
import com.frxs.dispatch.rest.model.AjaxParams;
import com.frxs.dispatch.rest.model.ApiResponse;
import com.frxs.dispatch.rest.service.rxjava.BaseObserver;
import com.frxs.dispatch.rest.service.rxjava.RxSchedulers;
import com.frxs.dispatch.widget.BetterSpinner;
import com.google.gson.JsonObject;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <pre>
 *     author : ewu
 *     e-mail : xxx@xx
 *     time   : 2017/08/07
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */
public class AssignDeliverActivity extends MyBaseActivity implements InitUIListener {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.search_type_sp)
    Spinner searchTypeSp;
    @BindView(R.id.search_content_et)
    EditText searchContentEt;
    @BindView(R.id.order_lv)
    ListView orderLv;
    @BindView(R.id.filter_layout)
    LinearLayout filterLayout;
    @BindView(R.id.action_right_tv)
    TextView actionRightTv;
    @BindView(R.id.date_choose_tv)
    TextView dateChooseTv;
    @BindView(R.id.status_choose_tv)
    TextView statusChooseTv;
    @BindView(R.id.action_back_tv)
    TextView actionBackTv;
    @BindView(R.id.check_all_ck)
    CheckBox checkAllCk;
    @BindView(R.id.total_selected_order_num_tv)
    TextView totalSelectedOrderNumTv;
    @BindView(R.id.total_selected_order_amt_point_tv)
    TextView totalSelectedOrderAmtPointTv;
    @BindView(R.id.empty)
    EmptyView emptyView;
    private Adapter adapter;
    private String[] searchTypeArray;
    private ArrayList<String> dateFilterList;
    private ArrayList<String> statusFilterList;
    private int dateType = 0; // 请求订单时间 0：今天 1：昨天 2：前天 3：前七天
    private int statusType = 0; //订单类型 0：全部 1：未分配配送员 2：已分配配送员
    private List<AssignDeliverOrderBean> orderList = new ArrayList<>();
    private List<GetDeliverList.DriversBean> driversBeanList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_deliver);
        ButterKnife.bind(this);

        initData();
    }

    @Override
    public void initData() {
        titleTv.setText(R.string.assign_deliver);
        actionRightTv.setVisibility(View.VISIBLE);
        totalSelectedOrderNumTv.setText(String.format(getString(R.string.total_order), 0));
        totalSelectedOrderAmtPointTv.setText(String.format(getString(R.string.order_amount_point), 0.0, 0.0));

        dateFilterList = new ArrayList<>(Arrays.asList(getString(R.string.today), getString(R.string.yesterday), getString(R.string.the_day_before_yesterday),
                getString(R.string.date_all)));
        statusFilterList = new ArrayList<>(Arrays.asList(getString(R.string.all_order), getString(R.string.unassign_deliver_order), getString(R.string.assign_deliver_order)));

        searchTypeArray = getResources().getStringArray(R.array.assign_deliver_search_type);
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
                        searchContentEt.setInputType(InputType.TYPE_CLASS_TEXT);
                    } else if (item.equals(searchTypeArray[2])) {
                        searchContentEt.setInputType(InputType.TYPE_CLASS_TEXT);
                    } else if (item.equals(searchTypeArray[3])) {
                        searchContentEt.setInputType(InputType.TYPE_CLASS_NUMBER);
                    }
                    List<AssignDeliverOrderBean> resultOrderList = filterOrderList();
                    adapter.replaceAll(resultOrderList);
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
                doSearch();
            }
        });

        adapter = new Adapter<AssignDeliverOrderBean>(this, R.layout.item_assign_deliver_order) {
            @Override
            protected void convert(AdapterHelper helper, final AssignDeliverOrderBean item) {
                helper.setChecked(R.id.shop_name_tv, item.isSelected());
                helper.setOnClickListener(R.id.shop_name_tv, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isChecked = ((CheckBox)v).isChecked();
                        item.setSelected(isChecked);
                        updateSelectedOrderInfo();
                    }
                });

                helper.setText(R.id.order_date_cb, TextUtils.isEmpty(item.getConfDate()) ? "" : item.getConfDate().split(" ")[0]);
                helper.setText(R.id.shop_name_tv, item.getShopName());
                helper.setText(R.id.order_status_tv, item.getStatusName());
                helper.setText(R.id.order_line_tv, String.format(getString(R.string.order_line), item.getLineName()));
                helper.setText(R.id.order_amount_tv, String.format(getString(R.string.order_amount_point), item.getPayAmount(), item.getTotalPoint()));
                helper.setText(R.id.order_station_tv, String.format(getString(R.string.order_station), item.getStationNumber()));
                helper.setText(R.id.order_shop_address, Html.fromHtml("<u>"+ item.getAddress() +"</u>"));
                if (!TextUtils.isEmpty(item.getShippingUserName())) {
                    helper.setVisible(R.id.order_deliver_tv, View.VISIBLE);
                    helper.setText(R.id.order_deliver_tv, String.format(getString(R.string.deliver_info), item.getShippingUserName(), item.getShippingSerialNumber()));
                } else {
                    helper.setVisible(R.id.order_deliver_tv, View.GONE);
                }

                helper.setOnClickListener(R.id.ll_order, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item != null) {
                            Bundle bundle = new Bundle();
                            bundle.putString("FROM", "assign");
                            bundle.putString("ORDER_ID", item.getOrderId());
                            bundle.putString("SHOP_NAME", item.getShopName());
                            bundle.putDouble("ORDER_AMT", item.getPayAmount());
                            bundle.putDouble("ORDER_POINT", item.getTotalPoint());
                            gotoActivity(OrderDetailsActivity.class, false, bundle);
                        }
                    }
                });

                helper.setOnClickListener(R.id.order_shop_address, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showShopAddressDialog(item);
                    }
                });
            }
        };
        orderLv.setAdapter(adapter);

        reqPendingOrderList();
        driversBeanList = MyApplication.getInstance().getDeliverList();
        if (null == driversBeanList || driversBeanList.size() == 0) {
            reqGetDriver();
        }
    }

    private void updateSelectedOrderInfo(){
        List<AssignDeliverOrderBean> selectedOrderList = getSelectedOrderList();
        boolean allSelected;
        if (selectedOrderList.size() <= 0) {
            allSelected = false;
        } else if (selectedOrderList.size() < adapter.getAll().size()) {
            allSelected = false;
        } else {
            allSelected = true;
        }
        double orderAmt = 0.0;
        double orderPoint = 0.0;
        for (AssignDeliverOrderBean order : selectedOrderList) {
            orderAmt += order.getPayAmount();
            orderPoint += order.getTotalPoint();
        }
        totalSelectedOrderNumTv.setText(String.format(getString(R.string.total_order), selectedOrderList.size()));
        totalSelectedOrderAmtPointTv.setText(String.format(getString(R.string.order_amount_point), orderAmt, orderPoint));
        checkAllCk.setChecked(allSelected);
    }


    private void showShopAddressDialog(AssignDeliverOrderBean item) {
        final MaterialDialog dialog = new MaterialDialog(AssignDeliverActivity.this);
        final View contentView = LayoutInflater.from(AssignDeliverActivity.this).inflate(R.layout.dialog_confirm, null);
        dialog.setContentView(contentView);
        contentView.findViewById(R.id.confirm_info_tv).setVisibility(View.GONE);
        contentView.findViewById(R.id.ll_shop_address).setVisibility(View.VISIBLE);
        ((TextView)contentView.findViewById(R.id.order_id)).setText(item.getOrderId());
        final TextView mText = (TextView) contentView.findViewById(R.id.shop_address);
        mText.setText(item.getAddress());
        mText.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mText.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                final String newText = StringUtil.autoSplitText(mText);
                if (!TextUtils.isEmpty(newText)) {
                    mText.setText(newText);
                }
            }
        });

        contentView.findViewById(R.id.confirm_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        contentView.findViewById(R.id.cancel_btn).setVisibility(View.GONE);
        dialog.show();
    }

    private List<AssignDeliverOrderBean> getSelectedOrderList() {
        ArrayList<AssignDeliverOrderBean> srcOrderList = (ArrayList<AssignDeliverOrderBean>) adapter.getAll();
        List<AssignDeliverOrderBean> selectedOrderList = new ArrayList<AssignDeliverOrderBean>();
        for (AssignDeliverOrderBean item : srcOrderList) {
            if (item.isSelected()) {
                selectedOrderList.add(item);
            }
        }

        return selectedOrderList;
    }

    @OnClick({R.id.action_back_tv, R.id.action_right_tv, R.id.date_choose_layout, R.id.status_choose_layout, R.id.assign_deliver_btn, R.id.clear_deliver_btn,
            R.id.check_all_ck})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_back_tv:
                finish();
                break;
            case R.id.action_right_tv:
                reqPendingOrderList();
                break;
            case R.id.date_choose_layout:
                showDropDownPopWindow(dateFilterList, R.id.date_choose_layout);
                break;
            case R.id.status_choose_layout:
                showDropDownPopWindow(statusFilterList, R.id.status_choose_layout);
                break;
            case R.id.assign_deliver_btn: {
                List<AssignDeliverOrderBean> selectedOrderList = getSelectedOrderList();
                if (selectedOrderList.size() > 0) {
                   /* for (int i = 0; i < selectedOrderList.size(); i++) {
                        if (i >= 1) {
                            String currentDate = (TextUtils.isEmpty(selectedOrderList.get(i).getConfDate()) ? " " : selectedOrderList.get(i).getConfDate().split(" ")[0]);
                            String previewDate = (TextUtils.isEmpty(selectedOrderList.get(i - 1).getConfDate()) ? " " : selectedOrderList.get(i - 1).getConfDate().split(" ")[0]);
                            if (!previewDate.equals(currentDate)) {
                                ToastUtils.show(AssignDeliverActivity.this, "只能指派确认时间为同一天的订单");
                                return;
                            }
                        }
                    }*/
                    showAssignDeliverDialog();
                } else {
                    ToastUtils.show(AssignDeliverActivity.this, "请先选择需要指派配送员的订单");
                }
                break;
            }
            case R.id.clear_deliver_btn: {
                List<AssignDeliverOrderBean> selectedOrderList = getSelectedOrderList();
                if (selectedOrderList.size() > 0) {
                    for (AssignDeliverOrderBean item: selectedOrderList) {
                        if (TextUtils.isEmpty(item.getShippingUserName())) {
                            ToastUtils.show(AssignDeliverActivity.this, String.format(getString(R.string.tips_order_shipping_user_name), item.getOrderId()));
                            return;
                        }
                    }

                    showConfirmDialog();
                } else {
                    ToastUtils.show(AssignDeliverActivity.this, "请先选择需要清空配送员的订单");
                }
                break;
            }
            case R.id.check_all_ck:
                ArrayList<AssignDeliverOrderBean> srcOrderList = (ArrayList<AssignDeliverOrderBean>) adapter.getAll();
                for (AssignDeliverOrderBean item: srcOrderList) {
                    item.setSelected(checkAllCk.isChecked());
                }
                updateSelectedOrderInfo();
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    private void showConfirmDialog() {
        final MaterialDialog dialog = new MaterialDialog(AssignDeliverActivity.this);
        final View contentView = LayoutInflater.from(AssignDeliverActivity.this).inflate(R.layout.dialog_confirm, null);
        dialog.setContentView(contentView);
        ((TextView)contentView.findViewById(R.id.confirm_info_tv)).setText(getString(R.string.confirm_clear_deliver));
        contentView.findViewById(R.id.confirm_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reqClearDeliver();
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


    private void showDropDownPopWindow(ArrayList<String> itemList, final int responseViewId) {
        int spanCount;
        if (responseViewId == R.id.date_choose_layout) {
            spanCount = 4;
            dateChooseTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.navi_up, 0);
        } else {
            spanCount = 3;
            statusChooseTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.navi_up, 0);
        }
        View contentView = LayoutInflater.from(AssignDeliverActivity.this).inflate(R.layout.view_pop_down, null);
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));
        final CustomPopWindow filterPopWindow = new CustomPopWindow.PopupWindowBuilder(AssignDeliverActivity.this)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .enableBackgroundDark(true)
                .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        dateChooseTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.navi_down, 0);
                        statusChooseTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.navi_down, 0);
                    }
                })
                .create();
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter<String>(this, R.layout.item_filter_menu) {
            @Override
            protected void convert(RecyclerAdapterHelper helper, final String item) {
                final TextView itemTv = helper.getView(R.id.item_tv);
                itemTv.setSelected(false);
                int adapterPosition = helper.getAdapterPosition();
                if (responseViewId == R.id.date_choose_layout) {
                    if (dateType == adapterPosition) {
                        itemTv.setSelected(true);
                    }
                } else {
                    if (statusType == adapterPosition) {
                        itemTv.setSelected(true);
                    }
                }
                helper.setText(R.id.item_tv, item);
                helper.setOnClickListener(R.id.item_tv, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                filterPopWindow.dissmiss();
                                if (getString(R.string.today).equals(item)) {
                                    dateChooseTv.setText(item);
                                    dateType = 0;
                                    reqPendingOrderList();
                                } else if (getString(R.string.yesterday).equals(item)) {
                                    dateChooseTv.setText(item);
                                    dateType = 1;
                                    reqPendingOrderList();
                                } else if (getString(R.string.the_day_before_yesterday).equals(item)) {
                                    dateChooseTv.setText(item);
                                    dateType = 2;
                                    reqPendingOrderList();
                                } else if (getString(R.string.date_all).equals(item)) {
                                    dateChooseTv.setText(item);
                                    dateType = 3;
                                    reqPendingOrderList();
                                } else if ((getString(R.string.all_order).equals(item))) {
                                    statusChooseTv.setText(item);
                                    statusType = 0;
                                    doSearch();
                                } else if ((getString(R.string.unassign_deliver_order).equals(item))) {
                                    statusChooseTv.setText(item);
                                    statusType = 1;
                                    doSearch();
                                } else if ((getString(R.string.assign_deliver_order).equals(item))) {
                                    statusChooseTv.setText(item);
                                    statusType = 2;
                                    doSearch();
                                }
                            }
                        }
                );
            }
        };
        recyclerView.setAdapter(recyclerAdapter);

        recyclerAdapter.replaceAll(itemList);
        filterPopWindow.showAsDropDown(filterLayout, 0, 1);
    }

    private void doSearch() {
        List<AssignDeliverOrderBean> resultOrderList = filterOrderList();
        if (resultOrderList != null && resultOrderList.size() > 0) {
            emptyView.setVisibility(View.GONE);
            for (AssignDeliverOrderBean item : resultOrderList) {
                item.setSelected(false);
            }
            adapter.replaceAll(resultOrderList);
        } else {
            adapter.clear();
            initEmptyView(EmptyView.MODE_NODATA);
        }
        updateSelectedOrderInfo();
    }

    private List<AssignDeliverOrderBean> filterOrderList() {
        List<AssignDeliverOrderBean> resultOrderList = new ArrayList<AssignDeliverOrderBean>();
        List<AssignDeliverOrderBean> tempOrderList = new ArrayList<AssignDeliverOrderBean>();
        if (statusType == 0) {
            tempOrderList.addAll(orderList);
        } else {
            for (AssignDeliverOrderBean item: orderList) {
                if (2 == statusType && item.getShippingRecordID() != 0) {
                    tempOrderList.add(item);
                } else if (1 == statusType && item.getShippingRecordID() == 0){
                    tempOrderList.add(item);
                }
            }
        }

        String filterContent = searchContentEt.getText().toString().trim();
        if (!TextUtils.isEmpty(filterContent)) {
            int selectedPosition = searchTypeSp.getSelectedItemPosition();
            switch (selectedPosition) {
                case 0: //门店编号
                    for (AssignDeliverOrderBean item: tempOrderList) {
                        String shopCode = item.getShopCode();
                        if (!TextUtils.isEmpty(shopCode) && shopCode.contains(filterContent)) {
                            resultOrderList.add(item);
                        }
                    }
                    break;
                case 1: //配送线路
                    for (AssignDeliverOrderBean item: tempOrderList) {
                        String line = item.getLineName();
                        if (!TextUtils.isEmpty(line) && line.contains(filterContent)) {
                            resultOrderList.add(item);
                        }
                    }
                    break;
                case 2: //配送员
                    for (AssignDeliverOrderBean item: tempOrderList) {
                        String deliver = item.getShippingUserName();
                        if (!TextUtils.isEmpty(deliver) && deliver.contains(filterContent)) {
                            resultOrderList.add(item);
                        }
                    }
                    break;
                case 3: //订单编号
                    for (AssignDeliverOrderBean item: tempOrderList) {
                        String orderId = item.getOrderId();
                        if (!TextUtils.isEmpty(orderId) && orderId.contains(filterContent)) {
                            resultOrderList.add(item);
                        }
                    }
                    break;
                default:
                    break;
            }
        } else {
            resultOrderList.addAll(tempOrderList);
        }

        return resultOrderList;
    }

    private void reqPendingOrderList() {
        AjaxParams params = new AjaxParams();
        params.put("WID", getWID());
        params.put("UserId", getUserID());
        params.put("UserName", getUserName());
        params.put("DateType", dateType);

        getService().GetShipingOrders(params.getUrlParams())
                .compose(RxSchedulers.compose(this, true))
                .subscribe(new BaseObserver<AssignDeliverOrderList>() {
                    @Override
                    public void onResponse(ApiResponse<AssignDeliverOrderList> result) {
                        if (result.isSuccessful()) {
                            AssignDeliverOrderList orders = result.getData();
                            orderList = orders.getOrders();
                            doSearch();
                        } else {
                            orderList.clear();// 解决删除最后一个订单时，切换搜索条件订单列表未刷新的情况；
                            adapter.clear();// 解决切换切换条件后没有订单时，全选状态错误的问题；
                            initEmptyView(EmptyView.MODE_NODATA);
                            ToastUtils.show(AssignDeliverActivity.this, result.getInfo());
                        }
                        updateSelectedOrderInfo();
                    }
                });
    }

    private void reqAssignDeliver(GetDeliverList.DriversBean driver) {
        AjaxParams params = new AjaxParams();
        params.put("WID", getWID());
        params.put("UserId", getUserID());
        params.put("UserName", getUserName());
        params.put("SerialNumber", driver.getSerialNumber()); //车次号
        params.put("DriverID", driver.getEmpID()); //配送员ID
        params.put("DriverName", driver.getEmpName());
        params.put("ShippingOwnerType", driver.getShippingOwnerType()); //配送员类型
        params.put("OrderId", getSelectedOrderIds()); //订单号，多个用逗号分开
        params.put("ShippingRecordIDs", getSelectedShippingRecordIds());//配送记录id，多个用逗号分开

        getService().SetShipper(params.getUrlParams())
                .compose(RxSchedulers.compose(this, true))
                .subscribe(new BaseObserver() {
                    @Override
                    public void onResponse(ApiResponse result) {
                        if (result.isSuccessful()) {
                            ToastUtils.show(AssignDeliverActivity.this, "指派成功");
                            reqPendingOrderList();
                        } else {
                            ToastUtils.show(AssignDeliverActivity.this, result.getInfo());
                        }
                    }
                });
    }

    private void reqClearDeliver() {
        AjaxParams params = new AjaxParams();
        params.put("WID", getWID());
        params.put("UserId", getUserID());
        params.put("UserName", getUserName());
        params.put("OrderIds", getSelectedOrderIds()); //订单号，多个用逗号分开

        getService().ClearShipper(params.getUrlParams())
                .compose(RxSchedulers.compose(this, true))
                .subscribe(new BaseObserver() {
                    @Override
                    public void onResponse(ApiResponse result) {
                        if (result.isSuccessful()) {
                            ToastUtils.show(AssignDeliverActivity.this, "清空配送员成功");
                            reqPendingOrderList();
                        } else {
                            ToastUtils.show(AssignDeliverActivity.this, result.getInfo());
                        }
                    }
                });
    }

    private void reqGetShippingSerialNumber(GetDeliverList.DriversBean driverBean, final ParentUIListener listener) {
        AjaxParams params = new AjaxParams();
        params.put("WID", getWID());
        params.put("UserId", getUserID());
        params.put("UserName", getUserName());
        params.put("DriverID", driverBean.getEmpID());
        params.put("ConfDate", getSelectedOrderConfDates());

        getService().GetShippingSerialNumber(params.getUrlParams())
                .compose(RxSchedulers.compose(this, true))
                .subscribe(new BaseObserver<JsonObject>() {
                    @Override
                    public void onResponse(ApiResponse<JsonObject> result) {
                        if (result.isSuccessful()) {
                            JsonObject jsonData = result.getData();
                            if (null != jsonData) {
                                int shippingSerialNumber = jsonData.get("ShippingSerialNumber").getAsInt();
                                int isNoShippingCount = jsonData.get("IsNoShippingCount").getAsInt();// 0（配送完成）:显示N+1车   大于0（未配送完成）：显示N车 N+1车
                                Intent data = new Intent();
                                data.putExtra("ShippingSerialNumber", shippingSerialNumber);
                                data.putExtra("IsNoShippingCount", isNoShippingCount);
                                listener.onParentResult(data);
                            }
                        } else {
                            ToastUtils.show(AssignDeliverActivity.this, result.getInfo());
                        }
                    }
                });
    }

    private String getSelectedOrderIds() {
        List<AssignDeliverOrderBean> selectedOrderList = getSelectedOrderList();
        StringBuilder orderIdsBuilder = new StringBuilder();
        for (int i = 0; i < selectedOrderList.size(); i++) {
            if (i != selectedOrderList.size() - 1) {
                orderIdsBuilder.append(selectedOrderList.get(i).getOrderId() + ",");
            } else {
                orderIdsBuilder.append(selectedOrderList.get(i).getOrderId());
            }
        }

        return orderIdsBuilder.toString();
    }

    private String getSelectedOrderConfDates() {
        List<AssignDeliverOrderBean> selectedOrderList = getSelectedOrderList();
        String orderConfDatesBuilder = "";
        if (selectedOrderList.size() > 0) {
            orderConfDatesBuilder = selectedOrderList.get(0).getConfDate();
        }

        return orderConfDatesBuilder;
    }

    private String getSelectedShippingRecordIds() {
        List<AssignDeliverOrderBean> selectedOrderList = getSelectedOrderList();
        StringBuilder shippingRecordIdsBuilder = new StringBuilder();
        for (int i = 0; i < selectedOrderList.size(); i++) {
            if (i != selectedOrderList.size() - 1) {
                shippingRecordIdsBuilder.append(selectedOrderList.get(i).getShippingRecordID() + ",");
            } else {
                shippingRecordIdsBuilder.append(selectedOrderList.get(i).getShippingRecordID());
            }
        }

        return shippingRecordIdsBuilder.toString();
    }

    private void showAssignDeliverDialog() {
        final MaterialDialog dialog = new MaterialDialog(this);
        final View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_assign_deliver, null);
        final BetterSpinner deliverSp = (BetterSpinner) contentView.findViewById(R.id.deliver_sp);

        ArrayAdapter<GetDeliverList.DriversBean> adapter = new ArrayAdapter<GetDeliverList.DriversBean>(this,
                R.layout.simple_dropdown_item_line, driversBeanList);
        deliverSp.setAdapter(adapter);
        deliverSp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                deliverSp.onItemClick(parent, view, position, id);
                final GetDeliverList.DriversBean item = (GetDeliverList.DriversBean) parent.getAdapter().getItem(position);
                if (null != item && null != driversBeanList && position < driversBeanList.size()) {
                    reqGetShippingSerialNumber(item, new ParentUIListener() {
                        @Override
                        public void onParentResult(Intent data) {
                            // 0（配送完成）:显示N+1车   大于0（未配送完成）：显示N车 N+1车
                            final int shippingSerialNumber = data.getIntExtra("ShippingSerialNumber", 0);
                            int isNoShippingCount = data.getIntExtra("IsNoShippingCount", 0);
                            final TextView number1 = (TextView) contentView.findViewById(R.id.delivery_number1_tv);
                            number1.setVisibility(View.VISIBLE);
                            number1.setSelected(true);
                            final TextView number2 = (TextView) contentView.findViewById(R.id.delivery_number2_tv);
                            number2.setVisibility(View.GONE);
                            if (isNoShippingCount > 0) {
                                number1.setText(String.format(getString(R.string.key_delivery_order), shippingSerialNumber));
                                item.setSerialNumber(shippingSerialNumber);
                                number2.setVisibility(View.VISIBLE);
                                number2.setText(String.format(getString(R.string.key_delivery_order), shippingSerialNumber + 1));
                                number1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (!number1.isSelected()) {
                                            number1.setSelected(true);
                                            number2.setSelected(false);
                                            item.setSerialNumber(shippingSerialNumber);
                                        }
                                    }
                                });

                                number2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (!number2.isSelected()) {
                                            number2.setSelected(true);
                                            number1.setSelected(false);
                                            item.setSerialNumber(shippingSerialNumber + 1);
                                        }
                                    }
                                });
                            } else {
                                number1.setText(String.format(getString(R.string.key_delivery_order), shippingSerialNumber + 1));
                                item.setSerialNumber(shippingSerialNumber + 1);
                            }
                        }
                    });
                }
            }
        });

        dialog.setContentView(contentView);
        contentView.findViewById(R.id.confirm_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = deliverSp.getPosition();
                if (position < 0) {
                    ToastUtils.show(AssignDeliverActivity.this, "请选择配送员");
                    return;
                }
                if (position < driversBeanList.size()) {
                    reqAssignDeliver(driversBeanList.get(position));
                }
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

    private void initEmptyView(int mode) {
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setMode(mode);
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reqPendingOrderList();
            }
        });
    }
}
