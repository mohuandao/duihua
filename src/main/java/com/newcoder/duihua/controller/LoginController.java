package com.newcoder.duihua.controller;

import com.newcoder.duihua.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by wangdong on 2017/8/15.
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    UserService userService;

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public String register(@RequestParam("name") String username,
                           @RequestParam("password") String password,
                           @RequestParam(value = "rememberme", defaultValue = "false") boolean rememberme,
                           @RequestParam("next") String next,//从拦截器里传过来的，每次请求先判断用户是否存在，不存在就将请求路径穿过来，仅仅是方便看到地址
                           HttpServletResponse response,
                           Model model) {
        try {
            Map<String, Object> map = userService.register(username, password);
            //注册后自动登陆后产生的ticket
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                //设置cookie的时间
                if (rememberme ) {
                    cookie.setMaxAge(30 * 24 * 3600);
                }
                response.addCookie(cookie);
                if (!StringUtils.isEmpty(next)) {
                    return "redirect:" + next;
                } else {
                    return "redirect:/";
                }

            } else {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg", "服务器错误");
            return "login";
        }

    }

    @RequestMapping(value = "/reglogin", method = {RequestMethod.GET,RequestMethod.POST})
    public String reglogin(@RequestParam(value = "next", required = false) String next,
                           Model model) {
        model.addAttribute("next", next);
        return "login";
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("name") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "rememberme", defaultValue = "false") boolean rememberme,
                        @RequestParam(value = "next",required = false) String next,
                        HttpServletResponse response,
                        Model model) {
        try {
            Map<String, Object> map = userService.login(username, password);
            //判断cookie
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                //设置cookie时间
                if (rememberme ) {
                    cookie.setMaxAge(7 * 24 * 3600);
                }
                response.addCookie(cookie);
                if (!StringUtils.isEmpty(next)) {
                    return "redirect:" + next;
                } else {
                    return "redirect:/";
                }
            } else {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }

        } catch (Exception e) {
            logger.error("登录异常", e.getMessage());
            model.addAttribute("msg", "服务器错误");
            return "login";
        }

    }

    @RequestMapping(value = "/logout",method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }
}
