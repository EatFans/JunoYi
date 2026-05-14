package com.junoyi.wework.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信小程序配置参数
 *
 * @author Fan
 */
@Data
@Component
@ConfigurationProperties(prefix = "junoyi.wechat.mp")
public class WxMpProperties {

    private String appId;

    private String secret;
}