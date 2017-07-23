package com.english.rockGod.pc.web.cascadeUtil;

import com.google.common.collect.Lists;
import org.apache.commons.beanutils.PropertyUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/23/023.
 */
public class Util {
    private static List CAN_NOT_HAS_CHILDREN_CLASSES;

    public Util() {
    }

    public static Map toMap(Object bean) {
        if(bean == null) {
            return null;
        } else if(bean instanceof Map) {
            return (Map)bean;
        } else {
            try {
                Map resultMap = PropertyUtils.describe(bean);
                resultMap.remove("class");
                return resultMap;
            } catch (Exception var3) {
                throw new RuntimeException(String.format("[%s] can not convert to Map", new Object[]{bean.getClass().getName()}));
            }
        }
    }

    public static boolean canNotHasChildren(Object o) {
        return o == null || CAN_NOT_HAS_CHILDREN_CLASSES.indexOf(o.getClass()) != -1;
    }

    static {
        CAN_NOT_HAS_CHILDREN_CLASSES = Lists.newArrayList(new Class[]{Integer.TYPE, Boolean.TYPE, Double.TYPE, Float.TYPE, Long.TYPE, String.class, Integer.class, Boolean.class, Double.class, Float.class, Long.class});
    }
}
