package com.chinamobile.auth.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "jwt")
@Component
public class Audience {

    /**
     * 加密秘钥
     */
    private String secret;
    /**
     * 有效时间
     */
    private int expire;  //单位秒
    /**
     * 用户凭证
     */
    private String header;
}
