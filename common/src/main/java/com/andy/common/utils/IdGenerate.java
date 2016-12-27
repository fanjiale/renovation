package com.andy.common.utils;

import java.util.UUID;

/**
 * UUID生成唯一串
 * @author root
 */
public class IdGenerate {

    /**
     * 以UUID的策略生成一个32长度的字符串，在同一时空中保持唯一。
     * @return UUID128位长度字符串。
     */
    public static String getUUIDString() {
        String id = UUID.randomUUID().toString();
        id = id.replace("-", "");
        return id;
    }
}
