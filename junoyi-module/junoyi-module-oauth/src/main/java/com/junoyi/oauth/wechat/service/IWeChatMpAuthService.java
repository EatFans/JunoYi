package com.junoyi.oauth.wechat.service;

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
}