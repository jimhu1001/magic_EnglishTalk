package com.english.rockGod.pc.web.cascadeUtil;

import com.english.rockGod.pc.web.dto.Field;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.*;

/**
 * Created by Administrator on 2017/7/23/023.
 */
public class SerialReducer implements Reducer {
    private InvocationHandler invocationHandler;

    public SerialReducer(InvocationHandler invocationHandler) {
        this.invocationHandler = invocationHandler;
    }

    public Map reduce(List<Field> fields, ContextParams contextParams) {
        return this.reduceFields(fields, contextParams);
    }

    private Map reduceFields(List<Field> fields, ContextParams contextParams) {
        HashMap results = Maps.newHashMap();
        Iterator var4 = fields.iterator();

        while(var4.hasNext()) {
            Field field = (Field)var4.next();
            results.put(field.getComputedAs(), this.reduceField(field, contextParams));
        }

        return results;
    }

    private Object reduceField(Field field, ContextParams parentContextParams) {
        ContextParams contextParams = parentContextParams.extend(field.getParams());
        Object result = this.invocationHandler.invoke(field, contextParams);
        return field.getChildren().size() != 0 && !Util.canNotHasChildren(result)?(result instanceof Collection ?this.reduceResults(Lists.newArrayList((Collection)result), field.getChildren(), contextParams):this.processFieldsWithResults(result, field.getChildren(), contextParams)):result;
    }

    private List reduceResults(List<Object> results, final List<Field> fields, final ContextParams contextParams) {
        return Lists.transform(results, new Function() {
            public Object apply(Object input) {
                return SerialReducer.this.processFieldsWithResults(input, fields, contextParams);
            }
        });
    }

    private Map processFieldsWithResults(Object result, List<Field> fields, ContextParams parentContextParams) {
        Map resultMap = Util.toMap(result);
        ContextParams contextParams = parentContextParams.extend(resultMap);
        Map subResultMap = this.reduceFields(fields, contextParams);
        resultMap.putAll(subResultMap);
        return resultMap;
    }
}
