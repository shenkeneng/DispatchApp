package com.frxs.dispatch.comms;

/**
 * Created by ewu on 2016/3/23.
 */
public class Config {
    public static final String DB_NAME = "dbname.db";

    public static final String DELIVERS_FILE_NAME = "deliver.txt";

    public static final String PREFS_NAME = "MyFrefsFile";

    public static final String KEY_USER = "key_user";

    public static final String KEY_HIST = "key_history"; // 商品搜索历史

    public static final String KEY_SYNC_TIME = "key_sync_time"; // 同步数据时间

    public static final String KEY_SWEEP_LOADING_MODE = "key_sweep_loading_mode"; // 货配车模式

    public static final String SEARCH_PREFS_NAME = "SearchPrefsFile";

    // 远程服务器网络 (0:线上环境、1:测试环境、2：演示环境)
    public static int networkEnv = 0;

    public static final int TYPE_BASE = 0;

    public static final int TYPE_UPDATE = 1;

    public static String getBaseUrl(int typeUrl) {
    return getBaseUrl(typeUrl, networkEnv);
}

    public static String getBaseUrl(int typeUrl, int networkEnv) {
        String BASE_URL = "";
        if (networkEnv == 0) {
            if (typeUrl == TYPE_BASE) {
//                BASE_URL = "http://api_wh.erp2.frxs.com/";
                BASE_URL = "http://124.232.138.162:5005/";
            } else {
                BASE_URL = "http://orderapi.erp2.frxs.com/api/";
            }
        } else if (networkEnv == 1) {
            if (typeUrl == TYPE_BASE) {
//                BASE_URL = "http://webapitest.erp2.frxs.cn/";
                BASE_URL = "http://113.247.234.76:5005/";
            } else {
                BASE_URL = "http://b2btest.frxs.cn/api/";
            }
        } else if (networkEnv == 2) {
            if (typeUrl == TYPE_BASE) {
                BASE_URL = "http://yfbapi_wh.erp2.frxs.com/";// 预发布环境
            } else {
                BASE_URL = "http://yfbapi_wh.erp2.frxs.com/api/";
            }
        } else if (networkEnv == 3) {
            if (typeUrl == TYPE_BASE) {
                //BASE_URL = "http://192.168.8.246:8080/";// 演示环境
                //BASE_URL = "http://192.168.8.212:8080/";//ZJH
                //BASE_URL = "http://192.168.8.210:8099/api/";
                BASE_URL = "http://192.168.8.63:8086/";
                //BASE_URL = "http://192.168.8.142:8090/";
            } else {
                BASE_URL = "http://b2btest.frxs.cn/api/";
            }
        }else {
            if (typeUrl == TYPE_BASE) {
//                BASE_URL = "http://api_wh.erp2.frxs.com/";
                BASE_URL = "http://124.232.138.162:5005/";
            } else {
                BASE_URL = "http://orderapi.erp2.frxs.com/api/";
            }
        }

        return BASE_URL;
    }
}
