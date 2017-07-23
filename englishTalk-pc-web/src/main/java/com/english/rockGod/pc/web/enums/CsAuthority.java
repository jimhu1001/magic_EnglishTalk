package com.english.rockGod.pc.web.enums;



import com.english.rockGod.pc.web.dto.PermissionEnums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by shenyoujun on 16/6/2.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CsAuthority {

    PermissionEnums[] permissions();
}
