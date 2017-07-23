package com.english.rockGod.pc.web.cascadeUtil;


import com.english.rockGod.pc.web.dto.ContextContainer;
import com.english.rockGod.pc.web.dto.PermissionEnums;
import com.english.rockGod.pc.web.dto.UserContext;
import com.english.rockGod.pc.web.enums.CsAuthority;
import com.english.rockGod.pc.web.exception.ApplicationException;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by jimHu on 17/2/28.
 */
public class CascadePermissionInterceptorFactory implements InvocationInterceptorFactory {
    @Override
    public InvocationInterceptor create(Method method, Object target, MethodParametersResolver methodParametersResolver) {
        return (invocationHandler, field, contextParams) -> {
            CsAuthority csAuthority = method.getAnnotation(CsAuthority.class);
            if(csAuthority!=null){
                UserContext userContext = ContextContainer.getUserContext();
                if(userContext != null){
                    if(CollectionUtils.isEmpty(userContext.getPermissionDtos())){
                        throw new RuntimeException("用户没权限");
                    }
                    Boolean hasPermission = Boolean.FALSE;
                    List<Integer> userPermissionIds = userContext.getPermissionDtos().stream()
                            .map(item->item.getPermissionId()).collect(Collectors.toList());

                    for(PermissionEnums permissionEnum:csAuthority.permissions()){
                        if(userPermissionIds.contains(permissionEnum.getCode())){
                            hasPermission = Boolean.TRUE;
                            break;
                        }
                    }
                    if(!hasPermission){
                        throw new ApplicationException("用户没有权限");
                    }
                }
            }
            return invocationHandler.invoke(field,contextParams);
        };
    }
}
