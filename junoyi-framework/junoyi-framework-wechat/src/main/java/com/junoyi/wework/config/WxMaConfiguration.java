package com.junoyi.wework.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.junoyi.wework.properties.WxMpProperties;
import lombok.RequiredArgsConstructor;
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
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(wxMpProperties.getAppId());
        config.setSecret(wxMpProperties.getSecret());

        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(config);

        return service;
    }
}

