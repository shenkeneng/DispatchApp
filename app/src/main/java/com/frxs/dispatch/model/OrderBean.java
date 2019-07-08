package com.frxs.dispatch.model;

import java.io.Serializable;

/**
 * <pre>
 *     author : ewu
 *     e-mail : xxx@xx
 *     time   : 2017/08/30
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */
public class OrderBean implements Serializable {

    /**
     * LineName : 签约店周一周四
     * OrderDate : 0001-01-01 00:00:00
     * OrderId : 200000787167
     * OrderType : 1
     * PackingStatusStr : 大货区(△)
     * PayAmount : 0.0
     * SerialNumber : 1
     * SerialNumberName : 置顶
     * ShippingUserID : 1264
     * ShippingUserName : 匡月良
     * ShopCode : 90010462
     * ShopID : 896
     * ShopName : （90010462）都添超市
     * ShopSerialNumber : 1
     * StationNumber : 0
     * SubWCode : 1005
     * SubWID : 202
     * SubWName : 物流
     * WCode : 05
     * WID : 200
     * WName : 星沙仓库
     */

    private String LineName;
    private String OrderDate;
    private String OrderId;
    private int OrderType;
    private String PackingStatusStr;
    private double PayAmount;
    private int SerialNumber;
    private String SerialNumberName;
    private int ShippingUserID;
    private String ShippingUserName;
    private String ShopCode;
    private int ShopID;
    private String ShopName;
    private int ShopSerialNumber;
    private int StationNumber;
    private String SubWCode;
    private int SubWID;
    private String SubWName;
    private String WCode;
    private int WID;
    private String WName;

    public String getLineName() {
        return LineName;
    }

    public void setLineName(String LineName) {
        this.LineName = LineName;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String OrderDate) {
        this.OrderDate = OrderDate;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String OrderId) {
        this.OrderId = OrderId;
    }

    public int getOrderType() {
        return OrderType;
    }

    public void setOrderType(int OrderType) {
        this.OrderType = OrderType;
    }

    public String getPackingStatusStr() {
        return PackingStatusStr;
    }

    public void setPackingStatusStr(String PackingStatusStr) {
        this.PackingStatusStr = PackingStatusStr;
    }

    public double getPayAmount() {
        return PayAmount;
    }

    public void setPayAmount(double PayAmount) {
        this.PayAmount = PayAmount;
    }

    public int getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(int SerialNumber) {
        this.SerialNumber = SerialNumber;
    }

    public String getSerialNumberName() {
        return SerialNumberName;
    }

    public void setSerialNumberName(String SerialNumberName) {
        this.SerialNumberName = SerialNumberName;
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

    public String getShopCode() {
        return ShopCode;
    }

    public void setShopCode(String ShopCode) {
        this.ShopCode = ShopCode;
    }

    public int getShopID() {
        return ShopID;
    }

    public void setShopID(int ShopID) {
        this.ShopID = ShopID;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String ShopName) {
        this.ShopName = ShopName;
    }

    public int getShopSerialNumber() {
        return ShopSerialNumber;
    }

    public void setShopSerialNumber(int ShopSerialNumber) {
        this.ShopSerialNumber = ShopSerialNumber;
    }

    public int getStationNumber() {
        return StationNumber;
    }

    public void setStationNumber(int StationNumber) {
        this.StationNumber = StationNumber;
    }

    public String getSubWCode() {
        return SubWCode;
    }

    public void setSubWCode(String SubWCode) {
        this.SubWCode = SubWCode;
    }

    public int getSubWID() {
        return SubWID;
    }

    public void setSubWID(int SubWID) {
        this.SubWID = SubWID;
    }

    public String getSubWName() {
        return SubWName;
    }

    public void setSubWName(String SubWName) {
        this.SubWName = SubWName;
    }

    public String getWCode() {
        return WCode;
    }

    public void setWCode(String WCode) {
        this.WCode = WCode;
    }

    public int getWID() {
        return WID;
    }

    public void setWID(int WID) {
        this.WID = WID;
    }

    public String getWName() {
        return WName;
    }

    public void setWName(String WName) {
        this.WName = WName;
    }
}
