package com.newcoder.duihua.service;

import com.newcoder.duihua.Utils.DuihuaUtil;
import com.newcoder.duihua.dao.LoginTicketDao;
import com.newcoder.duihua.dao.UserDao;
import com.newcoder.duihua.model.LoginTicket;
import com.newcoder.duihua.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by wangdong on 2017/8/3.
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    LoginTicketDao loginTicketDao;

    public User getUser(int id) {
       return userDao.selectById(id);
    }

    public Map<String,Object> register(String username,String password) {
        Map<String,Object> map = new HashMap<String,Object>();
        if (StringUtils.isEmpty(username)) {
            map.put("msg","用户名不能为空");
        }
        if(StringUtils.isEmpty(password)) {
            map.put("msg","密码不能为空");

        }

        //用户名不能重复
        User  user = userDao.selectByName(username);
        if (user!=null) {
            map.put("msg","用户名已存在");
        }

        //要对密码进行加密
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setPassword(DuihuaUtil.MD5((password+user.getSalt())));
        String head = String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000));
        user.setHeadUrl(head);
        userDao.addUser(user);
        //这里是注册完成就自动完成登陆。
        //登录
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    private String addLoginTicket(int userId) {
        LoginTicket lticket = new LoginTicket();
        lticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime()+24*3600*1000);//将过期时间设为1000天后
        lticket.setExpired(date);
        lticket.setStatus(0);//状态设为0
        lticket.setTicket(UUID.randomUUID().toString().replace("-",""));//ticket为随机生成的数
        loginTicketDao.addTicket(lticket);//存入数据库
        return lticket.getTicket();//此过程可以设置其他的属性，比直接得到ticket更加好。

    }
    //登录
    public Map<String,Object> login(String username,String password) {
        Map<String,Object> map = new HashMap<String,Object>();
        if (StringUtils.isEmpty(username)) {
            map.put("msg","用户名不能为空");
            return map;
        }
        if (StringUtils.isEmpty(password)) {
            map.put("msg","密码不能为空");
            return map;
        }
        User user = userDao.selectByName(username);
        if (user==null) {
            map.put("msg","用户名不存在");
            return map;
        }
        if (!DuihuaUtil.MD5((password+user.getSalt())).equals(user.getPassword())) {
            map.put("msg","密码错误");
            return map;
        }
        //所有情况排除，就是登录成功的情况
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }
    //登出
    public void logout(String ticket) {
        loginTicketDao.updateStatus(1,ticket);
    }

}
