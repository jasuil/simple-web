package com.my.jasuil.config;

import com.my.jasuil.data.entities.UserInfo;
import com.my.jasuil.data.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class BasicInterceptor implements HandlerInterceptor {
    @Autowired
    UserInfoRepository userInfoRepository;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        List<Cookie> cookieList = Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[]{})).collect(Collectors.toList());
        String userSessionCookie = getSessionCookie(cookieList);

        String email = request.getHeader("user-email");
        String password = request.getHeader("user-password");
        List<String> allowList = new ArrayList<>();
        allowList.add("userinfo");

        if (request.getMethod() != null && request.getMethod().equals("OPTIONS")) {
            return true;
        }

        UserInfo userInfo = getUserInfo(email, password, userSessionCookie);

        if (userInfo != null) {
            if (userSessionCookie == null) {
                UUID uuid = UUID.randomUUID();
                userInfo.setSessionId(uuid.toString());
                setCookie(response, "user-session-id", uuid.toString());
            }
            return true;
        }

        if (allowList.contains(request.getRequestURL().toString().split("\\/")[3])) return true;
        response.setStatus(400);
        response.setHeader("auth", "required");
        return false;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception) throws Exception {

    }

    private String getSessionCookie(List<Cookie> cookieList) {
        for (Cookie cookie : cookieList) {
            if (cookie.getName().equals("user-session-id")) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private UserInfo getUserInfo(String email, String password, String userSessionCookie) {
        if (email != null && password != null) {
            return userInfoRepository.findAllByEmailAndPassword(email, password);
        } else if (userSessionCookie != null) {
            return userInfoRepository.findBySessionId(userSessionCookie);
        }
        return null;
    }

    private void setCookie(HttpServletResponse response, String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);
    }
}
