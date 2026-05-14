package com.junoyi.wework.properties;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

    @PostConstruct
    public void init() {
        if (Boolean.TRUE.equals(enabled)) {
            log.info("WeChat mini program function is enabled [AppID: {}]", maskAppId(appId));
        } else {
            log.info("WeChat mini program function is not enabled");
        }
    }

    /**
     * 脱敏 AppID，只显示前4位和后4位
     */
    private String maskAppId(String appId) {
        if (appId == null || appId.length() <= 8) {
            return "****";
        }
        return appId.substring(0, 4) + "****" + appId.substring(appId.length() - 4);
    }
}