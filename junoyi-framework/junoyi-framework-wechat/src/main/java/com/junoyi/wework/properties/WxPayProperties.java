package com.junoyi.wework.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信支付配置参数
 *
 * @author Fan
 */
@Data
@Component
@ConfigurationProperties(prefix = "junoyi.wechat.pay")
public class WxPayProperties {

    private String appId;

    private String merchantId;

    private String keyPath;

    private String apiV3Key;

    private String privateKeyPath;

    private String privateCertPath;

    private String certSerialNo;

    private String publicKeyId;

    private String publicKeyPath;

    private String payNotifyUrl;

    private String refundNotifyUrl;
}