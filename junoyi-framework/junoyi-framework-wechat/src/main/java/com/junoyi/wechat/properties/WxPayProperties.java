package com.junoyi.wechat.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信支付配置参数
 * <p>
 * 配置微信支付的相关参数，包括商户号、API密钥、证书路径等
 * </p>
 *
 * @author Fan
 */
@Data
@Component
@ConfigurationProperties(prefix = "junoyi.wechat.pay")
public class WxPayProperties {

    /**
     * 是否启用微信支付功能
     */
    private Boolean enabled = false;

    /**
     * 微信支付分配的公众账号ID（企业号corpid即为此appId）
     */
    private String appId;

    /**
     * 微信支付分配的商户号
     */
    private String merchantId;

    /**
     * API密钥路径（V2版本使用）
     */
    private String keyPath;

    /**
     * APIv3密钥（V3版本使用）
     */
    private String apiV3Key;

    /**
     * 商户私钥文件路径
     */
    private String privateKeyPath;

    /**
     * 商户证书文件路径
     */
    private String privateCertPath;

    /**
     * 商户证书序列号
     */
    private String certSerialNo;

    /**
     * 微信支付平台证书公钥ID
     */
    private String publicKeyId;

    /**
     * 微信支付平台证书公钥路径
     */
    private String publicKeyPath;

    /**
     * 支付成功回调通知地址
     */
    private String payNotifyUrl;

    /**
     * 退款成功回调通知地址
     */
    private String refundNotifyUrl;
}