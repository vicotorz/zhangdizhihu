package com.zhihu.configuration;


import com.zhihu.interceptor.LoginRequiredInterceptor;
import com.zhihu.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by dell on 2016/7/25.
 * 代理模式
 * 所有监听器需要在这里进行注册
 * addPathPatterns(...)写监听httprequest的路径
 */
@Component
public class WendaWebConfiguration extends WebMvcConfigurerAdapter {
    //注入自己的拦截器
    @Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);//没有写监听路径
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/user/*");
        super.addInterceptors(registry);
    }
}
