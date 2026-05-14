package com.junoyi.wework.auth;

import com.junoyi.platform.auth.OAuthProvider;
import com.junoyi.platform.enums.Platform;

/**
 * 微信小程序Oauth提供者
 *
 * @author Fan
 */
public class WeChatMpOauthProvider implements OAuthProvider {

    /**
     * 获取平台
     */
    @Override
    public Platform getPlatform() {
        return Platform.WECHAT;
    }

    /**
     * 获取能力
     */
    @Override
    public String getCapability() {
        return "mp";
    }
}