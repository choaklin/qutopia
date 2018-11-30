package com.qutopia.blog.gateway;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * 匹配web请求
 *
 * @author choaklin
 * @date 2018.11.11
 */
// @Configuration
public class WebDispatcherServletInitializer implements ServletContextInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        XmlWebApplicationContext webContext = new XmlWebApplicationContext();
        webContext.setConfigLocation("classpath:spring/spring-mvc-web.xml");

        ServletRegistration.Dynamic registration = servletContext.addServlet(
                "spring-mvc-web", new DispatcherServlet(webContext)
        );
        registration.addMapping("/web/*");
        registration.setLoadOnStartup(1);
    }
}
