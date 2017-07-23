package com.english.rockGod.pc.web.cascadeUtil;

import com.english.rockGod.pc.web.dto.Field;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/7/23/023.
 */
public class PropsPickerFactory implements InvocationInterceptorFactory {
    public PropsPickerFactory() {
    }

    public InvocationInterceptor create(Method method, Object target, MethodParametersResolver methodParametersResolver) {
        return new InvocationInterceptor() {
            public Object invoke(InvocationHandler invocationHandler, Field field, ContextParams contextParams) {
                Object result = invocationHandler.invoke(field, contextParams);
                List props = field.getProps();
                if(props.size() == 0) {
                    return result;
                } else {
                    HashMap ret = Maps.newHashMapWithExpectedSize(props.size());
                    Iterator var7 = props.iterator();

                    while(var7.hasNext()) {
                        String prop = (String)var7.next();

                        try {
                            ret.put(prop, PropertyUtils.getProperty(result, prop));
                        } catch (Exception var10) {
                            throw new IllegalArgumentException(String.format("can not get prop \"%s\" from [%s]", new Object[]{prop, result.getClass().getSimpleName()}));
                        }
                    }

                    return ret;
                }
            }
        };
    }
}
