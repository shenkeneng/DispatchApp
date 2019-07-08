package com.frxs.dispatch.model;

import java.io.Serializable;

/**
 * <pre>
 *     author : ewu
 *     e-mail : xxx@xx
 *     time   : 2017/09/05
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */
public class ShelfAreaBean implements Serializable {

    /**
     * ShelfAreaName : sample string 1
     * PackedOrderCount : 2
     * TotalOrderCount : 3
     */

    private String ShelfAreaName;
    private int PackedOrderCount;
    private int TotalOrderCount;

    public String getShelfAreaName() {
        return ShelfAreaName;
    }

    public void setShelfAreaName(String ShelfAreaName) {
        this.ShelfAreaName = ShelfAreaName;
    }

    public int getPackedOrderCount() {
        return PackedOrderCount;
    }

    public void setPackedOrderCount(int PackedOrderCount) {
        this.PackedOrderCount = PackedOrderCount;
    }

    public int getTotalOrderCount() {
        return TotalOrderCount;
    }

    public void setTotalOrderCount(int TotalOrderCount) {
        this.TotalOrderCount = TotalOrderCount;
    }
}
