package com.qutopia.blog.entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户类型
 *
 * @author choaklin
 * @date 2018.10.6
 */
public enum UserType {

    /**
     * 管理员
     */
    ADMIN(1),

    /**
     * 淘客
     */
    TAOKE(2);

    private int value;
    public int getValue() {
        return value;
    }

    UserType(int value) {
        this.value = value;
    }


    private static Map<Integer, UserType> userTypeMap = new HashMap<>();
    static {
        Arrays.stream(UserType.values()).forEach(channel -> userTypeMap.put(channel.getValue(), channel));
    }

    public static UserType from(int value) {
        UserType userType = userTypeMap.get(value);

        if (userType == null) {
            throw new NullPointerException("no instance from [" + UserType.class + "] of :" + value);
        }
        return userType;
    }
}
