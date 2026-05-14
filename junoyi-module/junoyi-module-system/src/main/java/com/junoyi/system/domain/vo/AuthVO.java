package com.junoyi.system.domain.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 登录验证返回密钥对
 */
@Data
@Builder
public class AuthVO {

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 刷新令牌
     */
    private String refreshToken;
}