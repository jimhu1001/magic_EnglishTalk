package com.english.rockGod.pc.web.cascadeUtil;

import com.english.rockGod.pc.web.dto.Field;

/**
 * Created by Administrator on 2017/7/23/023.
 */
public interface InvocationInterceptor {
    Object invoke(InvocationHandler var1, Field var2, ContextParams var3);

}
