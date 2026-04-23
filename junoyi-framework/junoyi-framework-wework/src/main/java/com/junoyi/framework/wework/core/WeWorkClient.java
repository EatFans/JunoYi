package com.junoyi.framework.wework.core;

import com.junoyi.framework.wework.properties.WeWorkProperties;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpOauth2UserInfo;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 企业微信统一客户端
 *
 * @author Fan
 */
public class WeWorkClient {

    private final WxCpService wxCpService;
    private final WeWorkProperties properties;

    public WeWorkClient(WxCpService wxCpService, WeWorkProperties properties) {
        this.wxCpService = wxCpService;
        this.properties = properties;
    }

    /**
     * 获取配置属性
     */
    public WeWorkProperties getProperties() {
        return properties;
    }

    /**
     * 构建扫码登录地址
     */
    public String buildQrConnectUrl(String state) {
        properties.validateRedirectUri();
        return String.format(
                "https://open.work.weixin.qq.com/wwopen/sso/qrConnect?appid=%s&agentid=%s&redirect_uri=%s&state=%s",
                properties.getCorpId(),
                properties.getAgentId(),
                urlEncode(properties.getRedirectUri()),
                state
        );
    }

    /**
     * 通过 code 获取 OAuth 用户信息
     */
    public WxCpOauth2UserInfo getOauth2UserInfo(String code) throws WxErrorException {
        return wxCpService.getOauth2Service().getUserInfo(code);
    }

    /**
     * 通过 code 获取企业微信用户ID
     */
    public String getUserIdByCode(String code) throws WxErrorException {
        WxCpOauth2UserInfo userInfo = getOauth2UserInfo(code);
        return userInfo == null ? null : userInfo.getUserId();
    }

    /**
     * 获取底层 SDK 服务
     */
    public WxCpService getWxCpService() {
        return wxCpService;
    }

    private String urlEncode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}

