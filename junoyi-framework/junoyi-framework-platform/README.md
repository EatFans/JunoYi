# JunoYi Framework Platform

## 模块简介

本模块是 **平台能力标准层**，定义了第三方平台集成的统一接口规范（SPI），为微信、企业微信、钉钉、飞书等第三方平台提供统一的能力抽象。

## 核心功能

### 1. OAuth 认证能力

提供统一的 OAuth 认证接口，支持：
- 生成授权 URL
- 通过授权码获取用户信息
- 刷新访问令牌

### 2. 支付能力

提供统一的支付接口，支持：
- 创建支付订单
- 查询订单状态
- 退款处理

### 3. 平台管理

提供统一的平台管理器，自动注册和管理所有平台的 Provider。

## 模块结构

```
junoyi-framework-platform
├── auth                    # OAuth 认证相关
│   └── OAuthProvider      # OAuth 提供者接口
├── pay                     # 支付相关
│   └── PayProvider        # 支付提供者接口
├── core                    # 核心管理
│   └── PlatformManager    # 平台管理器
├── domain                  # 领域对象
│   ├── OAuthRequest       # OAuth 请求对象
│   ├── OAuthResponse      # OAuth 响应对象
│   ├── PayRequest         # 支付请求对象
│   └── PayResponse        # 支付响应对象
└── enums                   # 枚举定义
    ├── Platform           # 平台枚举
    ├── TradeType          # 交易类型枚举
    └── PayStatus          # 支付状态枚举
```

## 使用示例

### 实现 OAuth Provider

```java
@Component
public class WeChatMpOauthProvider implements OAuthProvider {

    @Override
    public Platform getPlatform() {
        return Platform.WECHAT;
    }

    @Override
    public String getCapability() {
        return "mp"; // 小程序
    }

    @Override
    public String getAuthorizationUrl(OAuthRequest request) {
        // 实现生成授权 URL 的逻辑
        return "https://open.weixin.qq.com/...";
    }

    @Override
    public OAuthResponse getUserInfo(OAuthRequest request) {
        // 实现获取用户信息的逻辑
        return OAuthResponse.success(openId, accessToken);
    }

    @Override
    public OAuthResponse refreshToken(String refreshToken) {
        // 实现刷新令牌的逻辑
        return OAuthResponse.success(openId, newAccessToken);
    }
}
```

### 实现 Pay Provider

```java
@Component
public class WeChatPayProvider implements PayProvider {

    @Override
    public Platform getPlatform() {
        return Platform.WECHAT;
    }

    @Override
    public PayResponse createOrder(PayRequest request) {
        // 实现创建支付订单的逻辑
        return PayResponse.success(outTradeNo, transactionId, payInfo);
    }
}
```

### 使用 Platform Manager

```java
@Service
public class AuthService {

    @Autowired
    private PlatformManager platformManager;

    public OAuthResponse login(String code) {
        // 获取微信小程序的 OAuth Provider
        OAuthProvider provider = platformManager.getOAuthProvider(Platform.WECHAT, "mp");

        // 构建请求
        OAuthRequest request = OAuthRequest.builder()
                .code(code)
                .appId("your-app-id")
                .appSecret("your-app-secret")
                .build();

        // 获取用户信息
        return provider.getUserInfo(request);
    }
}
```

## 扩展支持

要添加新的第三方平台支持，只需：

1. 实现 `OAuthProvider` 接口（如需 OAuth 认证）
2. 实现 `PayProvider` 接口（如需支付功能）
3. 添加 `@Component` 注解，Spring 会自动注册到 `PlatformManager`

## 支持的平台

- ✅ 微信（WECHAT）
- ✅ 企业微信（WEWORK）
- 🚧 飞书（FEISHU）- 规划中
- 🚧 钉钉（DINGTALK）- 规划中
- 🚧 支付宝（ALIPAY）- 规划中