package com.frxs.dispatch.model;

import java.io.Serializable;

/**
 * <pre>
 *     author : ewu
 *     e-mail : xxx@xx
 *     time   : 2017/08/31
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */
public class AssignDeliverOrderBean implements Serializable {

    /**
     * OrderId : sample string 1
     * WID : 2
     * SubWID : 1
     * ConfDate : 2017-08-31 17:40:42
     * WCode : sample string 4
     * WName : sample string 5
     * ShopID : 6
     * ShopCode : sample string 7
     * ShopName : sample string 8
     * Status : 9
     * StatusName : sample string 10
     * ShippingUserID : 1
     * ShippingUserName : sample string 11
     * PayAmount : 12.0
     * TotalPoint : 13.0
     * StationNumber : 1
     * LineName : sample string 14
     * ShippingSerialNumber : 1
     */

    private String OrderId;
    private int WID;
    private int SubWID;
    private String ConfDate;// 确认时间
    private String WCode;
    private String WName;
    private int ShopID;
    private String ShopCode;
    private String ShopName;
    private int Status;
    private String StatusName;
    private int ShippingUserID;
    private String ShippingUserName;
    private double PayAmount;
    private double TotalPoint;
    private int StationNumber;
    private String LineName;
    private int ShippingSerialNumber;
    private int ShippingRecordID;//配送记录ID
    private boolean isSelected = false; //自定义字段 是否选中
    private String Address;//门店地址

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String OrderId) {
        this.OrderId = OrderId;
    }

    public int getWID() {
        return WID;
    }

    public void setWID(int WID) {
        this.WID = WID;
    }

    public int getSubWID() {
        return SubWID;
    }

    public void setSubWID(int SubWID) {
        this.SubWID = SubWID;
    }

    public String getConfDate() {
        return ConfDate;
    }

    public void setConfDate(String OrderDate) {
        this.ConfDate = OrderDate;
    }

    public String getWCode() {
        return WCode;
    }

    public void setWCode(String WCode) {
        this.WCode = WCode;
    }

    public String getWName() {
        return WName;
    }

    public void setWName(String WName) {
        this.WName = WName;
    }

    public int getShopID() {
        return ShopID;
    }

    public void setShopID(int ShopID) {
        this.ShopID = ShopID;
    }

    public String getShopCode() {
        return ShopCode;
    }

    public void setShopCode(String ShopCode) {
        this.ShopCode = ShopCode;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String ShopName) {
        this.ShopName = ShopName;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String StatusName) {
        this.StatusName = StatusName;
    }

    public int getShippingUserID() {
        return ShippingUserID;
    }

    public void setShippingUserID(int ShippingUserID) {
        this.ShippingUserID = ShippingUserID;
    }

    public String getShippingUserName() {
        return ShippingUserName;
    }

    public void setShippingUserName(String ShippingUserName) {
        this.ShippingUserName = ShippingUserName;
    }

    public double getPayAmount() {
        return PayAmount;
    }

    public void setPayAmount(double PayAmount) {
        this.PayAmount = PayAmount;
    }

    public double getTotalPoint() {
        return TotalPoint;
    }

    public void setTotalPoint(double TotalPoint) {
        this.TotalPoint = TotalPoint;
    }

    public int getStationNumber() {
        return StationNumber;
    }

    public void setStationNumber(int StationNumber) {
        this.StationNumber = StationNumber;
    }

    public String getLineName() {
        return LineName;
    }

    public void setLineName(String LineName) {
        this.LineName = LineName;
    }

    public int getShippingSerialNumber() {
        return ShippingSerialNumber;
    }

    public void setShippingSerialNumber(int ShippingSerialNumber) {
        this.ShippingSerialNumber = ShippingSerialNumber;
    }

    public int getShippingRecordID() {
        return ShippingRecordID;
    }

    public void setShippingRecordID(int shippingRecordID) {
        ShippingRecordID = shippingRecordID;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
