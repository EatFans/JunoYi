package com.junoyi.oauth.wechat.domain.vo;

import lombok.Data;

/**
 * 登录用户信息
 *
 * @author Fan
 */
@Data
public class OauthUserInfoVO {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名字
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户手机号
     */
    private String phoneNumber;

    /**
     * 用户邮箱
     */
    private String email;
}