package com.qutopia.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
        excludeFilters = {
                @ComponentScan.Filter(
                    type = FilterType.ANNOTATION,
                    classes = {
                            Controller.class, RestController.class
                    })
        }
)
public class BootApplication {

    public static void main( String[] args ) {

        SpringApplication.run(BootApplication.class, args);
    }
}
