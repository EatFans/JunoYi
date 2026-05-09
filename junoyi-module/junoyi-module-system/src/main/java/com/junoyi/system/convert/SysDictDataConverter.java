package com.junoyi.system.convert;

import com.junoyi.system.domain.dto.SysDictDataDTO;
import com.junoyi.system.domain.po.SysDictData;
import com.junoyi.system.domain.vo.SysDictDataVO;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典数据转换器（静态工具类）
 *
 * @author Fan
 */
public final class SysDictDataConverter {

    private SysDictDataConverter() {
    }

    public static SysDictDataVO toVo(SysDictData entity) {
        if (entity == null) {
            return null;
        }
        SysDictDataVO vo = new SysDictDataVO();
        vo.setDictCode(entity.getDictCode());
        vo.setDictSort(entity.getDictSort());
        vo.setDictLabel(entity.getDictLabel());
        vo.setDictValue(entity.getDictValue());
        vo.setDictType(entity.getDictType());
        vo.setCssClass(entity.getCssClass());
        vo.setListClass(entity.getListClass());
        vo.setIsDefault(entity.getIsDefault());
        vo.setStatus(entity.getStatus());
        vo.setRemark(entity.getRemark());
        vo.setCreateTime(entity.getCreateTime() != null ? new Timestamp(entity.getCreateTime().getTime()).toLocalDateTime() : null);
        vo.setUpdateTime(entity.getUpdateTime() != null ? new Timestamp(entity.getUpdateTime().getTime()).toLocalDateTime() : null);
        return vo;
    }

    public static SysDictData toEntity(SysDictDataDTO dto) {
        if (dto == null) {
            return null;
        }
        SysDictData entity = new SysDictData();
        entity.setDictCode(dto.getDictCode());
        entity.setDictSort(dto.getDictSort());
        entity.setDictLabel(dto.getDictLabel());
        entity.setDictValue(dto.getDictValue());
        entity.setDictType(dto.getDictType());
        entity.setCssClass(dto.getCssClass());
        entity.setListClass(dto.getListClass());
        entity.setIsDefault(dto.getIsDefault());
        entity.setStatus(dto.getStatus());
        entity.setRemark(dto.getRemark());
        return entity;
    }

    public static SysDictDataVO dtoToVo(SysDictDataDTO dto) {
        if (dto == null) {
            return null;
        }
        SysDictDataVO vo = new SysDictDataVO();
        vo.setDictCode(dto.getDictCode());
        vo.setDictSort(dto.getDictSort());
        vo.setDictLabel(dto.getDictLabel());
        vo.setDictValue(dto.getDictValue());
        vo.setDictType(dto.getDictType());
        vo.setCssClass(dto.getCssClass());
        vo.setListClass(dto.getListClass());
        vo.setIsDefault(dto.getIsDefault());
        vo.setStatus(dto.getStatus());
        vo.setRemark(dto.getRemark());
        return vo;
    }

    public static List<SysDictDataVO> toVoList(List<SysDictData> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return Collections.emptyList();
        }
        return entityList.stream().map(SysDictDataConverter::toVo).collect(Collectors.toList());
    }

    public static List<SysDictData> toEntityList(List<SysDictDataDTO> dtoList) {
        if (dtoList == null || dtoList.isEmpty()) {
            return Collections.emptyList();
        }
        return dtoList.stream().map(SysDictDataConverter::toEntity).collect(Collectors.toList());
    }

    public static void updateEntity(SysDictDataDTO dto, SysDictData entity) {
        if (dto == null || entity == null) {
            return;
        }
        if (dto.getDictCode() != null) entity.setDictCode(dto.getDictCode());
        if (dto.getDictSort() != null) entity.setDictSort(dto.getDictSort());
        if (dto.getDictLabel() != null) entity.setDictLabel(dto.getDictLabel());
        if (dto.getDictValue() != null) entity.setDictValue(dto.getDictValue());
        if (dto.getDictType() != null) entity.setDictType(dto.getDictType());
        if (dto.getCssClass() != null) entity.setCssClass(dto.getCssClass());
        if (dto.getListClass() != null) entity.setListClass(dto.getListClass());
        if (dto.getIsDefault() != null) entity.setIsDefault(dto.getIsDefault());
        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());
        if (dto.getRemark() != null) entity.setRemark(dto.getRemark());
    }
}
