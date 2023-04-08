package com.nowcoder.controller.interceptor;

import com.nowcoder.annotation.LoginRequired;
import com.nowcoder.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
  * @ClassName LoginRequiredInterceptor
  * @description: TODO
  * @author Hwangjj
  * @date 2023/4/8 15:23
  * @version: 1.0
  */ 
@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //若拦截器拦截到的是一个方法
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            //获取方法上是否有该注解，并判断此时是否登录
            LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
            if (loginRequired != null && hostHolder.getUser() == null){
                //重定向至登录页面
                response.sendRedirect(request.getContextPath() + "/login");
                //不访问controller
                return false;
            }
        }
        return true;
    }
}
