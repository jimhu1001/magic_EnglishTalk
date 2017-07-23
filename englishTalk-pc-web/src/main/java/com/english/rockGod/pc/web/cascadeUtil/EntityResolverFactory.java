package com.english.rockGod.pc.web.cascadeUtil;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.lang.annotation.Annotation;

/**
 * Created by Administrator on 2017/7/23/023.
 */
public class EntityResolverFactory implements ParameterResolverFactory {
    private static ObjectMapper m = new ObjectMapper();

    public EntityResolverFactory() {
        m.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public ParameterResolver create(Annotation[] annotations, final Class type) {
        Annotation[] var3 = annotations;
        int var4 = annotations.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Annotation annotation = var3[var5];
            if(annotation instanceof Entity) {
                return new ParameterResolver() {
                    public Object resolve(ContextParams params) {
                        try {
                            return EntityResolverFactory.m.convertValue(params.getAll(), type);
                        } catch (Exception var3) {
                            throw new RuntimeException(String.format("@Entity param can not create instance for type [%s]", new Object[]{type.getSimpleName()}));
                        }
                    }
                };
            }
        }

        return null;
    }
}
