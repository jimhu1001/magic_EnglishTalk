package com.english.rockGod.admin.service.framework;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by shenyoujun on 15/1/4.
 */
public class BeanMappingServiceImpl implements BeanMappingService {

    @Autowired
    @Qualifier("adminBeanMapper")
    private Mapper mapper;

    @Override
    public <T> T transform(Object o, Class<T> clazz) {
        try {
            T t =clazz.newInstance();
            mapper.map(o,t);
            return t;
        }   catch (Exception e){
            throw new RuntimeException("转换对象有问题",e);
        }
    }
}
