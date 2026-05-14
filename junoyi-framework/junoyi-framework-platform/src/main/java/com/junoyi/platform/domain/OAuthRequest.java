package com.junoyi.platform.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * OAuth 授权请求对象
 * <p>
 * 封装了 OAuth 授权所需的通用参数，适用于微信、企业微信、钉钉、飞书等多种平台
 * </p>
 *
 * @author Fan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuthRequest {

    /**
     * 授权码
     * <p>
     * 用户授权后返回的临时授权码，用于换取access_token
     * </p>
     */
    private String code;

    /**
     * 应用ID
     * <p>
     * 第三方平台分配的应用ID（如微信的appid）
     * </p>
     */
    private String appId;

    /**
     * 应用密钥
     * <p>
     * 第三方平台分配的应用密钥（如微信的secret）
     * </p>
     */
    private String appSecret;

    /**
     * 回调地址
     * <p>
     * 授权成功后的回调地址
     * </p>
     */
    private String redirectUri;

    /**
     * 授权范围
     * <p>
     * 应用授权作用域，多个用逗号分隔（如：snsapi_base,snsapi_userinfo）
     * </p>
     */
    private String scope;

    /**
     * 状态参数
     * <p>
     * 用于保持请求和回调的状态，授权请求后原样带回给第三方
     * </p>
     */
    private String state;

    /**
     * 扩展参数
     * <p>
     * 用于存放各平台特有的扩展参数
     * </p>
     */
    private Map<String, Object> extraParams;
}

