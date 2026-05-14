# 微信小程序 OAuth 快速开始

## 5分钟快速集成

### 1. 配置文件（application.yml）

```yaml
junoyi:
  wechat:
    mp:
      enabled: true
      app-id: wx1234567890abcdef  # 替换为你的小程序 AppID
      secret: your-mini-app-secret  # 替换为你的小程序 AppSecret
```

### 2. Service 实现

```java
@Service
@RequiredArgsConstructor
@Slf4j
public class WeChatMpAuthServiceImpl implements IWeChatMpAuthService {

    private final PlatformManager platformManager;
    private final SysUserThirdAuthMapper sysUserThirdAuthMapper;
    private final SysUserMapper sysUserMapper;
    private final AuthHelper authHelper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AuthVO login(String code) {
        // 1. 获取 Provider 并调用微信接口
        OAuthProvider provider = platformManager.getOAuthProvider(Platform.WECHAT, "mp");
        OAuthRequest request = OAuthRequest.builder().code(code).build();
        OAuthResponse response = provider.getUserInfo(request);
        
        if (!"SUCCESS".equals(response.getCode())) {
            throw new RuntimeException("微信授权失败: " + response.getMessage());
        }
        
        // 2. 查询绑定关系
        String authType = "wechat_mp";
        String authKey = response.getOpenId();
        SysThirdAuthUserDTO authUser = sysUserThirdAuthMapper
                .selectUserSnapshotByAuth(authType, authKey);
        
        // 3. 新用户注册，老用户登录
        if (authUser == null) {
            return registerAndLogin(response, authType, authKey);
        } else {
            return loginExistingUser(authUser);
        }
    }
    
    private AuthVO registerAndLogin(OAuthResponse response, String authType, String authKey) {
        // 创建用户
        SysUser newUser = new SysUser();
        newUser.setUserName("wx_" + System.currentTimeMillis());
        newUser.setNickName("微信用户");
        newUser.setStatus(0);
        newUser.setDelFlag(false);
        sysUserMapper.insert(newUser);
        
        // 创建绑定
        SysUserThirdAuth thirdAuth = new SysUserThirdAuth();
        thirdAuth.setUserId(newUser.getUserId());
        thirdAuth.setAuthType(authType);
        thirdAuth.setAuthKey(authKey);
        sysUserThirdAuthMapper.insert(thirdAuth);
        
        return generateToken(newUser);
    }
    
    private AuthVO loginExistingUser(SysThirdAuthUserDTO authUser) {
        if (authUser.getDelFlag() || authUser.getStatus() != 0) {
            throw new RuntimeException("用户状态异常");
        }
        
        SysUser user = new SysUser();
        user.setUserId(authUser.getUserId());
        user.setUserName(authUser.getUserName());
        user.setNickName(authUser.getNickName());
        
        return generateToken(user);
    }
    
    private AuthVO generateToken(SysUser user) {
        LoginUser loginUser = LoginUser.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .nickName(user.getNickName())
                .build();
        
        TokenPair tokenPair = authHelper.login(
                loginUser, 
                PlatformType.MINI_PROGRAM, 
                ServletUtils.getClientIp(), 
                ServletUtils.getUserAgent()
        );
        
        return AuthVO.builder()
                .accessToken(tokenPair.getAccessToken())
                .refreshToken(tokenPair.getRefreshToken())
                .expiresIn(tokenPair.getExpiresIn())
                .build();
    }
}
```

### 3. Controller 实现

```java
@RestController
@RequestMapping("/auth/wechat/mp")
@RequiredArgsConstructor
public class WeChatMpAuthController extends BaseController {

    private final IWeChatMpAuthService weChatMpAuthService;

    @PostMapping("/login")
    @PlatformScope(PlatformType.MINI_PROGRAM)
    public R<AuthVO> wechatMpLogin(@RequestBody WechatMpLoginDTO loginDTO) {
        AuthVO authVO = weChatMpAuthService.login(loginDTO.getCode());
        return R.ok(authVO);
    }
}
```

### 4. 小程序端调用

```javascript
// 登录
wx.login({
  success: (res) => {
    if (res.code) {
      wx.request({
        url: 'https://your-api.com/auth/wechat/mp/login',
        method: 'POST',
        data: { code: res.code },
        success: (response) => {
          const { accessToken, refreshToken } = response.data.data;
          wx.setStorageSync('accessToken', accessToken);
          wx.setStorageSync('refreshToken', refreshToken);
          wx.showToast({ title: '登录成功' });
        }
      });
    }
  }
});
```

## 完成！

现在你的微信小程序登录功能已经集成完成。

## 下一步

- 查看 [完整使用指南](./微信小程序OAuth使用指南.md) 了解更多功能
- 查看 [平台管理器文档](../junoyi-framework/junoyi-framework-platform/README.md) 了解如何扩展其他平台

