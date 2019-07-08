package com.frxs.dispatch.greendao.entity;

import android.text.TextUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

/**
 * <pre>
 *     author : ewu
 *     e-mail : xxx@xx
 *     time   : 2017/06/08
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */
@Entity
public class ProductEntity implements Serializable {
    private static final long serialVersionUID = -1;

    @Id
    private Long id;

    private String PreBuyID;

    private String VendorName;

    private String VendorCode;

    private String ProductId;

    private String ProductName;

    private String SKU;

    private String BarCode;

    private double BuyPackingQty;

    private String ShelfCode;

    private double BuyQty;

    private String BuyUnit;

    private String Unit;

    private double UnitQty;

    private String Remark;

    private String ImgUrl;

    private boolean isReceived; //客户端自定义字段标识是否已收货 true已收货 false未收货

    private double receivedQty = -1; //客户端自定义字段收货数量

    private String receivedUnit; //客户端自定义字段收货单位

    @Transient
    private String tempUnit; //客户端自定义字段临时保留的字段做单位切换用

    @Generated(hash = 1866394280)
    public ProductEntity(Long id, String PreBuyID, String VendorName,
            String VendorCode, String ProductId, String ProductName, String SKU,
            String BarCode, double BuyPackingQty, String ShelfCode, double BuyQty,
            String BuyUnit, String Unit, double UnitQty, String Remark,
            String ImgUrl, boolean isReceived, double receivedQty,
            String receivedUnit) {
        this.id = id;
        this.PreBuyID = PreBuyID;
        this.VendorName = VendorName;
        this.VendorCode = VendorCode;
        this.ProductId = ProductId;
        this.ProductName = ProductName;
        this.SKU = SKU;
        this.BarCode = BarCode;
        this.BuyPackingQty = BuyPackingQty;
        this.ShelfCode = ShelfCode;
        this.BuyQty = BuyQty;
        this.BuyUnit = BuyUnit;
        this.Unit = Unit;
        this.UnitQty = UnitQty;
        this.Remark = Remark;
        this.ImgUrl = ImgUrl;
        this.isReceived = isReceived;
        this.receivedQty = receivedQty;
        this.receivedUnit = receivedUnit;
    }

    @Generated(hash = 27353230)
    public ProductEntity() {
    }

    public String getTempUnit() {
        if (TextUtils.isEmpty(tempUnit)) {
            tempUnit = TextUtils.isEmpty(receivedUnit) ? BuyUnit: receivedUnit;
        }
        return tempUnit;
    }

    public void setTempUnit(String tempUnit) {
        this.tempUnit = tempUnit;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreBuyID() {
        return this.PreBuyID;
    }

    public void setPreBuyID(String PreBuyID) {
        this.PreBuyID = PreBuyID;
    }

    public String getVendorName() {
        return this.VendorName;
    }

    public void setVendorName(String VendorName) {
        this.VendorName = VendorName;
    }

    public String getVendorCode() {
        return this.VendorCode;
    }

    public void setVendorCode(String VendorCode) {
        this.VendorCode = VendorCode;
    }

    public String getProductId() {
        return this.ProductId;
    }

    public void setProductId(String ProductId) {
        this.ProductId = ProductId;
    }

    public String getProductName() {
        return this.ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public String getSKU() {
        return this.SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getBarCode() {
        return this.BarCode;
    }

    public void setBarCode(String BarCode) {
        this.BarCode = BarCode;
    }

    public double getBuyPackingQty() {
        return this.BuyPackingQty;
    }

    public void setBuyPackingQty(double BuyPackingQty) {
        this.BuyPackingQty = BuyPackingQty;
    }

    public String getShelfCode() {
        return this.ShelfCode;
    }

    public void setShelfCode(String ShelfCode) {
        this.ShelfCode = ShelfCode;
    }

    public double getBuyQty() {
        return this.BuyQty;
    }

    public void setBuyQty(double BuyQty) {
        this.BuyQty = BuyQty;
    }

    public String getBuyUnit() {
        return this.BuyUnit;
    }

    public void setBuyUnit(String BuyUnit) {
        this.BuyUnit = BuyUnit;
    }

    public String getUnit() {
        return this.Unit;
    }

    public void setUnit(String Unit) {
        this.Unit = Unit;
    }

    public double getUnitQty() {
        return this.UnitQty;
    }

    public void setUnitQty(double UnitQty) {
        this.UnitQty = UnitQty;
    }

    public String getRemark() {
        return this.Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getImgUrl() {
        return this.ImgUrl;
    }

    public void setImgUrl(String ImgUrl) {
        this.ImgUrl = ImgUrl;
    }

    public boolean getIsReceived() {
        return this.isReceived;
    }

    public void setIsReceived(boolean isReceived) {
        this.isReceived = isReceived;
    }

    public double getReceivedQty() {
        return this.receivedQty;
    }

    public void setReceivedQty(double receivedQty) {
        this.receivedQty = receivedQty;
    }

    public String getReceivedUnit() {
        return this.receivedUnit;
    }

    public void setReceivedUnit(String receivedUnit) {
        this.receivedUnit = receivedUnit;
    }





}
