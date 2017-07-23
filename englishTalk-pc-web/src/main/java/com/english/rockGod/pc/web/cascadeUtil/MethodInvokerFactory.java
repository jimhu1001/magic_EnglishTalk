package com.english.rockGod.pc.web.cascadeUtil;

import com.english.rockGod.pc.web.dto.Field;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/7/23/023.
 */
public class MethodInvokerFactory implements InvocationInterceptorFactory {
    public MethodInvokerFactory() {
    }

    public InvocationInterceptor create(final Method method, final Object target, final MethodParametersResolver methodParametersResolver) {
        return new InvocationInterceptor() {
            public Object invoke(InvocationHandler invocationHandler, Field field, ContextParams contextParams) {
                try {
                    return method.invoke(target, methodParametersResolver.resolve(contextParams).toArray());
                } catch (Exception var5) {
                    throw new RuntimeException(var5);
                }
            }
        };
    }
}
