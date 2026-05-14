package com.junoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.junoyi.system.domain.dto.SysThirdAuthUserDTO;
import com.junoyi.system.domain.po.SysUserThirdAuth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
    @Select("""
            SELECT
                ta.id AS authId,
                ta.user_id AS bindUserId,
                u.user_id AS userId,
                u.user_name AS userName,
                u.nick_name AS nickName,
                u.status AS status,
                u.del_flag AS delFlag
            FROM sys_user_third_auth ta
            LEFT JOIN sys_user u ON ta.user_id = u.user_id
            WHERE ta.auth_type = #{authType}
              AND ta.auth_key = #{authKey}
            LIMIT 1
            """)
    SysThirdAuthUserDTO selectUserSnapshotByAuth(@Param("authType") String authType,
                                                 @Param("authKey") String authKey);
}
