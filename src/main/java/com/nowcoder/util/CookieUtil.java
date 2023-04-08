package com.nowcoder.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
  * @ClassName CookieUtil
  * @description: TODO
  * @author Hwangjj
  * @date 2023/4/8 11:50
  * @version: 1.0
  */ 

public class CookieUtil {

    public static String getValue(HttpServletRequest request, String name){
        if (request == null || name == null){
            throw new IllegalArgumentException("参数为空");
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for(Cookie cookie:cookies){
                if (cookie.getName().equals(name)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
