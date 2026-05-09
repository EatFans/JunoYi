package com.junoyi.system.convert;

import com.junoyi.system.domain.dto.SysDictTypeDTO;
import com.junoyi.system.domain.po.SysDictType;
import com.junoyi.system.domain.vo.SysDictTypeVO;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典类型转换器（静态工具类）
 *
 * @author Fan
 */
public final class SysDictTypeConverter {

    private SysDictTypeConverter() {
    }

    public static SysDictTypeVO toVo(SysDictType entity) {
        if (entity == null) {
            return null;
        }
        SysDictTypeVO vo = new SysDictTypeVO();
        vo.setDictId(entity.getDictId());
        vo.setDictName(entity.getDictName());
        vo.setDictType(entity.getDictType());
        vo.setStatus(entity.getStatus());
        vo.setRemark(entity.getRemark());
        vo.setCreateTime(entity.getCreateTime() != null ? new Timestamp(entity.getCreateTime().getTime()).toLocalDateTime() : null);
        vo.setUpdateTime(entity.getUpdateTime() != null ? new Timestamp(entity.getUpdateTime().getTime()).toLocalDateTime() : null);
        return vo;
    }

    public static SysDictType toEntity(SysDictTypeDTO dto) {
        if (dto == null) {
            return null;
        }
        SysDictType entity = new SysDictType();
        entity.setDictId(dto.getDictId());
        entity.setDictName(dto.getDictName());
        entity.setDictType(dto.getDictType());
        entity.setStatus(dto.getStatus());
        entity.setRemark(dto.getRemark());
        return entity;
    }

    public static SysDictTypeVO dtoToVo(SysDictTypeDTO dto) {
        if (dto == null) {
            return null;
        }
        SysDictTypeVO vo = new SysDictTypeVO();
        vo.setDictId(dto.getDictId());
        vo.setDictName(dto.getDictName());
        vo.setDictType(dto.getDictType());
        vo.setStatus(dto.getStatus());
        vo.setRemark(dto.getRemark());
        return vo;
    }

    public static List<SysDictTypeVO> toVoList(List<SysDictType> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return Collections.emptyList();
        }
        return entityList.stream().map(SysDictTypeConverter::toVo).collect(Collectors.toList());
    }

    public static List<SysDictType> toEntityList(List<SysDictTypeDTO> dtoList) {
        if (dtoList == null || dtoList.isEmpty()) {
            return Collections.emptyList();
        }
        return dtoList.stream().map(SysDictTypeConverter::toEntity).collect(Collectors.toList());
    }

    public static void updateEntity(SysDictTypeDTO dto, SysDictType entity) {
        if (dto == null || entity == null) {
            return;
        }
        if (dto.getDictId() != null) entity.setDictId(dto.getDictId());
        if (dto.getDictName() != null) entity.setDictName(dto.getDictName());
        if (dto.getDictType() != null) entity.setDictType(dto.getDictType());
        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());
        if (dto.getRemark() != null) entity.setRemark(dto.getRemark());
    }
}
