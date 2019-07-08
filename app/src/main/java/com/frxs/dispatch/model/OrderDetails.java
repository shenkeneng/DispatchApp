package com.frxs.dispatch.model;

import java.util.List;

/**
 * Created by Chentie on 2017/9/18.
 */

public class OrderDetails {


    private List<SaleOrderDetailBean> SaleOrderDetail;

    public List<SaleOrderDetailBean> getSaleOrderDetail() {
        return SaleOrderDetail;
    }

    public void setSaleOrderDetail(List<SaleOrderDetailBean> SaleOrderDetail) {
        this.SaleOrderDetail = SaleOrderDetail;
    }

    public static class SaleOrderDetailBean {
        /**
         * PreQty : 1.0
         * ProductName : 五粮液100ml45度清纯小酒
         * SKU : 006241
         * SalePrice : 12.8641
         * SaleUnit : 瓶
         */

        private double PreQty;
        private String ProductName;
        private String SKU;
        private double SalePrice;
        private String SaleUnit;

        public double getPreQty() {
            return PreQty;
        }

        public void setPreQty(double PreQty) {
            this.PreQty = PreQty;
        }

        public String getProductName() {
            return ProductName;
        }

        public void setProductName(String ProductName) {
            this.ProductName = ProductName;
        }

        public String getSKU() {
            return SKU;
        }

        public void setSKU(String SKU) {
            this.SKU = SKU;
        }

        public double getSalePrice() {
            return SalePrice;
        }

        public void setSalePrice(double SalePrice) {
            this.SalePrice = SalePrice;
        }

        public String getSaleUnit() {
            return SaleUnit;
        }

        public void setSaleUnit(String SaleUnit) {
            this.SaleUnit = SaleUnit;
        }
    }
}
