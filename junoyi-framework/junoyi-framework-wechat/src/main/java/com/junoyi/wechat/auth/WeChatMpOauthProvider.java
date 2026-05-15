package com.junoyi.wechat.auth;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.junoyi.platform.auth.OAuthProvider;
import com.junoyi.platform.domain.OAuthRequest;
import com.junoyi.platform.domain.OAuthResponse;
import com.junoyi.platform.enums.Platform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 微信小程序 OAuth 提供者
 * <p>
 * 实现微信小程序的授权登录功能，通过 code 换取 session_key 和 openid
 * </p>
 *
 * @author Fan
 */
@Slf4j
@RequiredArgsConstructor
public class WeChatMpOauthProvider implements OAuthProvider {

    private final WxMaService wxMaService;

    /**
     * 获取平台类型
     *
     * @return 微信平台
     */
    @Override
    public Platform getPlatform() {
        return Platform.WECHAT;
    }

    /**
     * 获取能力类型
     *
     * @return mp - 小程序
     */
    @Override
    public String getCapability() {
        return "mp";
    }

    /**
     * 生成授权URL
     * <p>
     * 微信小程序不需要生成授权URL，直接在小程序端调用 wx.login() 获取 code
     * </p>
     *
     * @param request OAuth请求对象
     * @return 空字符串
     */
    @Override
    public String getAuthorizationUrl(OAuthRequest request) {
        // 微信小程序不需要生成授权URL
        return "";
    }

    /**
     * 通过授权码获取用户信息
     * <p>
     * 使用小程序端获取的 code 换取 session_key 和 openid
     * </p>
     *
     * @param request OAuth请求对象，必须包含 code
     * @return OAuth响应对象，包含 openid 和 session_key
     */
    @Override
    public OAuthResponse getUserInfo(OAuthRequest request) {
        try {
            // 调用微信接口，通过 code 换取 session_key 和 openid
            WxMaJscode2SessionResult session = wxMaService.getUserService()
                    .getSessionInfo(request.getCode());

            log.info("微信小程序登录成功，openid: {}", session.getOpenid());

            // 构建响应对象
            return OAuthResponse.builder()
                    .code("SUCCESS")
                    .message("授权成功")
                    .openId(session.getOpenid())
                    .unionId(session.getUnionid())
                    .accessToken(session.getSessionKey()) // 将 session_key 作为 accessToken 返回
                    .build();

        } catch (WxErrorException e) {
            log.error("微信小程序登录失败: {}", e.getMessage(), e);
            return OAuthResponse.fail("微信授权失败: " + e.getError().getErrorMsg());
        } catch (Exception e) {
            log.error("微信小程序登录异常: {}", e.getMessage(), e);
            return OAuthResponse.fail("系统异常，请稍后重试");
        }
    }

}