package com.junoyi.platform.auth;

import com.junoyi.platform.domain.OAuthRequest;
import com.junoyi.platform.domain.OAuthResponse;
import com.junoyi.platform.enums.Platform;

/**
 * OAuth认证提供者接口
 * <p>
 * 该接口定义了第三方OAuth认证提供者的基本契约，用于统一管理和集成不同的OAuth认证服务，
 * 如微信、企业微信、钉钉、飞书等第三方平台的授权认证。
 * </p>
 * <p>
 * 实现该接口的类需要提供具体的OAuth授权流程实现，包括：
 * - 生成授权URL
 * - 通过授权码获取用户信息
 * - 刷新访问令牌
 * </p>
 *
 * @author Fan
 */
public interface OAuthProvider {

    /**
     * 获取平台标识
     * <p>
     * 返回当前Provider所属的平台类型，如WECHAT、WEWORK、FEISHU、DINGTALK等
     * </p>
     *
     * @return 返回平台标识枚举
     */
    Platform getPlatform();

    /**
     * 获取能力类型
     * <p>
     * 返回当前Provider提供的具体能力类型，用于区分同一平台下的不同OAuth场景。
     * 例如：
     * - "mp"：小程序授权
     * - "oauth"：网页授权
     * - "qrcode"：扫码登录
     * </p>
     *
     * @return 能力类型标识
     */
    String getCapability();

    /**
     * 生成授权URL
     * <p>
     * 根据请求参数生成第三方平台的授权URL，用户访问该URL后进行授权操作
     * </p>
     *
     * @param request OAuth请求对象，包含appId、redirectUri、scope等参数
     * @return 授权URL字符串
     */
    String getAuthorizationUrl(OAuthRequest request);

    /**
     * 通过授权码获取用户信息
     * <p>
     * 用户授权后，使用返回的授权码（code）换取用户的访问令牌和基本信息
     * </p>
     *
     * @param request OAuth请求对象，必须包含code参数
     * @return OAuth响应对象，包含用户信息和访问令牌
     */
    OAuthResponse getUserInfo(OAuthRequest request);

}
