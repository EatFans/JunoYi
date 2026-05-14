package com.junoyi.oauth.wechat.controller;

import com.junoyi.framework.core.domain.module.R;
import com.junoyi.framework.security.annotation.PlatformScope;
import com.junoyi.framework.security.enums.PlatformType;
import com.junoyi.framework.web.domain.BaseController;
import com.junoyi.oauth.wechat.domain.dto.WechatMpLoginDTO;
import com.junoyi.oauth.wechat.service.IWeChatMpAuthService;
import com.junoyi.system.domain.vo.AuthVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @PostMapping("/login")
    public R<AuthVO> wechatMpLogin(@RequestBody WechatMpLoginDTO loginDTO){
        weChatMpAuthService.login(loginDTO.getCode());
        return R.ok();
    }

    /**
     * 微信小程序刷新AccessToken接口
     */
    @PostMapping("/refresh")
    @PlatformScope(PlatformType.MINI_PROGRAM)
    public R<?> wechatMpFreshToken(){
        return R.ok();
    }

    /**
     * 微信小程序退出登录
     */
    @PostMapping("/logout")
    @PlatformScope(PlatformType.MINI_PROGRAM)
    public R<Void> wechatMpLogout(){
        return R.ok();
    }
}