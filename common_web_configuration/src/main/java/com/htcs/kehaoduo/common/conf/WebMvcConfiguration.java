package com.htcs.kehaoduo.common.conf;


import com.htcs.kehaoduo.common.converter.DateConverter;
import com.htcs.kehaoduo.common.converter.UniversalEnumConverterFactory;
import com.htcs.kehaoduo.common.interceptor.AdminInterceptor;
import com.htcs.kehaoduo.common.interceptor.WebInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by htcs on 2017/7/26.
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public AdminInterceptor adminInterceptor() {
        return new AdminInterceptor();
    }

    @Bean
    public WebInterceptor webInterceptor() {
        return new WebInterceptor();
    }

    /**
     * 添加jar包中static资源
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    /**
     * 添加cors
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
                .allowCredentials(false).maxAge(3600);
    }

    /**
     * 配置拦截器
     *
     * @param registry
     * @author zhaokaiyuan
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(adminInterceptor()).addPathPatterns("/admin/**", "/admin/**/*")
                .excludePathPatterns("/admin/login", "/admin/sync/sync");
        registry.addInterceptor(webInterceptor()).addPathPatterns("/web/**", "/web/**/*")
                .excludePathPatterns(
                        "/web/login",
                        "/web/getUserOpenId",
                        "/web/register",
                        "/web/isRegistered",
                        "/web/autoLogin",
                        "/web/logout",
                        "/web/sendSms",
                        "/web/forgetPwd",
                        "/web/publicity/uploadImg",
                        "/web/publicity/queryContent4Click",
                        "/web/publicity/insertPhone",
                        "/web/publicity/insertPublicityReadLog");
    }

    /**
     * 添加enum枚举转换器
     *
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new UniversalEnumConverterFactory());
        registry.addConverter(new DateConverter());
    }

}
