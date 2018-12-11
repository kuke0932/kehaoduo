package com.htcs.kehaoduo.system;

import com.htcs.kehaoduo.common.conf.RequestUserListener;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhaokaiyuan
 */
@MapperScan(value = {"com.htcs.kehaoduo.system.mapper", "com.htcs.kehaoduo.bs.mapper"})
@ComponentScan(basePackages = {"com.htcs.kehaoduo.*"}/*, excludeFilters ={ @ComponentScan.Filter(pattern = "com.htcs.kehaoduo.system.dao.*DAO")}*/)
@SpringBootApplication
public class App extends SpringBootServletInitializer {

    public static void main(String[] args) {
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