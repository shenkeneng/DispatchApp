package com.frxs.dispatch.model;

import java.io.Serializable;

/**
 * <pre>
 *     author : ewu
 *     e-mail : xxx@xx
 *     time   : 2017/09/06
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */
public class WarehouseSysParam implements Serializable {

    public static final String SYS_SWEEP_LOADING_MODE = "210"; //货配车模式

    /**
     * ParamCode : sample string 1
     * ParamName : sample string 2
     * ParamValue : sample string 3
     * Remark : sample string 4
     */

    private String ParamCode;
    private String ParamName;
    private String ParamValue;
    private String Remark;

    public String getParamCode() {
        return ParamCode;
    }

    public void setParamCode(String ParamCode) {
        this.ParamCode = ParamCode;
    }

    public String getParamName() {
        return ParamName;
    }

    public void setParamName(String ParamName) {
        this.ParamName = ParamName;
    }

    public String getParamValue() {
        return ParamValue;
    }

    public void setParamValue(String ParamValue) {
        this.ParamValue = ParamValue;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }
}
