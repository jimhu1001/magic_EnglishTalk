package com.english.rockGod.pc.web.cascadeUtil;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/23/023.
 */
public class ContextParams {
    private ContextParams parent;
    private Map current;

    public ContextParams(Map current, ContextParams parent) {
        this.parent = parent;
        this.current = current;
    }

    public Object get(Object key) {
        Object result = null;
        if(this.current != null) {
            result = this.current.get(key);
            if(result != null) {
                return result;
            }
        }

        if(this.parent != null) {
            result = this.parent.get(key);
        }

        return result;
    }

    public Map getAll() {
        HashMap ret = Maps.newHashMap();
        if(this.parent != null) {
            ret.putAll(this.parent.getAll());
        }

        if(this.current != null) {
            ret.putAll(this.current);
        }

        return ret;
    }

    public ContextParams extend(Map params) {
        return new ContextParams(params, this);
    }

    public static ContextParams create(Map params) {
        return new ContextParams(params, (ContextParams)null);
    }

    public static ContextParams create() {
        return create((Map)null);
    }
}
