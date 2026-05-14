package com.junoyi.platform.enums;

/**
 * 支付交易类型枚举
 * <p>
 * 定义了各种支付场景的交易类型，适用于微信支付、支付宝支付等多种支付渠道
 * </p>
 *
 * @author Fan
 */
public enum TradeType {

    /**
     * JSAPI支付（或小程序支付）
     * <p>
     * 用户在微信公众号、小程序内打开商户H5网页，商户在H5网页通过调用微信支付提供的JSAPI接口调起微信支付模块来完成支付
     * </p>
     */
    JSAPI("JSAPI支付", "JSAPI"),

    /**
     * APP支付
     * <p>
     * 商户APP调用微信支付提供的SDK调起微信支付模块来完成支付
     * </p>
     */
    APP("APP支付", "APP"),

    /**
     * H5支付
     * <p>
     * 用户在微信以外的手机浏览器请求微信支付的场景唤起微信支付
     * </p>
     */
    H5("H5支付", "H5"),

    /**
     * Native支付（扫码支付）
     * <p>
     * 商户生成二维码，用户扫描二维码后在微信内完成支付
     * </p>
     */
    NATIVE("扫码支付", "NATIVE"),

    /**
     * 小程序支付
     * <p>
     * 用户在小程序中使用微信支付的场景
     * </p>
     */
    MINI_PROGRAM("小程序支付", "MINI_PROGRAM");

    /**
     * 交易类型描述
     */
    private final String label;

    /**
     * 交易类型编码
     */
    private final String code;

    TradeType(String label, String code) {
        this.label = label;
        this.code = code;
    }

    /**
     * 获取交易类型描述
     *
     * @return 交易类型描述
     */
    public String getLabel() {
        return label;
    }

    /**
     * 获取交易类型编码
     *
     * @return 交易类型编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 根据编码获取交易类型
     *
     * @param code 交易类型编码
     * @return 交易类型枚举，如果不存在则返回 null
     */
    public static TradeType fromCode(String code) {
        for (TradeType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}

