package com.example.intelligentcommunity.util;

import java.util.HashMap;

public class Result extends HashMap<String, Object> {
    //成功返回
    public static Result ok() {
        Result result = new Result();
        result.put("code", 200);
        result.put("msg", "操作成功");
        return result;
    }
    //失败返回
    public static Result error(String msg) {
        Result result = new Result();
        result.put("code", 500);
        result.put("msg", msg);
        return result;
    }

    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
