package com.junoyi.oauth.wechat.controller;

import com.junoyi.framework.core.domain.module.R;
import com.junoyi.framework.security.annotation.PlatformScope;
import com.junoyi.framework.security.enums.PlatformType;
import com.junoyi.framework.web.domain.BaseController;
import com.junoyi.oauth.wechat.service.IWeChatMpAuthService;
import com.junoyi.system.domain.vo.AuthVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/login")
    @PlatformScope(PlatformType.MINI_PROGRAM)
    public R<AuthVO> wechatMpLogin(){

        return R.ok();
    }

    /**
     * 微信小程序刷新AccessToken接口
     */
    public R<?> freshToken(){
        return R.ok();
    }

    /**
     * 微信小程序退出登录
     * @return
     */
    public R<Void> wechatLogout(){
        return R.ok();
    }
}