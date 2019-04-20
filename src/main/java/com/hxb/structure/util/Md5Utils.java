package com.hxb.structure.util;

import com.hxb.structure.constant.CommonConstants;
import com.hxb.structure.exception.UtilException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * @author create by huang xiao bao
 * @date 2019-04-20 17:05:30
 */
public class Md5Utils {

    /**
     * 加密字符串为32位密文
     * @param plainText 待加密字符串
     * @return 密文
     */
    public static String md5(String plainText) {
        if(Objects.isNull(plainText)){
            throw new UtilException("plainText not be null");
        }
        //定义一个字节数组
        byte[] secretBytes;
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance(CommonConstants.MD5);
            //对字符串进行加密
            md.update(plainText.getBytes());
            //获得加密后的数据
            secretBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new UtilException("没有md5这个算法！");
        }
        //将加密后的数据转换为16进制数字
        String md5code = new BigInteger(1, secretBytes).toString(CommonConstants.HEX);
        StringBuilder res = new StringBuilder(md5code);
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            res.insert(0,CommonConstants.STRING_ZERO);
        }
        return res.toString();
    }


    private Md5Utils(){}
}
