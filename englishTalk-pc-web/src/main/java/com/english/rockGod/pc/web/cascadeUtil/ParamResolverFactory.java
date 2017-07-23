package com.english.rockGod.pc.web.cascadeUtil;


import com.google.common.collect.Lists;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig.Feature;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * Created by Administrator on 2017/7/23/023.
 */
public class ParamResolverFactory implements ParameterResolverFactory {
    private static ObjectMapper m = new ObjectMapper();
    private static final String SPLITTER = ",";
    private static final List<? extends Class> NOT_ALLOW_NULL_CLASSES;

    public ParamResolverFactory() {
    }

    private static boolean isAllowNullFor(Class type) {
        return !NOT_ALLOW_NULL_CLASSES.contains(type);
    }

    public ParameterResolver create(Annotation[] annotations, final Class type) {
        Annotation[] var3 = annotations;
        int var4 = annotations.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Annotation annotation = var3[var5];
            if(annotation instanceof Param) {
                Param param = (Param)annotation;
                final String[] paramKeys = param.value().split(",");
                final boolean allowNull = isAllowNullFor(type);
                return new ParameterResolver() {
                    public Object resolve(ContextParams params) {
                        Object value = null;
                        String[] var3 = paramKeys;
                        int var4 = var3.length;

                        for(int var5 = 0; var5 < var4; ++var5) {
                            String paramKey = var3[var5];
                            value = params.get(paramKey);
                            if(value != null) {
                                break;
                            }
                        }

                        return this.convert(value);
                    }

                    private Object convert(Object o) {
                        if(o == null) {
                            if(allowNull) {
                                return null;
                            } else {
                                throw new IllegalArgumentException(this.getLocation() + "not allow null");
                            }
                        } else if(o.getClass().equals(type)) {
                            return o;
                        } else {
                            try {
                                return ParamResolverFactory.m.convertValue(o, type);
                            } catch (Exception var3) {
                                throw new RuntimeException(this.getLocation() + String.format("param type not match: expect [%s], actual [%s]", new Object[]{type.getSimpleName(), o.getClass().getSimpleName()}));
                            }
                        }
                    }

                    private String getLocation() {
                        return String.format("@Param(\"%s\") ", paramKeys);
                    }
                };
            }
        }

        return null;
    }

    static {
        m.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        NOT_ALLOW_NULL_CLASSES = Lists.newArrayList(new Class[]{Integer.TYPE, Boolean.TYPE, Double.TYPE, Float.TYPE, Long.TYPE});
    }
}
