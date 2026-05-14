package com.junoyi.platform.core;

import com.junoyi.platform.auth.OAuthProvider;

import java.util.List;
import java.util.Map;

/**
 * 平台管理器
 *
 * 统一管理所有平台 Provider
 *
 * @author Fan
 */
public class PlatformManager {

    private final Map<String, OAuthProvider> oauthProviderMap;

    /**
     * 自动注册 Provider
     */
    public PlatformManager(List<OAuthProvider> providers) {
        for (OAuthProvider provider : providers) {
            oauthProviderMap.put(
                    provider.getPlatform(),
                    provider
            );
        }
    }

    /**
     * 获取 OAuth Provider
     */
    public OAuthProvider getOAuthProvider(String platform) {
        return oauthProviderMap.get(platform);
    }
}