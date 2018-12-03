package com.qutopia.blog.gateway.init;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * 匹配 [web front]请求
 *
 * @author choaklin
 * @date 2018.11.11
 */
@Configuration
public class WebFrontDispatcherServlet implements ServletContextInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        XmlWebApplicationContext webContext = new XmlWebApplicationContext();
        webContext.setConfigLocation("classpath:spring/spring-mvc-web-front.xml");

        ServletRegistration.Dynamic registration = servletContext.addServlet(
                "web-front", new DispatcherServlet(webContext)
        );

        // 简化访问路径
        registration.addMapping("/*");
        registration.setLoadOnStartup(1);
    }
}
