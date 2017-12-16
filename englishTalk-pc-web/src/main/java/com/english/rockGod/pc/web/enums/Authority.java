package com.english.rockGod.pc.web.enums;

import com.english.rockGod.pc.web.dto.PermissionEnums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2017/11/19/019.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authority {
    PermissionEnums[] permissions();
}
