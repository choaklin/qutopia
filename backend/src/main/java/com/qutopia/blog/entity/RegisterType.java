package com.qutopia.blog.entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户的注册方式
 *
 * @author choaklin
 * @date 2019.1.24
 */
public enum RegisterType {

    /**
     * 初始化
     */
    INITIALIZE(1),

    /**
     * 平台注册, 暂不开放
     */
    PLATFORM(2),

    /**
     * QQ
     */
    QQ(3),

    /**
     * 微信
     */
    WEIXIN(4),

    /**
     * 微博
     */
    WEIBO(5),

    /**
     * github
     */
    GITHUB(6);

    private int value;
    public int getValue() {
        return value;
    }

    RegisterType(int value) {
        this.value = value;
    }


    private static Map<Integer, RegisterType> registerTypeMap = new HashMap<>();
    static {
        Arrays.stream(RegisterType.values()).forEach(channel -> registerTypeMap.put(channel.getValue(), channel));
    }

    public static RegisterType from(int value) {
        RegisterType registerType = registerTypeMap.get(value);

        if (registerType == null) {
            throw new NullPointerException("no instance from [" + RegisterType.class + "] of :" + value);
        }
        return registerType;
    }
}