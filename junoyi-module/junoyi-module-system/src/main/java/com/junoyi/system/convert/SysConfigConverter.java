package com.junoyi.system.convert;

import com.junoyi.system.domain.dto.SysConfigDTO;
import com.junoyi.system.domain.po.SysConfig;
import com.junoyi.system.domain.vo.SysConfigVO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统参数转换器
 *
 * @author Fan
 */
public final class SysConfigConverter {

    private SysConfigConverter() {
    }

    /**
     * PO转VO - 直接映射所有字段，isSystem(0/1)转换为isSystem(Y/N)
     */
    public static SysConfigVO toVo(SysConfig entity) {
        if (entity == null) {
            return null;
        }
        SysConfigVO vo = new SysConfigVO();
        vo.setConfigId(entity.getConfigId());
        vo.setConfigKey(entity.getConfigKey());
        vo.setConfigValue(entity.getConfigValue());
        vo.setConfigName(entity.getConfigName());
        vo.setConfigType(entity.getConfigType());
        vo.setSort(entity.getSort());
        vo.setIsSystem(entity.getIsSystem() != null && entity.getIsSystem() == 1 ? "Y" : "N");
        vo.setStatus(entity.getStatus());
        vo.setRemark(entity.getRemark());
        vo.setCreateTime(entity.getCreateTime() != null
                ? new java.sql.Timestamp(entity.getCreateTime().getTime()).toLocalDateTime()
                : null);
        vo.setUpdateTime(entity.getUpdateTime() != null
                ? new java.sql.Timestamp(entity.getUpdateTime().getTime()).toLocalDateTime()
                : null);
        return vo;
    }

    /**
     * DTO转PO - isSystem(Y/N)转换为isSystem(0/1)
     */
    public static SysConfig toEntity(SysConfigDTO dto) {
        if (dto == null) {
            return null;
        }
        SysConfig entity = new SysConfig();
        entity.setConfigId(dto.getConfigId());
        entity.setConfigKey(dto.getConfigKey());
        entity.setConfigValue(dto.getConfigValue());
        entity.setConfigName(dto.getConfigName());
        entity.setConfigType(dto.getConfigType());
        entity.setSort(dto.getSort());
        entity.setIsSystem("Y".equals(dto.getIsSystem()) ? 1 : 0);
        entity.setStatus(dto.getStatus());
        entity.setRemark(dto.getRemark());
        return entity;
    }

    /**
     * DTO转VO - 直接映射
     */
    public static SysConfigVO dtoToVo(SysConfigDTO dto) {
        if (dto == null) {
            return null;
        }
        SysConfigVO vo = new SysConfigVO();
        vo.setConfigId(dto.getConfigId());
        vo.setConfigKey(dto.getConfigKey());
        vo.setConfigValue(dto.getConfigValue());
        vo.setConfigName(dto.getConfigName());
        vo.setConfigType(dto.getConfigType());
        vo.setSort(dto.getSort());
        vo.setIsSystem(dto.getIsSystem());
        vo.setStatus(dto.getStatus());
        vo.setRemark(dto.getRemark());
        return vo;
    }

    /**
     * Entity 列表转 VO 列表
     */
    public static List<SysConfigVO> toVoList(List<SysConfig> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return Collections.emptyList();
        }
        return entityList.stream()
                .map(SysConfigConverter::toVo)
                .collect(Collectors.toList());
    }

    /**
     * DTO 列表转 Entity 列表
     */
    public static List<SysConfig> toEntityList(List<SysConfigDTO> dtoList) {
        if (dtoList == null || dtoList.isEmpty()) {
            return Collections.emptyList();
        }
        return dtoList.stream()
                .map(SysConfigConverter::toEntity)
                .collect(Collectors.toList());
    }

    /**
     * 更新实体 - 将 DTO 的非空字段更新到 Entity，isSystem(Y/N)转换为isSystem(0/1)
     */
    public static void updateEntity(SysConfigDTO dto, SysConfig entity) {
        if (dto == null || entity == null) {
            return;
        }
        if (dto.getConfigId() != null) {
            entity.setConfigId(dto.getConfigId());
        }
        if (dto.getConfigKey() != null) {
            entity.setConfigKey(dto.getConfigKey());
        }
        if (dto.getConfigValue() != null) {
            entity.setConfigValue(dto.getConfigValue());
        }
        if (dto.getConfigName() != null) {
            entity.setConfigName(dto.getConfigName());
        }
        if (dto.getConfigType() != null) {
            entity.setConfigType(dto.getConfigType());
        }
        if (dto.getSort() != null) {
            entity.setSort(dto.getSort());
        }
        if (dto.getIsSystem() != null) {
            entity.setIsSystem("Y".equals(dto.getIsSystem()) ? 1 : 0);
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getRemark() != null) {
            entity.setRemark(dto.getRemark());
        }
    }
}
