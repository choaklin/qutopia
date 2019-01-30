package com.qutopia.blog.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 *
 * @author choaklin
 * @date 2019.01.24
 */
@Data
@Document(collection = CollectionNames.USER)
public class UserDO implements Serializable {

    /**
     * 用户ID
     */
    @Id
    private String id;

    /**
     * 用户类型
     * @see UserType
     */
    private int userType;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像地址
     */
    private String figureUrl;

    /**
     * 登陆输入
     */
    private String loginInput;

    /**
     * 登陆密码
     */
    private String password;

    /**
     * 注册方式
     *
     * @see RegisterType
     */
    private int registerType;

    /**
     * 注册时间
     */
    private Date createTime;

}