package com.frxs.dispatch.model;

import java.util.List;

/**
 * Created by Chentie on 2017/9/5.
 */

public class TrachOrder {

    private int ShopCount;
    private double TotalAmt;
    private int TotalCount;
    private List<OrdersBean> Orders;

    public int getShopCount() {
        return ShopCount;
    }

    public void setShopCount(int ShopCount) {
        this.ShopCount = ShopCount;
    }

    public double getTotalAmt() {
        return TotalAmt;
    }

    public void setTotalAmt(double TotalAmt) {
        this.TotalAmt = TotalAmt;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int TotalCount) {
        this.TotalCount = TotalCount;
    }

    public List<OrdersBean> getOrders() {
        return Orders;
    }

    public void setOrders(List<OrdersBean> Orders) {
        this.Orders = Orders;
    }

    public static class OrdersBean {
        /**
         * OrderDate : 2017-09-05 08:56:30
         * OrderId : 200000787663
         * OrderType : 0
         * PayAmount : 616.9988
         * ShippingUserID : 0
         * ShippingUserName :
         * ShopCode : 888999
         * ShopID : 33167
         * ShopName : 涛哥的旗舰店
         * StationNumber : 134
         * Status : 2
         * StatusName : 等待拣货
         * SubWCode : 1005
         * SubWID : 202
         * SubWName : 物流
         * WCode : 200
         * WID : 200
         * WName : 星沙仓库
         */

        private String OrderDate;
        private String OrderId;
        private int OrderType;
        private double PayAmount;
        private int ShippingUserID;
        private String ShippingUserName;
        private String ShopCode;
        private int ShopID;
        private String ShopName;
        private int StationNumber;
        private int Status;
        private String StatusName;
        private String SubWCode;
        private int SubWID;
        private String SubWName;
        private String WCode;
        private int WID;
        private String WName;

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

        public double getPayAmount() {
            return PayAmount;
        }

        public void setPayAmount(double PayAmount) {
            this.PayAmount = PayAmount;
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

        public int getStationNumber() {
            return StationNumber;
        }

        public void setStationNumber(int StationNumber) {
            this.StationNumber = StationNumber;
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
}
