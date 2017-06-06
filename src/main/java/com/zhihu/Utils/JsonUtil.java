package com.zhihu.Utils;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by dell on 2017/6/5.
 */
public class JsonUtil {
    public static final int DEFAULT_USER_ID=2568;

    public static String getJSONString(int code) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        return json.toJSONString();
    }

    public static String getJSONString(int code, String msg) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        return json.toJSONString();
    }
}
