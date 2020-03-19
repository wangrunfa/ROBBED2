package com.robbad.config;

import com.robbad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("进入拦截器");
        //检查
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("lgPhone"));
        try {
            if ((boolean)session.getAttribute("userStatus")) {
                System.out.println(request.getRequestURI() + " : 通过检查");
//                session.setAttribute("lgBalance",userService.findLgBalance((String)session.getAttribute("lgPhone")));
                return true;
            } else {
                System.out.println(request.getRequestURI() + " : 未能通过检查");
                request.getRequestDispatcher("login").forward(request,response);
                return false;
            }
        } catch (Exception e) {

            session.setAttribute("userStatus",false);
            request.getRequestDispatcher("login").forward(request,response);
            return false;
        }

    }

}