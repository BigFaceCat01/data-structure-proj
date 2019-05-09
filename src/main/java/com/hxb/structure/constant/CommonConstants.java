package com.hxb.structure.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author create by huang xiao bao
 * @date 2019-04-20 17:07:39
 */
public final class CommonConstants {
    /**
     * 16 进制
     */
    public static final int HEX = 16;
    /**
     * 30分钟对应的毫秒数
     */
    public static final int THIRTY_MINUTES_MILLISECOND = 30*60*1000;
    public static final String STRING_ZERO = "0";
    public static final String MD5 = "MD5";
    public static final String DOT = ".";
    public static final String SEPARATOR = "/";
    public static final String PROTOCOL_FILE = "file";
    public static final String PROTOCOL_JAR = "jar";
    public static final String DOT_CLASS = ".class";
    public static final String EMPTY_STRING = "";
    public static final String JWT_SECRET = "23#Acv(!468ME@kjhJHKlj.0000";
    public static final String JWT_CONTENT = "sub";
    public static final String JWT_EXPIRE = "exp";
    public static final Map<String,Object> JWT_HEADER = new HashMap<>();

    static {
        JWT_HEADER.put("alg", "HS256");
        JWT_HEADER.put("typ", "JWT");
    }

}
