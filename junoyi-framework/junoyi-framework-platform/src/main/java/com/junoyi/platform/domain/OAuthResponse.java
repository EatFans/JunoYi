package com.junoyi.platform.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * OAuth 授权响应对象
 * <p>
 * 封装了 OAuth 授权后返回的用户信息和令牌信息
 * </p>
 *
 * @author Fan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuthResponse {

    /**
     * 响应状态码
     * <p>
     * SUCCESS：成功
     * FAIL：失败
     * </p>
     */
    private String code;

    /**
     * 响应消息
     * <p>
     * 返回信息，如非空，为错误原因
     * </p>
     */
    private String message;

    /**
     * 用户唯一标识
     * <p>
     * 用户在第三方平台的唯一标识（如微信的openid）
     * </p>
     */
    private String openId;

    /**
     * 用户统一标识
     * <p>
     * 用户在开放平台的唯一标识（如微信的unionid）
     * </p>
     */
    private String unionId;

    /**
     * 访问令牌
     * <p>
     * 接口调用凭证
     * </p>
     */
    private String accessToken;

    /**
     * 刷新令牌
     * <p>
     * 用于刷新access_token
     * </p>
     */
    private String refreshToken;

    /**
     * 令牌过期时间
     * <p>
     * access_token的有效期，单位：秒
     * </p>
     */
    private Long expiresIn;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户性别
     * <p>
     * 0-未知，1-男，2-女
     * </p>
     */
    private Integer gender;

    /**
     * 用户手机号
     */
    private String mobile;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 扩展信息
     * <p>
     * 用于存放各平台特有的用户信息
     * </p>
     */
    private Map<String, Object> extraInfo;

    /**
     * 创建成功的响应
     *
     * @param openId      用户唯一标识
     * @param accessToken 访问令牌
     * @return OAuthResponse
     */
    public static OAuthResponse success(String openId, String accessToken) {
        return OAuthResponse.builder()
                .code("SUCCESS")
                .message("授权成功")
                .openId(openId)
                .accessToken(accessToken)
                .build();
    }

    /**
     * 创建失败的响应
     *
     * @param message 失败原因
     * @return OAuthResponse
     */
    public static OAuthResponse fail(String message) {
        return OAuthResponse.builder()
                .code("FAIL")
                .message(message)
                .build();
    }
}

