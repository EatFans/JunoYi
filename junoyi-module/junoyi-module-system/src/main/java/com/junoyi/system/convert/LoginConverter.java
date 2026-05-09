package com.junoyi.system.convert;

import com.junoyi.system.domain.bo.LoginBO;
import com.junoyi.system.domain.dto.LoginDTO;

/**
 * 登录对象转换器静态类
 *
 * @author Fan
 */
public final class LoginConverter {

    /**
     * 私有构造函数，防止实例化
     */
    private LoginConverter() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * LoginDTO 转换 LoginBO
     * @param loginDTO LoginDTO
     * @return LoginBO
     */
    public static LoginBO toLoginBo(LoginDTO loginDTO){
        LoginBO loginBO = new LoginBO();
        loginBO.setEmail(loginDTO.getEmail());
        loginBO.setPhonenumber(loginBO.getPhonenumber());
        loginBO.setUsername(loginDTO.getUsername());
        loginBO.setPlatformType(loginDTO.getPlatformType());
        loginBO.setPassword(loginDTO.getPassword());
        return loginBO;
    }
}