package cn.jl.myweb.configuration;

import cn.jl.myweb.interceptor.Loginlnterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class LoginInterceptorConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截路径：必须登录才可以访问
        List<String> patterns = new ArrayList<>();
        patterns.add("/**");

        //白名单：在黑名单范围内，却不需要登录就可以访问
        List<String> excludePatterns = new ArrayList<>();
        excludePatterns.add("/bootstrap3/**");
        excludePatterns.add("/css/**");
        excludePatterns.add("/js/**");
        excludePatterns.add("/images/**");

        excludePatterns.add("/web/register.html");
        excludePatterns.add("/user/reg");
        excludePatterns.add("/web/login.html");
        excludePatterns.add("/user/login");

        //注册拦截器
        registry
                .addInterceptor(new Loginlnterceptor())
                .addPathPatterns(patterns)
                .excludePathPatterns(excludePatterns);
    }
}
