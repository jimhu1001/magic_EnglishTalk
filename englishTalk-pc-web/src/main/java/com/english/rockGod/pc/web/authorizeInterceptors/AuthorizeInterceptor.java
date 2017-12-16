package com.english.rockGod.pc.web.authorizeInterceptors;

import com.english.rockGod.pc.web.dto.ContextContainer;
import com.english.rockGod.pc.web.dto.PermissionEnums;
import com.english.rockGod.pc.web.enums.Authority;
import com.english.rockGod.pc.web.exception.ApplicationException;
import com.english.rockGod.pc.web.service.impl.PermissionServiceAgentImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/11/19/019.
 */
public class AuthorizeInterceptor implements HandlerInterceptor {

    @Autowired
    PermissionServiceAgentImpl permissionServiceAgent;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        try {
            HandlerMethod handler_ = (HandlerMethod) handler;
            Authority authority = handler_.getMethodAnnotation(Authority.class);
            if (null == authority) {
                return true;
            }
            PermissionEnums[] permissions = authority.permissions();
            Boolean hasPermission = Boolean.FALSE;
            for (PermissionEnums permissionEnums : permissions) {
                Integer userId = ContextContainer.getUserContext().getUserId();
                //Assert.assertFalse(userId == 0, "用户信息缺失");
                //先从用户上下文缓存中找,如果缓存中没有,调用service找
                if (!CollectionUtils.isEmpty(ContextContainer.getUserContext().permissionIds())) {
                    if(ContextContainer.getUserContext().permissionIds().contains(permissionEnums.getCode())){
                        hasPermission = Boolean.TRUE;
                        break;
                    }
                } else {
                    if(permissionServiceAgent.hasPermission(userId, permissionEnums.getCode())){
                        hasPermission = Boolean.TRUE;
                        break;
                    }
                }
            }
            //Assert.assertTrue(hasPermission, "用户没有权限");
            return true;
        } catch (Exception e) {
            //todo 定义新的Exception
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
