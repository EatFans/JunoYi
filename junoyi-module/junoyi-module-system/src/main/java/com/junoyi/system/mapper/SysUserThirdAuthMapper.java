package com.junoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.junoyi.system.domain.dto.SysThirdAuthUserDTO;
import com.junoyi.system.domain.po.SysUserThirdAuth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户绑定第三平台 Mapper
 *
 * @author Fan
 */
@Mapper
public interface SysUserThirdAuthMapper extends BaseMapper<SysUserThirdAuth> {

    /**
     * 通过第三方认证信息联表查询绑定关系与系统用户
     *
     * @param authType 第三方登录类型
     * @param authKey 第三方平台唯一标识符
     * @return 绑定与用户快照
     */
    SysThirdAuthUserDTO selectUserSnapshotByAuth(@Param("authType") String authType,
                                                 @Param("authKey") String authKey);
}
