package com.newcoder.duihua.configuration;

import com.newcoder.duihua.interceptor.LoginRequiredInterceptor;
import com.newcoder.duihua.interceptor.PassportIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by wangdong on 2017/8/17.
 */
@Component
public class DuihuaConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;

    @Autowired
    PassportIntercepter passportIntercepter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportIntercepter);
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/user/*");
        super.addInterceptors(registry);
    }
}
