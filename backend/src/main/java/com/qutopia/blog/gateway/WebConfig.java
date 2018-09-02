package com.qutopia.blog.gateway;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * 动态增加WebServlet Bean
 *
 * @author choaklin
 * @version 0.0.1
 * @date 2018/9/1
 */
@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {

        configurer.setUseSuffixPatternMatch(false)
                .setUseTrailingSlashMatch(false);
    }


    public ServletRegistrationBean getWebServlet() {

        XmlWebApplicationContext webContext = new XmlWebApplicationContext();
        webContext.setConfigLocation("classpath:spring/spring-mvc-web.xml");
        DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);

        ServletRegistrationBean webServletBean = new ServletRegistrationBean();
        webServletBean.setName("spring-web-mvc");
        webServletBean.setServlet(dispatcherServlet);
        webServletBean.setUrlMappings(Arrays.asList("/web/*"));
        webServletBean.setLoadOnStartup(1);
        return webServletBean;
    }
}
