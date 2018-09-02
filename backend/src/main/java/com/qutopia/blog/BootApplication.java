package com.qutopia.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 */
@SpringBootApplication(
//        scanBasePackages = {
////                "com.qutopia.blog.repository",
////                "com.qutopia.blog.service",
////                "com.qutopia.blog.utils"
////        }
)
public class BootApplication {

    public static void main( String[] args ) {

        SpringApplication.run(BootApplication.class, args);
    }
}
