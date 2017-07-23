package com.english.rockGod.pc.web.cascadeUtil;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by Administrator on 2017/7/23.
 */
public class SpringContextCascadeFactory implements ApplicationContextAware, CascadeFactory  {
    private BeansCascadeFactory beansCascadeFactory;
    private CascadeFactoryConfig config;

    public SpringContextCascadeFactory(CascadeFactoryConfig config) {
        this.config = config;
    }

    public SpringContextCascadeFactory() {
        this(CascadeFactoryConfig.DEFAULT);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.beansCascadeFactory = new BeansCascadeFactory(applicationContext.getBeansOfType(CascadeAware.class).values(), this.config);
    }

    public Cascade create() {
        return this.beansCascadeFactory.create();
    }
}
