package com.qutopia.blog.gateway.web.front;

import com.qutopia.blog.gateway.web.front.dto.LoginForm;
import com.qutopia.blog.service.UserService;
import com.qutopia.blog.utils.data.commons.UUIDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 登陆管理的控制器
 *
 * @author choaklin
 * @date 2019.01.13
 */
@Slf4j
@RestController
@RequestMapping("login")
public class LoginController {

    public static final String tokenKey = "token";

    @Autowired
    private UserService userService;

    /**
     * @return
     */
    @PostMapping
    public String doLogin(HttpSession session,  @Valid @RequestBody LoginForm loginForm) {

        String serverToken = (String) session.getAttribute(tokenKey);
        if (StringUtils.isBlank(serverToken)) {

            userService.login(loginForm);

            serverToken = UUIDGenerator.generate();
            session.setAttribute(tokenKey, serverToken);
        } else {
            log.warn(">> 别闹, 会话[{}]已登陆", session.getId());
        }
        return serverToken;
    }

    @GetMapping("logout")
    public String logout(HttpSession session, @RequestHeader(tokenKey) String clientToken) {

        String serverToken = (String) session.getAttribute(tokenKey);
        String msg = null;
        if (StringUtils.isBlank(serverToken)) {
            log.warn(">> Token[{}]的会话用户已注销过登陆", clientToken);
        } else {
            Assert.hasLength(clientToken, "会话Token不能空白");
            // 失效当前会话
            session.invalidate();
        }
        return msg;
    }
}
