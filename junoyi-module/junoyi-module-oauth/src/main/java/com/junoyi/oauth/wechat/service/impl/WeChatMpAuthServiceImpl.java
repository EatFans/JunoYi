package com.junoyi.oauth.wechat.service.impl;

import com.junoyi.framework.security.helper.AuthHelper;
import com.junoyi.oauth.wechat.service.IWeChatMpAuthService;
import com.junoyi.system.domain.vo.AuthVO;
import com.junoyi.system.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 微信小程序认证登录业务接口实现类
 *
 * @author Fan
 */
@Service
@RequiredArgsConstructor
public class WeChatMpAuthServiceImpl implements IWeChatMpAuthService {

    private final SysUserMapper sysUserMapper;
    private final SysUserThirdAuthMapper sysUserThirdAuthMapper;
    private final AuthHelper authHelper;

    /**
     * 微信小程序登录
     * @param code 微信小程序用户获取的的code
     * @return 返回 Token 对
     */
    @Override
    public AuthVO login(String code) {
        return null;
    }
}