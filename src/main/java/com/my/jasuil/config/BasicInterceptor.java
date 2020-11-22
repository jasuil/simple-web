package com.my.jasuil.config;

import com.my.jasuil.data.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BasicInterceptor implements HandlerInterceptor {
    @Autowired
    UserInfoRepository userInfoRepository;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        List<Cookie> cookieList = Arrays.stream(request.getCookies()).collect(Collectors.toList());
//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
//        response.setHeader("Access-Control-Allow-Headers",
//                "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, user-email, user-password");

        String emailCookie = null;
        String passwordCookie = null;
        for(Cookie cookie : cookieList) {
            if(cookie.getName().equals("user-email")) {
                emailCookie = cookie.getValue();
            } else if(cookie.getName().equals("user-password")) {
                passwordCookie = cookie.getValue();
            }
        }

        String email = emailCookie == null ? request.getHeader("user-email") : emailCookie;
        String password = passwordCookie == null ? request.getHeader("user-password") : passwordCookie;
        List<String> allowList = new ArrayList<>();
        allowList.add("userinfo");

        if(request.getMethod() != null && request.getMethod().equals("OPTIONS")) {
            return true;
        }

        if(email != null && password != null) {
            if(userInfoRepository.findAllByEmailAndPassword(email, password) != null) {

                Cookie cookie = new Cookie("user-email", email);
                if(emailCookie == null) {
                    cookie.setMaxAge(60 * 60 * 24);
                    response.addCookie(cookie);
                }

                if(passwordCookie == null) {
                    cookie = new Cookie("user-password", password);
                    cookie.setMaxAge(60 * 60 * 24);
                    response.addCookie(cookie);
                }
                return true;
            }
        }

        if(allowList.contains(request.getRequestURL().toString().split("\\/")[3])) return true;
        response.setStatus(400);
        response.setHeader("auth", "required");
        return false;
    }
    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception) throws Exception {

    }
}
