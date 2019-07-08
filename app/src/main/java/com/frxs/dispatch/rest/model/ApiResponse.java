package com.frxs.dispatch.rest.model;

import android.text.TextUtils;

/**
 * Created by ewu on 2016/3/23.
 */
public class ApiResponse<T> {

    private String Flag;// 状态

    private String Info;// 信息

    private T Data;           // 单个对象

    private T DataList;

    private String FlagDescription;

    private String CachedTime;

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public T getDataList() {
        return DataList;
    }

    public void setDataList(T dataList) {
        DataList = dataList;
    }

    public String getFlagDescription() {
        return FlagDescription;
    }

    public void setFlagDescription(String flagDescription) {
        FlagDescription = flagDescription;
    }

    public String getCachedTime() {
        return CachedTime;
    }

    public void setCachedTime(String cachedTime) {
        CachedTime = cachedTime;
    }

    public boolean isSuccessful() {
        if (!TextUtils.isEmpty(Flag) && Flag.equals("0")) {
            return true;
        } else {
            return false;
        }
    }
}
