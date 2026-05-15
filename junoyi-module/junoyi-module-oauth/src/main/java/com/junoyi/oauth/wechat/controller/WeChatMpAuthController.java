package com.junoyi.oauth.wechat.controller;

import com.junoyi.framework.core.domain.module.R;
import com.junoyi.framework.security.annotation.PlatformScope;
import com.junoyi.framework.security.enums.PlatformType;
import com.junoyi.framework.security.utils.SecurityUtils;
import com.junoyi.framework.web.domain.BaseController;
import com.junoyi.oauth.wechat.domain.dto.WechatMpLoginDTO;
import com.junoyi.oauth.wechat.domain.vo.OauthUserInfoVO;
import com.junoyi.oauth.wechat.service.IWeChatMpAuthService;
import com.junoyi.system.domain.vo.AuthVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 微信小程序认证登录控制器
 *
 * @author Fan
 */
@RestController
@RequestMapping("/auth/wechat/mp")
@RequiredArgsConstructor
public class WeChatMpAuthController extends BaseController {

    private final IWeChatMpAuthService weChatMpAuthService;

    /**
     * 微信小程序登录接口
     */
    @PostMapping("/login")
    public R<AuthVO> wechatMpLogin(@RequestBody WechatMpLoginDTO loginDTO){
        return R.ok(weChatMpAuthService.login(loginDTO.getCode()));
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/info")
    @PlatformScope(PlatformType.MINI_PROGRAM)
    public R<OauthUserInfoVO> getUserInfo(){
        Long userId = SecurityUtils.getUserId();
        if (userId == null || userId == 0L)
            return R.fail("获取信息失败");
        return R.ok(weChatMpAuthService.getUserInfo(userId));
    }

    /**
     * 微信小程序刷新AccessToken接口
     */
    @PostMapping("/refresh")
    @PlatformScope(PlatformType.MINI_PROGRAM)
    public R<AuthVO> wechatMpFreshToken(@RequestParam("refreshToken") String refreshToken){
        return R.ok(weChatMpAuthService.refreshToken(refreshToken));
    }

    /**
     * 微信小程序退出登录
     */
    @PostMapping("/logout")
    @PlatformScope(PlatformType.MINI_PROGRAM)
    public R<Void> wechatMpLogout(){
        return weChatMpAuthService.logout() ? R.ok() : R.fail("退出登录失败");
    }
}