package com.yingu.framework.net.des;

/**
 * author: create by qdwang
 * date: 2018/8/28 11:47
 * described：
 */
public class EncipherProxy {
    static final String format = "";
    static IEnciphter enciphter = null;

    public EncipherProxy() {
    }

    public static void init(IEnciphter e) {
        enciphter = e;
    }

    public static String encrypt(String json) throws Exception {
        return enciphter == null ? json : enciphter.encrypt(json);
    }

    public static String decrypt(String str) throws Exception {
        return enciphter == null ? str : enciphter.decrypt(str);
    }
}
