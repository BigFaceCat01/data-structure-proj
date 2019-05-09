package com.hxb.structure.util;

import com.hxb.structure.constant.CommonConstants;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-09 10:02:07
 */
@Slf4j
public class JwtUtils {
    /**
     * 签发JWT
     * @param content JSON数据字符串
     * @param expire token 过期时间
     * @param unit 时间单位
     * @return  String
     *
     */
    public static String createJWT(String content, long expire, TimeUnit unit) {
        long nowMillis = System.currentTimeMillis();
        long timeout = nowMillis + (expire <= 0 ? CommonConstants.THIRTY_MINUTES_MILLISECOND : TimeUnit.MILLISECONDS.convert(expire,unit));
        Date currentTime = new Date(nowMillis);
        Date expireTime = new Date(timeout);
        Claims claims = Jwts.claims();
        claims.setSubject(content);
        claims.setExpiration(expireTime);
        claims.setIssuedAt(currentTime);
        // build token
        return Jwts.builder()
                // jwt 头信息
                .setHeader(CommonConstants.JWT_HEADER)
                // jwt 消息体
                .setClaims(claims)
                // jwt 生成时间
                .setIssuedAt(currentTime)
                // jwt 过期时间
                .setExpiration(expireTime)
                // jwt 签名
                .signWith(SignatureAlgorithm.HS512,CommonConstants.JWT_SECRET)
                .compact();
    }


    /**
     * 验证Token正确性
     * @param token token
     * @return true or false
     */
    public static boolean checkToken(String token) {
        try {
            Jwts.parser().setSigningKey(CommonConstants.JWT_SECRET).parseClaimsJws(token);
            return true;
        }catch (SignatureException e){
            log.error("token = {} 签名可能被窜改",token,e);
        }catch (ExpiredJwtException e){
            log.error("token = {} 已过期",token,e);
        }catch (Exception e) {
            log.error("token = {} 不合法",token,e);
        }
        return false;
    }

    /**
     * 获得jwt 消息体
     * @param token token
     */
    public static void tokenInfo(String token,TokenParseCallback callback) {
        Jws<Claims> claimsJws = null;
        try {
            claimsJws = Jwts.parser().setSigningKey(CommonConstants.JWT_SECRET).parseClaimsJws(token);
        } catch (Exception e) {
            log.error("token = {} 校验失败",token,e);
        }
        callback.tokenContent(claimsJws.getBody().getSubject());
        callback.tokenExpire(claimsJws.getBody().getExpiration());
    }

    /**
     * token解析回调
     */
    public interface TokenParseCallback{
        /**
         * token 消息体回调
         * @param content 消息体
         */
        void tokenContent(String content);

        /**
         * token 过期时间回调
         * @param date 时间
         */
        void tokenExpire(Date date);
    }

    public static void main(String[] args) {
        String jwt = createJWT("{\"userId\":\"1238464646\"}", -1, TimeUnit.SECONDS);
        System.out.println(jwt);
        System.out.println(checkToken(jwt));
        JwtUtils.tokenInfo(jwt,new TokenParseCallback(){

            @Override
            public void tokenContent(String content) {
                System.out.println(content);

            }

            @Override
            public void tokenExpire(Date date) {
                System.out.println(date);
            }
        });
    }


    private JwtUtils(){}
}
