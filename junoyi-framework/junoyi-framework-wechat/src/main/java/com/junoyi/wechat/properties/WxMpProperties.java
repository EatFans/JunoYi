package com.junoyi.wechat.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信小程序配置参数
 * <p>
 * 配置微信小程序的 AppID 和 Secret，用于调用微信小程序相关接口
 * </p>
 *
 * @author Fan
 */
@Data
@Component
@ConfigurationProperties(prefix = "junoyi.wechat.mp")
public class WxMpProperties {

    /**
     * 是否启用微信小程序功能
     */
    private Boolean enabled = false;

    /**
     * 小程序 AppID
     */
    private String appId;

    /**
     * 小程序 AppSecret
     */
    private String secret;
}