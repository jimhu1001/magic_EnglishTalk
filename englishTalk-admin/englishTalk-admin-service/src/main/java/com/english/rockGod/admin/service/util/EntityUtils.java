package com.english.rockGod.admin.service.util;


import java.util.Date;

public abstract class EntityUtils {

    public static <T extends BaseEntity> T init(T t) {
        Date date = new Date();
        t.setCreateTime(date);
        t.setUpdateTime(date);
        return t;
    }

    public static <T extends BaseEntity> T update(T t) {
        Date date = new Date();
        t.setUpdateTime(date);
        return t;
    }

}
