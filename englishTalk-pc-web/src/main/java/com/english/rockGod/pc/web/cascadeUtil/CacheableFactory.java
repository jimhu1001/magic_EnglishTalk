package com.english.rockGod.pc.web.cascadeUtil;

import com.english.rockGod.pc.web.dto.Field;
import com.english.rockGod.pc.web.enums.CsAuthority;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/7/23/023.
 */
public class CacheableFactory implements InvocationInterceptorFactory {
    public CacheableFactory() {
    }

    public InvocationInterceptor create(Method method, Object target, final MethodParametersResolver methodParametersResolver) {
        if(method.getAnnotation(Cacheable.class) != null) {
            Cacheable cacheable = (Cacheable)method.getAnnotation(Cacheable.class);
            CacheBuilder builder = CacheBuilder.newBuilder();
            if(cacheable.expireMinutes() > 0) {
                builder.expireAfterWrite((long)cacheable.expireMinutes(), TimeUnit.MINUTES);
            }

            final Cache resultsCache = builder.build();
            return new InvocationInterceptor() {
                public Object invoke(final InvocationHandler invocationHandler, final Field field, final ContextParams contextParams) {
                    try {
                        return resultsCache.get(methodParametersResolver.resolve(contextParams), new Callable() {
                            public Object call() throws Exception {
                                return invocationHandler.invoke(field, contextParams);
                            }
                        });
                    } catch (ExecutionException var5) {
                        throw new RuntimeException(var5);
                    }
                }
            };
        } else {
            return null;
        }
    }
}
