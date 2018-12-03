package com.qutopia.blog.gateway.init;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * 动态增加WebServlet Bean
 *
 * @author choaklin
 * @version 0.0.1
 * @date 2018/9/1
 */
@Slf4j
// @Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

        resolvers.add(new PageableHandlerMethodArgumentResolver());
    }
}
