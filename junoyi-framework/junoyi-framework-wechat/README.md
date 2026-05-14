# JunoYi Framework WeChat

## 模块简介

本模块是 **微信能力封装层**，提供微信小程序和微信支付的集成能力，基于 `weixin-java-tools` SDK 实现。

## 核心功能

### 1. 微信小程序

- ✅ 小程序登录（code 换取 session_key 和 openid）
- ✅ 获取用户信息
- 🚧 小程序消息推送（规划中）
- 🚧 小程序二维码生成（规划中）

### 2. 微信支付

- ✅ 创建支付订单（支持 JSAPI、APP、H5、NATIVE）
- 🚧 查询订单状态（规划中）
- 🚧 关闭订单（规划中）
- 🚧 申请退款（规划中）
- 🚧 查询退款（规划中）

## 配置说明

### 微信小程序配置

在 `application.yml` 中添加以下配置：

```yaml
junoyi:
  wechat:
    mp:
      enabled: true                    # 是否启用微信小程序功能
      app-id: your-mini-app-id         # 小程序 AppID
      secret: your-mini-app-secret     # 小程序 AppSecret
```

### 微信支付配置

```yaml
junoyi:
  wechat:
    pay:
      enabled: true                              # 是否启用微信支付功能
      app-id: your-app-id                        # 微信支付分配的公众账号ID
      merchant-id: your-merchant-id              # 微信支付分配的商户号
      api-v3-key: your-api-v3-key                # APIv3密钥
      private-key-path: /path/to/private-key.pem # 商户私钥文件路径
      private-cert-path: /path/to/cert.pem       # 商户证书文件路径
      cert-serial-no: your-cert-serial-no        # 商户证书序列号
      pay-notify-url: https://your-domain.com/pay/notify    # 支付回调地址
      refund-notify-url: https://your-domain.com/refund/notify # 退款回调地址
```

## 使用示例

### 微信小程序登录

```java
@Service
@RequiredArgsConstructor
public class WeChatLoginService {
    
    private final PlatformManager platformManager;
    
    public OAuthResponse miniProgramLogin(String code) {
        // 获取微信小程序 OAuth Provider
        OAuthProvider provider = platformManager.getOAuthProvider(Platform.WECHAT, "mp");
        
        // 构建请求
        OAuthRequest request = OAuthRequest.builder()
                .code(code)
                .build();
        
        // 获取用户信息
        return provider.getUserInfo(request);
    }
}
```

### 微信支付下单

```java
@Service
@RequiredArgsConstructor
public class WeChatPayService {
    
    private final PlatformManager platformManager;
    
    public PayResponse createOrder(String outTradeNo, BigDecimal amount, String openId) {
        // 获取微信支付 Provider
        PayProvider provider = platformManager.getPayProvider(Platform.WECHAT);
        
        // 构建支付请求
        PayRequest request = PayRequest.builder()
                .outTradeNo(outTradeNo)
                .description("商品描述")
                .amount(amount)
                .currency("CNY")
                .userId(openId)
                .tradeType("JSAPI")
                .notifyUrl("https://your-domain.com/pay/notify")
                .build();
        
        // 创建支付订单
        return provider.createOrder(request);
    }
}
```

## 依赖说明

本模块依赖以下第三方库：

- `weixin-java-miniapp`：微信小程序 SDK
- `weixin-java-pay`：微信支付 SDK
- `junoyi-framework-platform`：平台能力标准层

## 注意事项

1. **证书配置**：微信支付需要配置商户证书，请确保证书文件路径正确
2. **回调地址**：支付回调地址必须是外网可访问的 HTTPS 地址
3. **安全性**：请妥善保管 AppSecret、API密钥等敏感信息，不要提交到代码仓库
4. **测试环境**：建议在测试环境使用微信支付的沙箱环境进行测试

## 扩展开发

如需添加更多微信相关功能，可以：

1. 实现新的 `OAuthProvider`（如公众号网页授权）
2. 扩展 `WeChatPayProvider`（如添加退款功能）
3. 添加新的配置类和 Properties

## 相关文档

- [微信小程序开发文档](https://developers.weixin.qq.com/miniprogram/dev/framework/)
- [微信支付开发文档](https://pay.weixin.qq.com/wiki/doc/apiv3/index.shtml)
- [weixin-java-tools 文档](https://github.com/Wechat-Group/WxJava)

