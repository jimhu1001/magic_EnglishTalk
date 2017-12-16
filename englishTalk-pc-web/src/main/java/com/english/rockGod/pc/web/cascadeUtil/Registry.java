package com.english.rockGod.pc.web.cascadeUtil;

import com.english.rockGod.pc.web.dto.Field;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/23/023.
 */
public class Registry {
    private Map<String, InvocationHandler> invocationHandlerMap = Maps.newHashMap();
    private CascadeFactoryConfig config;
    private InvocationHandler invocationHandler;

    public Registry(CascadeFactoryConfig config) {
        this.config = config;
        this.invocationHandler = new InvocationHandler() {
            public Object invoke(Field field, ContextParams contextParams) {
                String mapKey = Registry.this.getKey(field.getType(), field.getCategory());
                InvocationHandler invocationHandler = Registry.this.invocationHandlerMap.get(mapKey);
                if(invocationHandler == null) {
                    throw new RuntimeException(mapKey + " has not registered");
                } else {
                    return invocationHandler.invoke(field, contextParams);
                }
            }
        };
    }

    public void register(Object bean) {
        this.register(bean.getClass().getSimpleName(), bean);
    }

    public void register(String type, Object bean) {
        Method[] var3 = bean.getClass().getDeclaredMethods();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Method method = var3[var5];
            if(Modifier.isPublic(method.getModifiers())) {
                this.registerMethod(type, bean, method);
            }
        }

    }

    private String getKey(String type, String methodName) {
        return type + "." + methodName;
    }

    private void registerMethod(String type, Object target, Method method) {
        String methodName = method.getName();
        String mapKey = this.getKey(type, methodName);
        if(this.invocationHandlerMap.containsKey(mapKey)) {
            throw new RuntimeException(mapKey + " has already registered");
        } else {
            this.invocationHandlerMap.put(mapKey, this.buildFieldInvocationHandler(method, target));
        }
    }

    private InvocationHandler buildFieldInvocationHandler(Method method, Object target) {
        return this.buildInvocationHandler(this.buildInvocationInterceptors(this.getInvocationInterceptorFactories(), method, target, this.getParameterResolvers(method)));
    }

    private MethodParametersResolver getParameterResolvers(Method method) {
        ArrayList parameterResolverFactories = Lists.newArrayList();
        if(this.config.getParameterResolverFactories() != null) {
            parameterResolverFactories.addAll(this.config.getParameterResolverFactories());
        }

        parameterResolverFactories.add(new ParamResolverFactory());
        parameterResolverFactories.add(new EntityResolverFactory());
        return new MethodParametersResolver(method, parameterResolverFactories);
    }

    private List<InvocationInterceptor> buildInvocationInterceptors(List<InvocationInterceptorFactory> invocationInterceptorFactories, Method method, Object target, MethodParametersResolver methodParametersResolver) {
        ArrayList invocationInterceptors = Lists.newArrayList();
        Iterator var6 = invocationInterceptorFactories.iterator();

        while(var6.hasNext()) {
            InvocationInterceptorFactory invocationInterceptorFactory = (InvocationInterceptorFactory)var6.next();
            InvocationInterceptor invocationInterceptor = invocationInterceptorFactory.create(method, target, methodParametersResolver);
            if(invocationInterceptor != null) {
                invocationInterceptors.add(invocationInterceptor);
            }
        }

        return invocationInterceptors;
    }

    private List<InvocationInterceptorFactory> getInvocationInterceptorFactories() {
        ArrayList fieldInvocationInterceptorFactories = Lists.newArrayList();
        fieldInvocationInterceptorFactories.add(new MethodInvokerFactory());
        fieldInvocationInterceptorFactories.add(new CacheableFactory());
        fieldInvocationInterceptorFactories.add(new PropsPickerFactory());
        if(this.config.getInvocationInterceptorFactories() != null) {
            fieldInvocationInterceptorFactories.addAll(this.config.getInvocationInterceptorFactories());
        }

        fieldInvocationInterceptorFactories.add(new ExceptionHandlerFactory());
        return fieldInvocationInterceptorFactories;
    }

    private InvocationHandler buildInvocationHandler(List<InvocationInterceptor> interceptors) {
         final InvocationHandler last = null;

        final InvocationInterceptor interceptor;
        /*for(Iterator var3 = interceptors.iterator(); var3.hasNext(); last = new InvocationHandler() {
            public Object invoke(Field field, ContextParams contextParams) {
                return interceptor.invoke(last, field, contextParams);
            }
        }) {
            interceptor = (InvocationInterceptor)var3.next();
        }*/

        return last;
    }


    public InvocationHandler getInvocationHandler() {
        return this.invocationHandler;
    }
}
