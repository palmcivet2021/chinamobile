package com.chinamobile.auth.util;

import com.chinamobile.auth.bean.Audience;
import com.chinamobile.auth.exception.CustomException;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.Key;
import java.util.Date;

/**
 * ========================
 * Created with IntelliJ IDEA.
 * User：pyy
 * Date：2019/7/17 17:24
 * Version: v1.0
 * ========================
 */
public class JwtTokenUtil {

    private static Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);

    //public static final String AUTH_HEADER_KEY = "access_token";
    public static final String AUTH_HEADER_KEY = "accessToken";

    public static final String TOKEN_PREFIX = "";

    /**
     * 解析jwt
     * @param jsonWebToken
     * @param base64Security
     * @return
     */
    public static Claims parseJWT(String jsonWebToken, String base64Security)  throws Exception{
        BASE64Encoder encoder = new BASE64Encoder();
        base64Security = encoder.encode(base64Security.getBytes());
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                .parseClaimsJws(jsonWebToken).getBody();
        return claims;

        /*try {
            BASE64Encoder encoder = new BASE64Encoder();
            base64Security = encoder.encode(base64Security.getBytes());
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (SignatureException se) {
            // TODO 这里自行抛出异常
            log.error("===== 密钥不匹配 =====", se);
        } catch (ExpiredJwtException  eje) {
            log.error("===== Token过期 =====", eje);
            //throw new ExpiredJwtException();
            throw new CustomException(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage());
        } catch (Exception e){
            log.error("===== token解析异常 =====", e);
            //throw new CustomException(ResultCode.PERMISSION_TOKEN_INVALID);
        }
        return null;*/
    }

    /**
     * 构建jwt
     * @param userId
     * @param username
     * @param role
     * @param audience
     * @return
     */
    public static String createJWT(String userId, String username, String role, Audience audience) {
        try {
            // 使用HS256加密算法
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            BASE64Encoder encoder = new BASE64Encoder();

            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);

            //生成签名密钥
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(encoder.encode(audience.getSecret().getBytes()));
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

            //userId是重要信息，进行加密下
            String encryId = encoder.encode(userId.getBytes());

            //添加构成JWT的参数
            JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                    // 可以将基本不重要的对象信息放到claims
                    .claim("role", role)
                    .claim("userId", userId)
                    .setSubject(username)           // 代表这个JWT的主体，即它的所有人
                    //.setIssuer(audience.getClientId())              // 代表这个JWT的签发主体；
                    .setIssuedAt(new Date())        // 是一个时间戳，代表这个JWT的签发时间；
                    //.setAudience(audience.getName())          // 代表这个JWT的接收对象；
                    .signWith(signatureAlgorithm, signingKey);
            //添加Token过期时间
            int TTLMillis = audience.getExpire() * 60 * 1000;
            if (TTLMillis >= 0) {
                long expMillis = nowMillis + TTLMillis;
                Date exp = new Date(expMillis);
                builder.setExpiration(exp)  // 是一个时间戳，代表这个JWT的过期时间；
                        .setNotBefore(now); // 是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的
            }

            //生成JWT
            return builder.compact();
        } catch (Exception e) {
            log.error("签名失败", e);
            //throw new CustomException(ResultCode.PERMISSION_SIGNATURE_ERROR);
        }
        return null;
    }

    /**
     * 从token中获取用户名
     * @param token
     * @param base64Security
     * @return
     */
    public static String getUsername(String token, String base64Security) throws Exception{
        return parseJWT(token, base64Security).getSubject();
    }

    /**
     * 从token中获取用户ID
     * @param token
     * @param base64Security
     * @return
     */
    public static String getUserId(String token, String base64Security) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        String userId = parseJWT(token, base64Security).get("userId", String.class);
        byte[] decodeResult = decoder.decodeBuffer(userId);
        //return Base64Util.decode(userId);
        return new String(decodeResult);
    }

    /**
     * 是否已过期
     * @param token
     * @param base64Security
     * @return
     */
    public static boolean isExpiration(String token, String base64Security)  throws Exception{
        Date expireDate = parseJWT(token, base64Security).getExpiration();
        return parseJWT(token, base64Security).getExpiration().before(new Date());
    }


    /**
     * 获取原始token信息
     * @param authorizationHeader 授权头部信息
     * @return
     */
    public static String getRawToken(String authorizationHeader) {
        return authorizationHeader.substring(TOKEN_PREFIX.length());
    }

    /**
     * 获取授权头部信息
     * @param rawToken token信息
     * @return
     */
    public static String getAuthorizationHeader(String rawToken) {
        return TOKEN_PREFIX + rawToken;
    }

    /**
     * 校验授权头部信息格式合法性
     * @param authorizationHeader 授权头部信息
     * @return
     */
    public static boolean validate(String authorizationHeader) {
        return StringUtils.hasText(authorizationHeader)
                && authorizationHeader.startsWith(TOKEN_PREFIX);
    }

}