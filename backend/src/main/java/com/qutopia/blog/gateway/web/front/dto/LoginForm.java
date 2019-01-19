package com.qutopia.blog.gateway.web.front.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author choaklin
 * @date 2019.01.19
 */
@Data
public class LoginForm {

    /**
     * 登陆输入
     */
    @NotBlank(message = "登陆账号不能空白")
    private String loginInput;

    /**
     * 登陆密码
     */
    @NotBlank(message = "登陆密码不能空白")
    private String password;
}
