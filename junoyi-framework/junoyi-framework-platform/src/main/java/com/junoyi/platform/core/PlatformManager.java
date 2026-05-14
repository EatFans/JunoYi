package com.junoyi.platform.core;

import com.junoyi.platform.auth.OAuthProvider;
import com.junoyi.platform.enums.Platform;
import com.junoyi.platform.pay.PayProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 平台管理器
 * <p>
 * 统一管理所有第三方平台的 Provider，包括 OAuth 认证和支付功能。
 * 采用 SPI 机制，自动注册所有实现了相应接口的 Provider。
 * </p>
 *
 * @author Fan
 */
@Component
public class PlatformManager {

    /**
     * OAuth Provider 映射表
     * Key: platform_capability (如: wechat_mp, wework_oauth)
     */
    private final Map<String, OAuthProvider> oauthProviderMap = new ConcurrentHashMap<>();

    /**
     * Pay Provider 映射表
     * Key: platform (如: wechat, alipay)
     */
    private final Map<Platform, PayProvider> payProviderMap = new ConcurrentHashMap<>();

    /**
     * 构造函数 - 自动注册所有 OAuth Provider 和 Pay Provider
     * <p>
     * 使用 @Autowired(required = false) 允许在没有任何 Provider 的情况下也能正常启动
     * </p>
     *
     * @param oauthProviders OAuth Provider 列表，由 Spring 自动注入
     * @param payProviders   Pay Provider 列表，由 Spring 自动注入
     */
    public PlatformManager(
            @Autowired(required = false) List<OAuthProvider> oauthProviders,
            @Autowired(required = false) List<PayProvider> payProviders) {

        // 注册 OAuth Provider
        if (oauthProviders != null && !oauthProviders.isEmpty()) {
            for (OAuthProvider provider : oauthProviders) {
                String key = buildOAuthKey(provider.getPlatform(), provider.getCapability());
                oauthProviderMap.put(key, provider);
            }
        }

        // 注册 Pay Provider
        if (payProviders != null && !payProviders.isEmpty()) {
            for (PayProvider provider : payProviders) {
                payProviderMap.put(provider.getPlatform(), provider);
            }
        }
    }

    /**
     * 获取 OAuth Provider
     *
     * @param platform   平台类型
     * @param capability 能力类型（如：mp-小程序, oauth-授权登录）
     * @return OAuth Provider，如果不存在则返回 null
     */
    public OAuthProvider getOAuthProvider(Platform platform, String capability) {
        String key = buildOAuthKey(platform, capability);
        return oauthProviderMap.get(key);
    }

    /**
     * 获取 OAuth Provider（兼容旧版本）
     *
     * @param platformCode 平台编码（如：wechat, wework）
     * @return OAuth Provider，如果不存在则返回 null
     * @deprecated 建议使用 {@link #getOAuthProvider(Platform, String)}
     */
    @Deprecated
    public OAuthProvider getOAuthProvider(String platformCode) {
        // 遍历查找匹配的 provider
        for (OAuthProvider provider : oauthProviderMap.values()) {
            if (provider.getPlatform().getCode().equals(platformCode)) {
                return provider;
            }
        }
        return null;
    }

    /**
     * 获取 Pay Provider
     *
     * @param platform 平台类型
     * @return Pay Provider，如果不存在则返回 null
     */
    public PayProvider getPayProvider(Platform platform) {
        return payProviderMap.get(platform);
    }

    /**
     * 注册 OAuth Provider
     *
     * @param provider OAuth Provider
     */
    public void registerOAuthProvider(OAuthProvider provider) {
        String key = buildOAuthKey(provider.getPlatform(), provider.getCapability());
        oauthProviderMap.put(key, provider);
    }

    /**
     * 注册 Pay Provider
     *
     * @param provider Pay Provider
     */
    public void registerPayProvider(PayProvider provider) {
        payProviderMap.put(provider.getPlatform(), provider);
    }

    /**
     * 获取所有已注册的 OAuth Provider
     *
     * @return OAuth Provider 映射表
     */
    public Map<String, OAuthProvider> getAllOAuthProviders() {
        return new HashMap<>(oauthProviderMap);
    }

    /**
     * 获取所有已注册的 Pay Provider
     *
     * @return Pay Provider 映射表
     */
    public Map<Platform, PayProvider> getAllPayProviders() {
        return new HashMap<>(payProviderMap);
    }

    /**
     * 构建 OAuth Provider 的唯一键
     *
     * @param platform   平台类型
     * @param capability 能力类型
     * @return 唯一键，格式：platform_capability
     */
    private String buildOAuthKey(Platform platform, String capability) {
        return platform.getCode() + "_" + capability;
    }
}