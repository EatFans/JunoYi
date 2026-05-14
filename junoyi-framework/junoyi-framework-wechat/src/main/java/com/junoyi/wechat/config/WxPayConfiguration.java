package com.junoyi.wechat.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.junoyi.wechat.properties.WxPayProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        log.info("微信支付功能已启用 [商户号: {}]", maskMerchantId(wxPayProperties.getMerchantId()));

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

    /**
     * 脱敏商户号，只显示前4位和后4位
     */
    private String maskMerchantId(String merchantId) {
        if (merchantId == null || merchantId.length() <= 8) {
            return "****";
        }
        return merchantId.substring(0, 4) + "****" + merchantId.substring(merchantId.length() - 4);
    }
}

