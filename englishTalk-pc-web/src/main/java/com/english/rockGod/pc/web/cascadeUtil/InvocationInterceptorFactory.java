package com.english.rockGod.pc.web.cascadeUtil;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/7/23/023.
 */
public interface InvocationInterceptorFactory {
    InvocationInterceptor create(Method var1, Object var2, MethodParametersResolver var3);
}
