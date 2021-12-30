package com.huawei.util;

import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @ClassName:  JsonUtil   
 * @Description:  TODO(Json工具类) 
 * @author: XIE.YUXI 
 * @date:   2021年12月30日 下午2:23:40   
 *
 */
public class JsonUtil {

    public static Map<Object, Object>jsonToMap(String jsonStr){
        Map<Object, Object> map = JSON.parseObject(jsonStr, Map.class);
        return map;
    }
}
