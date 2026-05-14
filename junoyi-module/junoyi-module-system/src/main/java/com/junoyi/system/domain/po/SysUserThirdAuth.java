package com.junoyi.system.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 系统用户第三方登录绑定
 *
 * @author Fan
 */
@Data
@TableName("sys_user_third_auth")
public class SysUserThirdAuth {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 第三方登录类型
     */
    private String authType;

    /**
     * 第三方平台唯一标识符
     */
    private String authKey;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
