package com.ideu.demo.server08.interceptor;

import com.ideu.demo.server08.common.BaseContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long userId = (Long) request.getSession().getAttribute("Authorization");
        BaseContext.setCurrentId(userId);
        return true;
    }
}
