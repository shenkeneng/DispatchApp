package com.frxs.dispatch.model;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *     author : ewu
 *     e-mail : xxx@xx
 *     time   : 2017/08/30
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */
public class OrderInOrderList implements Serializable {

    private List<ShelfAreaBean> ShelfArea;

    private List<OrderBean> Orders;

    public List<ShelfAreaBean> getShelfArea() {
        return ShelfArea;
    }

    public void setShelfArea(List<ShelfAreaBean> shelfArea) {
        ShelfArea = shelfArea;
    }

    public List<OrderBean> getOrders() {
        return Orders;
    }

    public void setOrders(List<OrderBean> orders) {
        Orders = orders;
    }
}
