# 微信小程序 OAuth 使用指南

## 概述

`WeChatMpOauthProvider` 是微信小程序的 OAuth 认证提供者，实现了通过小程序 code 换取用户 openid 和 session_key 的功能。

## 前置条件

### 1. 配置微信小程序参数

在 `application.yml` 中配置：

```yaml
junoyi:
  wechat:
    mp:
      enabled: true
      app-id: wx1234567890abcdef  # 你的小程序 AppID
      secret: your-mini-app-secret  # 你的小程序 AppSecret
```

### 2. 确保模块已引入

检查 `junoyi-framework-boot-starter` 的 `pom.xml` 是否引入了 `junoyi-framework-wechat` 模块：

```xml
<dependency>
    <groupId>com.junoyi</groupId>
    <artifactId>junoyi-framework-wechat</artifactId>
</dependency>
```

## 使用方式

### 方式一：通过 PlatformManager（推荐）

这是最标准的使用方式，通过平台管理器获取 Provider。

#### 1. 在 Service 中注入 PlatformManager

```java
@Service
@RequiredArgsConstructor
public class WeChatMpAuthServiceImpl implements IWeChatMpAuthService {

    private final PlatformManager platformManager;
    private final SysUserThirdAuthMapper sysUserThirdAuthMapper;
    private final AuthHelper authHelper;

    @Override
    public AuthVO login(String code) {
        // 1. 获取微信小程序的 OAuth Provider
        OAuthProvider provider = platformManager.getOAuthProvider(Platform.WECHAT, "mp");
        
        // 2. 构建请求对象
        OAuthRequest request = OAuthRequest.builder()
                .code(code)
                .build();
        
        // 3. 调用微信接口获取用户信息
        OAuthResponse response = provider.getUserInfo(request);
        
        // 4. 检查响应
        if (!"SUCCESS".equals(response.getCode())) {
            throw new RuntimeException("微信授权失败: " + response.getMessage());
        }
        
        // 5. 根据 openid 查询或创建用户
        String authType = "wechat_mp";
        String authKey = response.getOpenId();
        
        SysThirdAuthUserDTO authUser = sysUserThirdAuthMapper
                .selectUserSnapshotByAuth(authType, authKey);
        
        if (authUser == null) {
            // 新用户，需要注册
            return handleNewUser(response);
        } else {
            // 老用户，直接登录
            return handleExistingUser(authUser);
        }
    }
    
    private AuthVO handleNewUser(OAuthResponse response) {
        // TODO: 实现新用户注册逻辑
        // 1. 创建系统用户
        // 2. 创建第三方绑定关系
        // 3. 生成 Token
        return null;
    }
    
    private AuthVO handleExistingUser(SysThirdAuthUserDTO authUser) {
        // 检查用户状态
        if (authUser.getDelFlag()) {
            throw new RuntimeException("用户已被删除");
        }
        if (authUser.getStatus() != 0) {
            throw new RuntimeException("用户已被禁用");
        }
        
        // 构建登录用户信息
        LoginUser loginUser = LoginUser.builder()
                .userId(authUser.getUserId())
                .userName(authUser.getUserName())
                .nickName(authUser.getNickName())
                .build();
        
        // 获取请求信息
        String loginIp = ServletUtils.getClientIp();
        String userAgent = ServletUtils.getUserAgent();
        
        // 调用登录
        TokenPair tokenPair = authHelper.login(
                loginUser, 
                PlatformType.MINI_PROGRAM, 
                loginIp, 
                userAgent
        );
        
        // 构建返回对象
        return AuthVO.builder()
                .accessToken(tokenPair.getAccessToken())
                .refreshToken(tokenPair.getRefreshToken())
                .expiresIn(tokenPair.getExpiresIn())
                .build();
    }
}
```

#### 2. 在 Controller 中调用

```java
@RestController
@RequestMapping("/auth/wechat/mp")
@RequiredArgsConstructor
public class WeChatMpAuthController extends BaseController {

    private final IWeChatMpAuthService weChatMpAuthService;

    /**
     * 微信小程序登录接口
     */
    @PostMapping("/login")
    @PlatformScope(PlatformType.MINI_PROGRAM)
    public R<AuthVO> wechatMpLogin(@RequestBody WechatMpLoginDTO loginDTO) {
        AuthVO authVO = weChatMpAuthService.login(loginDTO.getCode());
        return R.ok(authVO);
    }
}
```

#### 3. 前端调用示例（小程序端）

```javascript
// 1. 调用微信登录获取 code
wx.login({
  success: (res) => {
    if (res.code) {
      // 2. 将 code 发送到后端
      wx.request({
        url: 'https://your-api.com/auth/wechat/mp/login',
        method: 'POST',
        data: {
          code: res.code
        },
        success: (response) => {
          if (response.data.code === 200) {
            // 3. 保存 token
            const { accessToken, refreshToken } = response.data.data;
            wx.setStorageSync('accessToken', accessToken);
            wx.setStorageSync('refreshToken', refreshToken);
            
            // 4. 跳转到首页
            wx.switchTab({
              url: '/pages/index/index'
            });
          } else {
            wx.showToast({
              title: response.data.message,
              icon: 'none'
            });
          }
        }
      });
    }
  }
});
```

### 方式二：直接注入 Provider

如果你只使用微信小程序，可以直接注入 Provider。

```java
@Service
@RequiredArgsConstructor
public class WeChatMpAuthServiceImpl implements IWeChatMpAuthService {

    private final WeChatMpOauthProvider weChatMpOauthProvider;
    
    @Override
    public AuthVO login(String code) {
        // 构建请求对象
        OAuthRequest request = OAuthRequest.builder()
                .code(code)
                .build();
        
        // 直接调用 Provider
        OAuthResponse response = weChatMpOauthProvider.getUserInfo(request);
        
        // ... 后续处理逻辑同方式一
    }
}
```

## 完整的登录流程

### 1. 数据库表结构

确保有以下两张表：

```sql
-- 系统用户表
CREATE TABLE sys_user (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(50) NOT NULL,
    nick_name VARCHAR(50),
    avatar VARCHAR(255),
    status INT DEFAULT 0,
    del_flag BOOLEAN DEFAULT FALSE,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 第三方绑定表
CREATE TABLE sys_user_third_auth (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    auth_type VARCHAR(50) NOT NULL COMMENT '第三方类型：wechat_mp, wechat_oauth, wework_oauth',
    auth_key VARCHAR(255) NOT NULL COMMENT '第三方唯一标识：openid 或 unionid',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_auth (auth_type, auth_key)
);
```

### 2. 完整的 Service 实现

```java
@Service
@RequiredArgsConstructor
@Slf4j
public class WeChatMpAuthServiceImpl implements IWeChatMpAuthService {

    private final PlatformManager platformManager;
    private final SysUserMapper sysUserMapper;
    private final SysUserThirdAuthMapper sysUserThirdAuthMapper;
    private final AuthHelper authHelper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AuthVO login(String code) {
        // 1. 获取微信小程序的 OAuth Provider
        OAuthProvider provider = platformManager.getOAuthProvider(Platform.WECHAT, "mp");
        
        // 2. 构建请求对象
        OAuthRequest request = OAuthRequest.builder()
                .code(code)
                .build();
        
        // 3. 调用微信接口获取用户信息
        OAuthResponse response = provider.getUserInfo(request);
        
        // 4. 检查响应
        if (!"SUCCESS".equals(response.getCode())) {
            throw new RuntimeException("微信授权失败: " + response.getMessage());
        }
        
        log.info("微信小程序登录成功，openid: {}", response.getOpenId());
        
        // 5. 根据 openid 查询绑定关系
        String authType = "wechat_mp";
        String authKey = response.getOpenId();
        
        SysThirdAuthUserDTO authUser = sysUserThirdAuthMapper
                .selectUserSnapshotByAuth(authType, authKey);
        
        if (authUser == null) {
            // 新用户，需要注册
            return registerAndLogin(response, authType, authKey);
        } else {
            // 老用户，直接登录
            return loginExistingUser(authUser);
        }
    }
    
    /**
     * 注册新用户并登录
     */
    private AuthVO registerAndLogin(OAuthResponse response, String authType, String authKey) {
        // 1. 创建系统用户
        SysUser newUser = new SysUser();
        newUser.setUserName("wx_" + System.currentTimeMillis()); // 生成唯一用户名
        newUser.setNickName("微信用户");
        newUser.setStatus(0);
        newUser.setDelFlag(false);
        sysUserMapper.insert(newUser);
        
        // 2. 创建第三方绑定关系
        SysUserThirdAuth thirdAuth = new SysUserThirdAuth();
        thirdAuth.setUserId(newUser.getUserId());
        thirdAuth.setAuthType(authType);
        thirdAuth.setAuthKey(authKey);
        sysUserThirdAuthMapper.insert(thirdAuth);
        
        // 3. 生成 Token
        return generateToken(newUser);
    }
    
    /**
     * 已有用户登录
     */
    private AuthVO loginExistingUser(SysThirdAuthUserDTO authUser) {
        // 检查用户状态
        if (authUser.getDelFlag()) {
            throw new RuntimeException("用户已被删除");
        }
        if (authUser.getStatus() != 0) {
            throw new RuntimeException("用户已被禁用");
        }
        
        // 构建用户对象
        SysUser user = new SysUser();
        user.setUserId(authUser.getUserId());
        user.setUserName(authUser.getUserName());
        user.setNickName(authUser.getNickName());
        
        // 生成 Token
        return generateToken(user);
    }
    
    /**
     * 生成 Token
     */
    private AuthVO generateToken(SysUser user) {
        // 构建登录用户信息
        LoginUser loginUser = LoginUser.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .nickName(user.getNickName())
                .build();
        
        // 获取请求信息
        String loginIp = ServletUtils.getClientIp();
        String userAgent = ServletUtils.getUserAgent();
        
        // 调用登录
        TokenPair tokenPair = authHelper.login(
                loginUser, 
                PlatformType.MINI_PROGRAM, 
                loginIp, 
                userAgent
        );
        
        // 构建返回对象
        return AuthVO.builder()
                .accessToken(tokenPair.getAccessToken())
                .refreshToken(tokenPair.getRefreshToken())
                .expiresIn(tokenPair.getExpiresIn())
                .build();
    }
}
```

## 响应数据结构

### OAuthResponse 对象

```java
{
    "code": "SUCCESS",           // 状态码
    "message": "授权成功",        // 消息
    "openId": "oXXXXXXXXXXXXX",  // 微信 openid
    "unionId": "uXXXXXXXXXXXX",  // 微信 unionid（可能为空）
    "accessToken": "session_key" // session_key
}
```

### AuthVO 对象

```java
{
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "expiresIn": 7200  // 过期时间（秒）
}
```

## 常见问题

### 1. Provider 未注册

**错误信息**：`No OAuth provider found for platform: WECHAT, capability: mp`

**解决方案**：
- 检查 `application.yml` 中 `junoyi.wechat.mp.enabled` 是否为 `true`
- 检查 `AutoConfiguration.imports` 文件是否存在
- 检查 Maven 依赖是否正确引入

### 2. code 已被使用

**错误信息**：`code been used`

**原因**：微信的 code 只能使用一次，5分钟内有效

**解决方案**：
- 前端每次登录都要重新调用 `wx.login()` 获取新的 code
- 不要重复使用同一个 code

### 3. appid 不匹配

**错误信息**：`invalid appid`

**解决方案**：
- 检查配置文件中的 `app-id` 是否正确
- 确保前端小程序的 appid 与后端配置的一致

## 最佳实践

1. **使用 PlatformManager**：推荐使用 PlatformManager 获取 Provider，便于后续扩展其他平台
2. **异常处理**：对微信接口调用进行完善的异常处理
3. **日志记录**：记录关键操作日志，便于问题排查
4. **用户状态检查**：登录时检查用户的删除标记和状态
5. **事务管理**：新用户注册时使用事务，确保数据一致性

## 扩展功能

### 1. 获取用户手机号

如果需要获取用户手机号，可以扩展 Provider：

```java
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnBean(WxMaService.class)
public class WeChatMpOauthProvider implements OAuthProvider {

    private final WxMaService wxMaService;

    // ... 其他方法

    /**
     * 获取用户手机号
     */
    public String getPhoneNumber(String code) {
        try {
            WxMaPhoneNumberInfo phoneInfo = wxMaService.getUserService()
                    .getPhoneNoInfo(code);
            return phoneInfo.getPhoneNumber();
        } catch (WxErrorException e) {
            log.error("获取手机号失败: {}", e.getMessage(), e);
            throw new RuntimeException("获取手机号失败");
        }
    }
}
```

### 2. 解密用户信息

```java
/**
 * 解密用户信息
 */
public WxMaUserInfo decryptUserInfo(String encryptedData, String iv, String sessionKey) {
    try {
        return wxMaService.getUserService()
                .getUserInfo(sessionKey, encryptedData, iv);
    } catch (WxErrorException e) {
        log.error("解密用户信息失败: {}", e.getMessage(), e);
        throw new RuntimeException("解密用户信息失败");
    }
}
```

## 时序图

```
小程序端                后端                    微信服务器
   |                     |                         |
   |--wx.login()-------->|                         |
   |<---返回 code---------|                         |
   |                     |                         |
   |--POST /login------->|                         |
   |   {code: "xxx"}     |                         |
   |                     |                         |
   |                     |--code2Session---------->|
   |                     |                         |
   |                     |<--openid+session_key----|
   |                     |                         |
   |                     |--查询用户绑定关系------->|
   |                     |                         |
   |                     |--生成 Token------------>|
   |                     |                         |
   |<--返回 Token---------|                         |
   |   {accessToken,     |                         |
   |    refreshToken}    |                         |
```

## 参考资料

- [微信小程序登录文档](https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/login.html)
- [WxJava 文档](https://github.com/Wechat-Group/WxJava)
- [平台管理器使用指南](../junoyi-framework/junoyi-framework-platform/README.md)

