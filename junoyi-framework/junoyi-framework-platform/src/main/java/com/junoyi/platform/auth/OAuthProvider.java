package com.junoyi.platform.auth;

import com.junoyi.platform.enums.Platform;

/**
 * OAuth认证提供者接口
 * <p>
 * 该接口定义了第三方OAuth认证提供者的基本契约，用于统一管理和集成不同的OAuth认证服务，
 * 如微信、企业微信、钉钉等第三方平台的授权认证。
 * </p>
 *
 * @author Fan
 */
public interface OAuthProvider {

    /**
     * 获取平台标识
     * @return 返回平台标识
     */
    Platform getPlatform();

    /**
     * 获取能力类型
     * @return 能力类型
     */
    String getCapability();
}
