package com.frxs.dispatch.model;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *     author : ewu
 *     e-mail : xxx@xx
 *     time   : 2017/09/01
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */
public class GetDeliverList implements Serializable {

    /**
     * EmpID : 1
     * EmpName : sample string 2
     * ShippingOwnerType : 3
     */

    private List<DriversBean> Drivers;

    public List<DriversBean> getDrivers() {
        return Drivers;
    }

    public void setDrivers(List<DriversBean> Drivers) {
        this.Drivers = Drivers;
    }

    public static class DriversBean {
        private int EmpID;
        private String EmpName;
        private int ShippingOwnerType;
        private int serialNumber;   //自定义字段 车次号

        public int getEmpID() {
            return EmpID;
        }

        public void setEmpID(int EmpID) {
            this.EmpID = EmpID;
        }

        public String getEmpName() {
            return EmpName;
        }

        public void setEmpName(String EmpName) {
            this.EmpName = EmpName;
        }

        public int getShippingOwnerType() {
            return ShippingOwnerType;
        }

        public void setShippingOwnerType(int ShippingOwnerType) {
            this.ShippingOwnerType = ShippingOwnerType;
        }

        public int getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(int serialNumber) {
            this.serialNumber = serialNumber;
        }

        @Override
        public String toString() {
            return EmpName;
        }
    }
}
