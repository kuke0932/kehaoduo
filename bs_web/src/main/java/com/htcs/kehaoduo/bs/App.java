package com.htcs.kehaoduo.bs;


import com.htcs.kehaoduo.common.conf.RequestUserListener;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author zhaokaiyuan
 * @create 2017-10-19 17:29
 **/
@MapperScan(value = {"com.htcs.kehaoduo.bs.mapper", "com.htcs.kehaoduo.system.mapper"})
@ComponentScan(basePackages = {"com.htcs.kehaoduo.*"}, excludeFilters ={ @ComponentScan.Filter(pattern = "com.htcs.kehaoduo.wechat.controller.*", type = FilterType.REGEX)})
@SpringBootApplication
public class App extends SpringBootServletInitializer {

    public static void main(String[] args) {
        System.setProperty("jsse.enableSNIExtension", "false");
        SpringApplication.run(App.class);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(App.class);
    }

    @Bean
    ServletListenerRegistrationBean requestUserListener() {
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(new RequestUserListener());
        return servletListenerRegistrationBean;
    }
}

