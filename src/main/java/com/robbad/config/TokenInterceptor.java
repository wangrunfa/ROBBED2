package com.robbad.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("进入拦截器");
        //检查
        HttpSession session = request.getSession();
        try {

            if ((boolean) session.getAttribute("status")) {
                System.out.println(request.getRequestURI() + " : 通过检查");
                return true;
            } else {
                System.out.println(request.getRequestURI() + " : 未能通过检查");
                request.getRequestDispatcher("login").forward(request,response);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();

            session.setAttribute("status",false);
            request.getRequestDispatcher("login").forward(request,response);
            return false;
        }

    }

}