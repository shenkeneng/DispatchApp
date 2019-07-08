package com.frxs.dispatch.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.frxs.core.utils.MathUtils;
import com.frxs.core.utils.ToastUtils;
import com.frxs.core.widget.EmptyView;
import com.frxs.dispatch.R;
import com.frxs.dispatch.model.OrderDetails;
import com.frxs.dispatch.rest.model.AjaxParams;
import com.frxs.dispatch.rest.model.ApiResponse;
import com.frxs.dispatch.rest.service.rxjava.BaseObserver;
import com.frxs.dispatch.rest.service.rxjava.RxSchedulers;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chentie on 2017/9/18.
 */

public class OrderDetailsActivity extends MyBaseActivity {

    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.empty)
    EmptyView emptyView;
    @BindView(R.id.order_lv)
    ListView orderLv;
    @BindView(R.id.shop_name_tv)
    TextView shopNameTv;
    @BindView(R.id.order_amt_tv)
    TextView orderAmtTv;
    @BindView(R.id.order_id_tv)
    TextView orderIdTv;
    private Adapter adapter;
    private String orderId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);

        initDate();
    }

    private void initDate() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String from = bundle.getString("FROM");
            orderId = bundle.getString("ORDER_ID");
            String shopName = bundle.getString("SHOP_NAME");
            double orderAmt = bundle.getDouble("ORDER_AMT");
            double orderPoint = bundle.getDouble("ORDER_POINT");

            shopNameTv.setText(TextUtils.isEmpty(shopName) ? "" : shopName);
            orderIdTv.setText(String.format(getString(R.string.order_no), (TextUtils.isEmpty(orderId) ? "" : orderId)));
            if (!TextUtils.isEmpty(from)) {
                orderAmtTv.setText(String.format(getString(R.string.order_amount_point), orderAmt, orderPoint));
            } else {
                orderAmtTv.setText(String.format(getString(R.string.order_amount), orderAmt));
            }
        }

        llSearch.setVisibility(View.GONE);
        titleTv.setText(R.string.good_details);
        emptyView.setVisibility(View.GONE);

        adapter = new Adapter<OrderDetails.SaleOrderDetailBean>(OrderDetailsActivity.this, R.layout.item_good) {
            @Override
            protected void convert(AdapterHelper helper, OrderDetails.SaleOrderDetailBean item) {
                helper.setText(R.id.tv_number, String.valueOf(helper.getPosition() + 1));
                // 设置商品名称
                helper.setText(R.id.tv_product_name, (TextUtils.isEmpty(item.getProductName()) ? "" : item.getProductName()));
                // 设置商品数量单位
                helper.setText(R.id.tv_goods_count, String.valueOf(item.getPreQty()) + item.getSaleUnit());
                // 设置商品编码
                helper.setText(R.id.tv_goods_code, String.format(getString(R.string.good_code), item.getSKU()));
                // 设置商品价格
                helper.setText(R.id.tv_goods_amt, String.format(getString(R.string.good_amt), MathUtils.twolittercountString(item.getSalePrice())));
            }
        };
        addFooterView();
        orderLv.setAdapter(adapter);
        GetOrderDetails();
    }

    @OnClick({R.id.action_back_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_back_tv:
                finish();
                break;
        }
        super.onClick(view);
    }

    private void GetOrderDetails() {
        if (TextUtils.isEmpty(orderId)) {
            ToastUtils.show(OrderDetailsActivity.this, "未获取到订单号");
            return;
        }
        showProgressDialog();
        AjaxParams params = new AjaxParams();
        params.put("WID", getWID());
        params.put("OrderId", orderId);
        getService().GetSaleOrderDetailList(params.getUrlParams())
                .compose(RxSchedulers.compose(this, true))
                .subscribe(new BaseObserver<OrderDetails>() {
                    @Override
                    public void onResponse(ApiResponse<OrderDetails> result) {
                        dismissProgressDialog();
                        if (result.isSuccessful()) {
                            OrderDetails data = result.getData();
                            if (data != null) {
                                if (data.getSaleOrderDetail() != null && data.getSaleOrderDetail().size() > 0) {
                                    adapter.replaceAll(data.getSaleOrderDetail());
                                }
                            }
                        } else {
                            ToastUtils.show(OrderDetailsActivity.this, result.getInfo());
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        super.onFailure(t);
                        dismissProgressDialog();
                        ToastUtils.show(OrderDetailsActivity.this, t.getMessage());
                    }
                });
    }

    /**
     * 添加、显示、隐藏脚布局
     */
    private void addFooterView() {
        TextView footerView = new TextView(OrderDetailsActivity.this);
        footerView.setText("到底部了~");
        footerView.setPadding(10, 10, 10, 10);
        footerView.setGravity(Gravity.CENTER);
        footerView.setBackgroundResource(R.drawable.shape_bg_item);
        orderLv.addFooterView(footerView);

    }
}
