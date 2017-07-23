package com.english.rockGod.pc.web.cascadeUtil;


import com.google.common.base.Function;
import com.google.common.collect.Lists;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Administrator on 2017/7/23/023.
 */
public class MethodParametersResolver {
    private List<ParameterResolver> parameterResolvers = Lists.newArrayList();
    private String methodName;

    public MethodParametersResolver(Method method, List<ParameterResolverFactory> parameterResolverFactories) {
        this.methodName = method.getName();
        int parameterIndex = 0;
        Class[] types = method.getParameterTypes();
        Annotation[][] var5 = method.getParameterAnnotations();
        int var6 = var5.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            Annotation[] annotations = var5[var7];
            Class type = types[parameterIndex];
            ParameterResolver parameterResolver = this.getParameterResolver(annotations, type, parameterResolverFactories);
            if(parameterResolver == null) {
                throw new IllegalArgumentException(String.format("%s: can not resolve the [%s] parameter", new Object[]{this.methodName, Integer.valueOf(parameterIndex + 1)}));
            }

            this.parameterResolvers.add(parameterResolver);
            ++parameterIndex;
        }

    }

    private ParameterResolver getParameterResolver(Annotation[] annotations, Class type, List<ParameterResolverFactory> parameterResolverFactories) {
        Iterator var4 = parameterResolverFactories.iterator();

        ParameterResolver parameterResolver;
        do {
            if(!var4.hasNext()) {
                return null;
            }

            ParameterResolverFactory parameterResolverFactory = (ParameterResolverFactory)var4.next();
            parameterResolver = parameterResolverFactory.create(annotations, type);
        } while(parameterResolver == null);

        return parameterResolver;
    }

    public List<Object> resolve(final ContextParams params) {
        try {
            return Lists.transform(this.parameterResolvers, new Function() {
                @Override
                public Object apply(Object o) {
                    ParameterResolver input=(ParameterResolver)o;
                    return input.resolve(params);
                }
            });
        } catch (Exception var3) {
            throw new RuntimeException(this.methodName + ": illegal argument: " + var3.getMessage());
        }
    }
}
