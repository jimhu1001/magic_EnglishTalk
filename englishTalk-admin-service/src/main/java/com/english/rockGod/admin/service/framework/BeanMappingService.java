package com.english.rockGod.admin.service.framework;

/**
 * Created by shenyoujun on 15/1/4.
 */
public interface BeanMappingService {

    <T> T transform(Object o, Class<T> clazz);
}
