package com.robbad.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: tenmallfront
 * @description:
 * @author: niyao
 * @create: 2019-11-22 18:38
 */
@Configuration
public class WxWebMvcConfig implements WebMvcConfigurer {

    /**
     * 用户拦截器
     * @param registry
     */

    //自定义
    @Autowired
    TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(tokenInterceptor)
       .addPathPatterns("/GrabASingleList")
       .addPathPatterns("/occupied")
       .addPathPatterns("/purchase")
       .addPathPatterns("/DirectDrive")
       .addPathPatterns("/grondstoffenlijst")
       .addPathPatterns("/particularsRequest")
       .excludePathPatterns("/login");


    }

    /**
     * 静态资源路径配置
     * @param registry
     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//    }

    /**
     * 跨域配置
     * @param
     */
//    private CorsConfiguration buildConfig(){
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedHeader("*"); // 允许任何的head头部
//        corsConfiguration.addAllowedOrigin("*"); // 允许任何域名使用
//        corsConfiguration.addAllowedMethod("*"); // 允许任何的请求方法
//        corsConfiguration.setAllowCredentials(true);
//        return corsConfiguration;
//    }

    // 添加CorsFilter拦截器，对任意的请求使用
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", buildConfig());
//        return new CorsFilter(source);
//    }
}
