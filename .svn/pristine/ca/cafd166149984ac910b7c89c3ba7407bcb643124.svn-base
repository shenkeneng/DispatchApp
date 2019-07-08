package com.frxs.dispatch.rest.service;


import com.frxs.dispatch.model.AppVersionInfo;
import com.frxs.dispatch.model.AssignDeliverOrderList;
import com.frxs.dispatch.model.DeliveryStatistics;
import com.frxs.dispatch.model.GetDeliverList;
import com.frxs.dispatch.model.OrderDetails;
import com.frxs.dispatch.model.OrderInOrderList;
import com.frxs.dispatch.model.TrachOrder;
import com.frxs.dispatch.model.UserInfo;
import com.frxs.dispatch.model.WarehouseSysParam;
import com.frxs.dispatch.rest.model.ApiResponse;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService<T> {

//    // 登录
//    @FormUrlEncoded
//    @POST("Arrival/Login")
//    Call<ApiResponse<UserInfo>> UserLogin(@FieldMap Map<String, Object> params);

    //Rx登录
    @FormUrlEncoded
    @POST("Scheduler/Login")
    Observable<ApiResponse<UserInfo>> UserLogin(@FieldMap Map<String, Object> params);

    /**
     * Rx修改密码
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("Scheduler/UpdatePwd")
    Observable<ApiResponse> UpdatePwd(@FieldMap Map<String, Object> params);

    //Rx获取发货订单列表
    @FormUrlEncoded
    @POST("Scheduler/SaleOrderSendList")
    Observable<ApiResponse<OrderInOrderList>> SaleOrderSendList(@FieldMap Map<String, Object> params);

    //Rx修改发货顺序
    @FormUrlEncoded
    @POST("Scheduler/ChangeShopSerialNumber")
    Observable<ApiResponse> ChangeShopSerialNumber(@FieldMap Map<String, Object> params);

    //Rx修改发货状态（置顶、置底、暂停、恢复）
    @FormUrlEncoded
    @POST("Scheduler/ChangeSaleOrderShopOrderState")
    Observable<ApiResponse> ChangeSaleOrderShopOrderState(@FieldMap Map<String, Object> params);

    //Rx获取指派司机订单列表
    @FormUrlEncoded
    @POST("Scheduler/GetShipingOrders")
    Observable<ApiResponse<AssignDeliverOrderList>> GetShipingOrders(@FieldMap Map<String, Object> params);

    //Rx指派配送员
    @FormUrlEncoded
    @POST("Scheduler/SetShipper")
    Observable<ApiResponse> SetShipper(@FieldMap Map<String, Object> params);

    //Rx清空配送员
    @FormUrlEncoded
    @POST("Scheduler/ClearShipper")
    Observable<ApiResponse> ClearShipper(@FieldMap Map<String, Object> params);

    //Rx获取配送员
    @FormUrlEncoded
    @POST("Scheduler/GetDriver")
    Observable<ApiResponse<GetDeliverList>> GetDriver(@FieldMap Map<String, Object> params);

    //Rx获取指定配送员的车次信息
    @FormUrlEncoded
    @POST("Scheduler/GetShippingSerialNumber")
    Observable<ApiResponse<JsonObject>> GetShippingSerialNumber(@FieldMap Map<String, Object> params);

    //Rx今日发货统计
    @FormUrlEncoded
    @POST("Scheduler/SaleOrderDeliveryReport")
    Observable<ApiResponse<DeliveryStatistics>> SaleOrderDeliveryReport(@FieldMap Map<String, Object> params);

    //Rx订单跟踪
    @FormUrlEncoded
    @POST("Scheduler/GetTraceOrders")
    Observable<ApiResponse<TrachOrder>> GetTraceOrders(@FieldMap Map<String, Object> params);

    //Rx获取仓库系统参数
    @FormUrlEncoded
    @POST("Comm/GetWarehouseSysParams")
    Observable<ApiResponse<List<WarehouseSysParam>>> GetWarehouseSysParams(@FieldMap Map<String, Object> params);

    //版本更新
    @FormUrlEncoded
    @POST("AppVersion/AppVersionUpdateGet")
    Call<ApiResponse<AppVersionInfo>> AppVersionUpdateGet(@FieldMap Map<String, Object> params);

    //商品详情
    @FormUrlEncoded
    @POST("Scheduler/SaleOrderDetailList")
    Observable<ApiResponse<OrderDetails>> GetSaleOrderDetailList(@FieldMap Map<String, Object> params);
}
