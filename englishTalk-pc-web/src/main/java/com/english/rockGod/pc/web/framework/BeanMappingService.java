package com.english.rockGod.pc.web.framework;

/**
 * Created by jimHu on 17/7/22.
 */
public interface BeanMappingService {

    <T> T transform(Object o, Class<T> clazz);


}
