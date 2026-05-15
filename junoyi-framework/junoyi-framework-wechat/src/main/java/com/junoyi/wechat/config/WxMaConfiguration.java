package com.junoyi.wechat.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.junoyi.wechat.auth.WeChatMpOauthProvider;
import com.junoyi.wechat.properties.WxMpProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信小程序配置类
 * <p>
 * 配置微信小程序的 WxMaService，用于调用微信小程序相关接口
 * </p>
 *
 * @author Fan
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(WxMpProperties.class)
@ConditionalOnProperty(prefix = "junoyi.wechat.mp", name = "enabled", havingValue = "true", matchIfMissing = false)
public class WxMaConfiguration {

    private final WxMpProperties wxMpProperties;

    /**
     * 配置微信小程序 Service
     *
     * @return WxMaService
     */
    @Bean
    public WxMaService wxMaService() {
        log.info("微信小程序功能已启用 [AppID: {}]", maskAppId(wxMpProperties.getAppId()));

        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(wxMpProperties.getAppId());
        config.setSecret(wxMpProperties.getSecret());

        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(config);

        return service;
    }

    /**
     * 配置微信小程序 OAuth Provider
     *
     * @param wxMaService 微信小程序 Service
     * @return WeChatMpOauthProvider
     */
    @Bean
    public WeChatMpOauthProvider weChatMpOauthProvider(WxMaService wxMaService) {
        log.info("微信小程序 OAuth Provider 已注册");
        return new WeChatMpOauthProvider(wxMaService);
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

