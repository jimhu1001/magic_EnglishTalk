package com.english.rockGod.pc.web.cascadeUtil;

import com.english.rockGod.pc.web.dto.Field;
import com.english.rockGod.pc.web.exception.ApplicationException;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/7/23/023.
 */
public class ExceptionHandlerFactory implements InvocationInterceptorFactory {
    public ExceptionHandlerFactory() {
    }

    public InvocationInterceptor create(Method method, Object target, MethodParametersResolver methodParametersResolver) {
        return new InvocationInterceptor() {
            public Object invoke(InvocationHandler invocationHandler, Field field, ContextParams contextParams) {
                try {
                    return invocationHandler.invoke(field, contextParams);
                } catch (Throwable var6) {
                    Throwable cause = this.getCause(var6);
                    throw new ApplicationException(cause.getMessage());
                }
            }

            private Throwable getCause(Throwable outer) {
                Throwable inner = outer.getCause();
                return inner == null?outer:this.getCause(inner);
            }
        };
    }
}
