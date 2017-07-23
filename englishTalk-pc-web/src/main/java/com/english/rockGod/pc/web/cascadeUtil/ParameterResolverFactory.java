package com.english.rockGod.pc.web.cascadeUtil;

import java.lang.annotation.Annotation;

/**
 * Created by Administrator on 2017/7/23/023.
 */
public interface ParameterResolverFactory {
    ParameterResolver create(Annotation[] var1, Class var2);
}
