package com.junoyi.wework.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.junoyi.wework.properties.WxPayProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信支付配置类
 * <p>
 * 配置微信支付的 WxPayService，用于调用微信支付相关接口
 * </p>
 *
 * @author Fan
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(WxPayProperties.class)
@ConditionalOnProperty(prefix = "junoyi.wechat.pay", name = "enabled", havingValue = "true", matchIfMissing = false)
public class WxPayConfiguration {

    private final WxPayProperties wxPayProperties;

    /**
     * 配置微信支付 Service
     *
     * @return WxPayService
     */
    @Bean
    public WxPayService wxPayService() {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(wxPayProperties.getAppId());
        payConfig.setMchId(wxPayProperties.getMerchantId());
        payConfig.setApiV3Key(wxPayProperties.getApiV3Key());
        payConfig.setPrivateKeyPath(wxPayProperties.getPrivateKeyPath());
        payConfig.setPrivateCertPath(wxPayProperties.getPrivateCertPath());
        payConfig.setCertSerialNo(wxPayProperties.getCertSerialNo());

        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);

        return wxPayService;
    }
}

