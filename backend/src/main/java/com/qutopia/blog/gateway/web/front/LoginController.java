package com.qutopia.blog.gateway.web.front;

import com.qutopia.blog.utils.data.commons.UUIDGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登陆管理的控制器
 *
 * @author choaklin
 * @date 2019.01.13
 */
@RestController
@RequestMapping("login")
public class LoginController {


    /**
     * @return
     */
    @PostMapping
    public String doLogin() {

        return UUIDGenerator.generate();
    }
}
