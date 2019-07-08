package com.frxs.dispatch.model;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *     author : ewu
 *     e-mail : xxx@xx
 *     time   : 2017/09/02
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */
public class DeliveryStatistics implements Serializable {

    /**
     * TotalDeclaration : sample string 1
     * Declaration : sample string 2
     * ReportDate : 2017-09-02 11:55:15
     * NotDeclaration : 4
     * Orders : [{"ShopID":1,"Status":"sample string 2","Amount":3,"BuyAmt":4,"SaleAmt":5,"LackAmt":6},{"ShopID":1,"Status":"sample string 2","Amount":3,"BuyAmt":4,"SaleAmt":5,"LackAmt":6},{"ShopID":1,"Status":"sample string 2","Amount":3,"BuyAmt":4,"SaleAmt":5,"LackAmt":6}]
     */

    private String TotalDeclaration;
    private String Declaration;
    private String ReportDate;
    private int NotDeclaration;
    /**
     * ShopID : 1
     * Status : sample string 2
     * Amount : 3
     * BuyAmt : 4.0
     * SaleAmt : 5.0
     * LackAmt : 6.0
     */

    private List<OrdersBean> Orders;

    public String getTotalDeclaration() {
        return TotalDeclaration;
    }

    public void setTotalDeclaration(String TotalDeclaration) {
        this.TotalDeclaration = TotalDeclaration;
    }

    public String getDeclaration() {
        return Declaration;
    }

    public void setDeclaration(String Declaration) {
        this.Declaration = Declaration;
    }

    public String getReportDate() {
        return ReportDate;
    }

    public void setReportDate(String ReportDate) {
        this.ReportDate = ReportDate;
    }

    public int getNotDeclaration() {
        return NotDeclaration;
    }

    public void setNotDeclaration(int NotDeclaration) {
        this.NotDeclaration = NotDeclaration;
    }

    public List<OrdersBean> getOrders() {
        return Orders;
    }

    public void setOrders(List<OrdersBean> Orders) {
        this.Orders = Orders;
    }

    public static class OrdersBean {
        private int ShopID;
        private String Status;
        private int Amount;
        private double BuyAmt;
        private double SaleAmt;
        private double LackAmt;
        private boolean isSelected = false; //自定义字段 是否选中

        public int getShopID() {
            return ShopID;
        }

        public void setShopID(int ShopID) {
            this.ShopID = ShopID;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public int getAmount() {
            return Amount;
        }

        public void setAmount(int Amount) {
            this.Amount = Amount;
        }

        public double getBuyAmt() {
            return BuyAmt;
        }

        public void setBuyAmt(double BuyAmt) {
            this.BuyAmt = BuyAmt;
        }

        public double getSaleAmt() {
            return SaleAmt;
        }

        public void setSaleAmt(double SaleAmt) {
            this.SaleAmt = SaleAmt;
        }

        public double getLackAmt() {
            return LackAmt;
        }

        public void setLackAmt(double LackAmt) {
            this.LackAmt = LackAmt;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
