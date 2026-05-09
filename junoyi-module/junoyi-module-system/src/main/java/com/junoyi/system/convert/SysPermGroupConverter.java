package com.junoyi.system.convert;

import com.junoyi.system.domain.dto.SysPermGroupDTO;
import com.junoyi.system.domain.po.SysPermGroup;
import com.junoyi.system.domain.vo.SysPermGroupVO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限组转换器（静态工具类）
 *
 * @author Fan
 */
public final class SysPermGroupConverter {

    private SysPermGroupConverter() {
    }

    public static SysPermGroupVO toVo(SysPermGroup permGroup) {
        if (permGroup == null) {
            return null;
        }
        SysPermGroupVO vo = new SysPermGroupVO();
        vo.setId(permGroup.getId());
        vo.setGroupCode(permGroup.getGroupCode());
        vo.setGroupName(permGroup.getGroupName());
        vo.setPriority(permGroup.getPriority());
        vo.setDescription(permGroup.getDescription());
        vo.setStatus(permGroup.getStatus());
        vo.setPermissions(permGroup.getPermissions());
        vo.setCreateTime(permGroup.getCreateTime());
        vo.setUpdateTime(permGroup.getUpdateTime());
        return vo;
    }

    public static List<SysPermGroupVO> toVoList(List<SysPermGroup> permGroupList) {
        if (permGroupList == null || permGroupList.isEmpty()) {
            return Collections.emptyList();
        }
        return permGroupList.stream().map(SysPermGroupConverter::toVo).collect(Collectors.toList());
    }

    public static SysPermGroup toPo(SysPermGroupDTO dto) {
        if (dto == null) {
            return null;
        }
        SysPermGroup po = new SysPermGroup();
        po.setId(dto.getId());
        po.setGroupCode(dto.getGroupCode());
        po.setGroupName(dto.getGroupName());
        po.setPriority(dto.getPriority());
        po.setDescription(dto.getDescription());
        po.setStatus(dto.getStatus());
        po.setPermissions(dto.getPermissions());
        return po;
    }

    public static List<SysPermGroup> toPoList(List<SysPermGroupDTO> dtoList) {
        if (dtoList == null || dtoList.isEmpty()) {
            return Collections.emptyList();
        }
        return dtoList.stream().map(SysPermGroupConverter::toPo).collect(Collectors.toList());
    }

    public static void updatePo(SysPermGroupDTO dto, SysPermGroup po) {
        if (dto == null || po == null) {
            return;
        }
        if (dto.getId() != null) po.setId(dto.getId());
        if (dto.getGroupCode() != null) po.setGroupCode(dto.getGroupCode());
        if (dto.getGroupName() != null) po.setGroupName(dto.getGroupName());
        if (dto.getPriority() != null) po.setPriority(dto.getPriority());
        if (dto.getDescription() != null) po.setDescription(dto.getDescription());
        if (dto.getStatus() != null) po.setStatus(dto.getStatus());
        if (dto.getPermissions() != null) po.setPermissions(dto.getPermissions());
    }
}
