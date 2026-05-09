package com.junoyi.system.convert;

import com.junoyi.system.domain.dto.SysDeptDTO;
import com.junoyi.system.domain.po.SysDept;
import com.junoyi.system.domain.vo.SysDeptVO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统部门转换器（静态工具类）
 *
 * @author Fan
 */
public final class SysDeptConverter {

    private SysDeptConverter() {
    }

    public static SysDeptVO toVo(SysDept sysDept) {
        if (sysDept == null) {
            return null;
        }
        SysDeptVO vo = new SysDeptVO();
        vo.setId(sysDept.getId());
        vo.setParentId(sysDept.getParentId());
        vo.setName(sysDept.getName());
        vo.setSort(sysDept.getSort());
        vo.setLeader(sysDept.getLeader());
        vo.setPhonenumber(sysDept.getPhonenumber());
        vo.setEmail(sysDept.getEmail());
        vo.setStatus(sysDept.getStatus());
        vo.setCreateTime(sysDept.getCreateTime());
        vo.setUpdateTime(sysDept.getUpdateTime());
        vo.setRemark(sysDept.getRemark());
        return vo;
    }

    public static List<SysDeptVO> toVoList(List<SysDept> sysDeptList) {
        if (sysDeptList == null || sysDeptList.isEmpty()) {
            return Collections.emptyList();
        }
        return sysDeptList.stream()
                .map(SysDeptConverter::toVo)
                .collect(Collectors.toList());
    }

    public static SysDept toPo(SysDeptDTO dto) {
        if (dto == null) {
            return null;
        }
        SysDept entity = new SysDept();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getParentId() != null) {
            entity.setParentId(dto.getParentId());
        }
        entity.setName(dto.getName());
        if (dto.getSort() != null) {
            entity.setSort(dto.getSort());
        }
        entity.setLeader(dto.getLeader());
        entity.setPhonenumber(dto.getPhonenumber());
        entity.setEmail(dto.getEmail());
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        entity.setRemark(dto.getRemark());
        return entity;
    }
}
