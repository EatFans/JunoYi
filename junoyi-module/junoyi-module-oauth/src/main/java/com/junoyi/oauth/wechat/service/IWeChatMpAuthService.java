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
}