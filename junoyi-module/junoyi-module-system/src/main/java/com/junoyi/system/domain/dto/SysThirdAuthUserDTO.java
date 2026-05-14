package com.junoyi.system.domain.dto;

import lombok.Data;

/**
 * 第三方绑定与系统用户联表查询结果
 */
@Data
public class SysThirdAuthUserDTO {

    /** 绑定记录ID */
    private Long authId;

    /** 绑定中的用户ID */
    private Long bindUserId;

    /** 用户ID */
    private Long userId;

    /** 用户名 */
    private String userName;

    /** 昵称 */
    private String nickName;

    /** 用户状态 */
    private Integer status;

    /** 软删除标识 */
    private Boolean delFlag;
}

