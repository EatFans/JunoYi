package com.junoyi.oauth.wechat.service;

import com.junoyi.oauth.wechat.domain.vo.OauthUserInfoVO;
import com.junoyi.system.domain.vo.AuthVO;

/**
 * 微信小程序认证登录业务接口
 *
 * @author Fan
 */
public interface IWeChatMpAuthService {

    /**
     * 微信小程序登录
     * @param code 微信小程序用户获取的的code
     * @return 返回 Token 对
     */
    AuthVO login(String code);

    /**
     * 获取用户信息
     * @param userId 用户Id
     * @return 用户信息
     */
    OauthUserInfoVO getUserInfo(Long userId);

    /**
     * 刷新访问令牌
     * @param refreshToken 刷新令牌
     * @return 令牌对
     */
    AuthVO refreshToken(String refreshToken);

    /**
     * 退出当前会话
     * @return 如果成功就返回true，否则返回false
     */
    boolean logout();
}