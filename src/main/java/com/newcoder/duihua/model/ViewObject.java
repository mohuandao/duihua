package com.newcoder.duihua.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangdong on 2017/8/4.
 */
public class ViewObject {
   private Map<String ,Object> objs = new HashMap<String, Object>() ;


    public void set(String key,Object obj) {
        objs.put(key, obj);
    }

    public Object get(String key) {
        return objs.get(key);
    }
}
