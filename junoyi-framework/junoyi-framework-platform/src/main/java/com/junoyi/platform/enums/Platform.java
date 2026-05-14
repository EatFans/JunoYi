package com.junoyi.platform.enums;

/**
 * 平台枚举
 *
 * @author Fan
 */
public enum Platform {

    WECHAT("微信", "wechat"),
    WEWORK("企业微信","wework"),
    FEISHU("飞书","feishu"),
    DINGTALK("钉钉","dingtalk");

    private final String label;

    private final String code;

    private Platform(String platformLabel, String platformCode){
        this.label = platformLabel;
        this.code = platformCode;
    }

    /**
     * 获取中文标签
     * @return 标签
     */
    public String getLabel() {
        return label;
    }

    /**
     * 获取唯一编码
     * @return 编码
     */
    public String getCode() {
        return code;
    }
}
