package com.newcoder.duihua.interceptor;

import com.newcoder.duihua.dao.LoginTicketDao;
import com.newcoder.duihua.dao.UserDao;
import com.newcoder.duihua.model.HostHolder;
import com.newcoder.duihua.model.LoginTicket;
import com.newcoder.duihua.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by wangdong on 2017/8/17.
 */
@Component
public class PassportIntercepter implements HandlerInterceptor {
    @Autowired
    UserDao userDao;
    @Autowired
    LoginTicketDao loginTicketDao;
    @Autowired
    HostHolder hostHolder;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket = null;
        if (httpServletRequest.getCookies()!=null) {
            for (Cookie cookie:httpServletRequest.getCookies()) {
                    if (cookie.getName().equals("ticket")) {
                        ticket = cookie.getValue();
                        break;
                    }
            }
        }

        if (ticket!=null) {
           LoginTicket loginTicket =  loginTicketDao.selectByTicket(ticket);
           //ticket无效时
           if (loginTicket==null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus()!=0) {
                return true;
           }
           User user = userDao.selectById(loginTicket.getUserId());
           hostHolder.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView!=null && hostHolder.getUser()!=null) {
            modelAndView.addObject("user",hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        hostHolder.clear();
    }
}
