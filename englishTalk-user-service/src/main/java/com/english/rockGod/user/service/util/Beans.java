package com.english.rockGod.user.service.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

public class Beans implements ApplicationContextAware {

    public static ApplicationContext applicationContext;



    public static Object getBean(String className) {
        return applicationContext.getBean(className);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(Class<T> type) {

        T bean = null;

        Map<String, T> map = applicationContext.getBeansOfType(type);
        if (map.size() == 1) {
            // only return the bean if there is exactly one
            bean = (T) map.values().iterator().next();
        }
        return bean;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext = applicationContext;
    }
}
