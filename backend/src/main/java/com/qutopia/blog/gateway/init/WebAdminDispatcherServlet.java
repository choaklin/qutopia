package com.qutopia.blog.gateway.init;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * 匹配 [web admin]请求
 *
 * @author choaklin
 * @date 2018.11.11
 */
@Configuration
public class WebAdminDispatcherServlet implements ServletContextInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        XmlWebApplicationContext webContext = new XmlWebApplicationContext();
        webContext.setConfigLocation("classpath:spring/spring-mvc-web-admin.xml");

        ServletRegistration.Dynamic registration = servletContext.addServlet(
                "web-admin", new DispatcherServlet(webContext)
        );
        registration.addMapping("/web/admin/*");
        registration.setLoadOnStartup(1);
    }
}
