package com.qutopia.blog.entity;

/**
 * 用户互联信息，记录与第三方授权登陆的信息
 *
 * @author choaklin
 * @date 2019.01.25
 */
public class UserConnectInfoDO {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 第三方的用户ID
     */
    private String openId;

    /**
     * 第三方的Token
     */
    private String accessToken;

}
