package com.english.rockGod.pc.web.cascadeUtil;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/7/23/023.
 */
public class BeansCascadeFactory implements CascadeFactory  {
    private RegistryCascadeFactory registryCascadeFactory;

    public BeansCascadeFactory(Collection<? extends Object> beans, CascadeFactoryConfig config) {
        Registry registry = new Registry(config);
        Iterator var4 = beans.iterator();

        while(var4.hasNext()) {
            Object bean = var4.next();
            registry.register(bean);
        }

        this.registryCascadeFactory = new RegistryCascadeFactory(registry, config);
    }

    public Cascade create() {
        return this.registryCascadeFactory.create();
    }
}
