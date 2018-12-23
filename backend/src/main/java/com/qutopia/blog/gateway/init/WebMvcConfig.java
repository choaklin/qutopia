package com.qutopia.blog.gateway.init;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;


/**
 * 动态增加WebServlet Bean
 *
 * @author choaklin
 * @version 0.0.1
 * @date 2018/9/1
 */
@Configuration
public class WebMvcConfig {

    /**
     * 设置文件的解析器为： {@link CommonsMultipartResolver}替换默认的{@link StandardServletMultipartResolver}
     *
     * @return
     * @see org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration
     */
    @Bean(name = DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME)
    public MultipartResolver multipartResolver() {

        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        //resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
        resolver.setResolveLazily(true);
        resolver.setMaxInMemorySize(40960);
        //上传文件大小 5M 5*1024*1024
        resolver.setMaxUploadSize(5 * 1024 * 1024);
        return resolver;
    }
}
