package com.junoyi.system.convert;

import com.junoyi.system.domain.dto.SysMenuDTO;
import com.junoyi.system.domain.po.SysMenu;
import com.junoyi.system.domain.vo.SysMenuVO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统菜单转换器（静态工具类）
 *
 * @author Fan
 */
public final class SysMenuConverter {

    private SysMenuConverter() {
    }

    public static SysMenuVO toVo(SysMenu entity) {
        if (entity == null) {
            return null;
        }
        SysMenuVO vo = new SysMenuVO();
        vo.setId(entity.getId());
        vo.setParentId(entity.getParentId());
        vo.setName(entity.getName());
        vo.setPath(entity.getPath());
        vo.setComponent(entity.getComponent());
        vo.setTitle(entity.getTitle());
        vo.setIcon(entity.getIcon());
        vo.setMenuType(entity.getMenuType());
        vo.setSort(entity.getSort());
        vo.setIsHide(entity.getIsHide());
        vo.setIsHideTab(entity.getIsHideTab());
        vo.setKeepAlive(entity.getKeepAlive());
        vo.setIsIframe(entity.getIsIframe());
        vo.setLink(entity.getLink());
        vo.setIsFullPage(entity.getIsFullPage());
        vo.setFixedTab(entity.getFixedTab());
        vo.setActivePath(entity.getActivePath());
        vo.setShowBadge(entity.getShowBadge());
        vo.setShowTextBadge(entity.getShowTextBadge());
        vo.setPermission(entity.getPermission());
        vo.setStatus(entity.getStatus());
        vo.setRemark(entity.getRemark());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }

    public static SysMenu toEntity(SysMenuDTO dto) {
        if (dto == null) {
            return null;
        }
        SysMenu entity = new SysMenu();
        entity.setId(dto.getId());
        entity.setParentId(dto.getParentId());
        entity.setName(dto.getName());
        entity.setPath(dto.getPath());
        entity.setComponent(dto.getComponent());
        entity.setTitle(dto.getTitle());
        entity.setIcon(dto.getIcon());
        entity.setMenuType(dto.getMenuType());
        entity.setSort(dto.getSort());
        entity.setIsHide(dto.getIsHide());
        entity.setIsHideTab(dto.getIsHideTab());
        entity.setKeepAlive(dto.getKeepAlive());
        entity.setIsIframe(dto.getIsIframe());
        entity.setLink(dto.getLink());
        entity.setIsFullPage(dto.getIsFullPage());
        entity.setFixedTab(dto.getFixedTab());
        entity.setActivePath(dto.getActivePath());
        entity.setShowBadge(dto.getShowBadge());
        entity.setShowTextBadge(dto.getShowTextBadge());
        entity.setPermission(dto.getPermission());
        entity.setStatus(dto.getStatus());
        entity.setRemark(dto.getRemark());
        return entity;
    }

    public static SysMenuVO dtoToVo(SysMenuDTO dto) {
        if (dto == null) {
            return null;
        }
        SysMenuVO vo = new SysMenuVO();
        vo.setId(dto.getId());
        vo.setParentId(dto.getParentId());
        vo.setName(dto.getName());
        vo.setPath(dto.getPath());
        vo.setComponent(dto.getComponent());
        vo.setTitle(dto.getTitle());
        vo.setIcon(dto.getIcon());
        vo.setMenuType(dto.getMenuType());
        vo.setSort(dto.getSort());
        vo.setIsHide(dto.getIsHide());
        vo.setIsHideTab(dto.getIsHideTab());
        vo.setKeepAlive(dto.getKeepAlive());
        vo.setIsIframe(dto.getIsIframe());
        vo.setLink(dto.getLink());
        vo.setIsFullPage(dto.getIsFullPage());
        vo.setFixedTab(dto.getFixedTab());
        vo.setActivePath(dto.getActivePath());
        vo.setShowBadge(dto.getShowBadge());
        vo.setShowTextBadge(dto.getShowTextBadge());
        vo.setPermission(dto.getPermission());
        vo.setStatus(dto.getStatus());
        vo.setRemark(dto.getRemark());
        return vo;
    }

    public static List<SysMenuVO> toVoList(List<SysMenu> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return Collections.emptyList();
        }
        return entityList.stream().map(SysMenuConverter::toVo).collect(Collectors.toList());
    }

    public static List<SysMenu> toEntityList(List<SysMenuDTO> dtoList) {
        if (dtoList == null || dtoList.isEmpty()) {
            return Collections.emptyList();
        }
        return dtoList.stream().map(SysMenuConverter::toEntity).collect(Collectors.toList());
    }

    public static void updateEntity(SysMenuDTO dto, SysMenu entity) {
        if (dto == null || entity == null) {
            return;
        }
        if (dto.getId() != null) entity.setId(dto.getId());
        if (dto.getParentId() != null) entity.setParentId(dto.getParentId());
        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getPath() != null) entity.setPath(dto.getPath());
        if (dto.getComponent() != null) entity.setComponent(dto.getComponent());
        if (dto.getTitle() != null) entity.setTitle(dto.getTitle());
        if (dto.getIcon() != null) entity.setIcon(dto.getIcon());
        if (dto.getMenuType() != null) entity.setMenuType(dto.getMenuType());
        if (dto.getSort() != null) entity.setSort(dto.getSort());
        if (dto.getIsHide() != null) entity.setIsHide(dto.getIsHide());
        if (dto.getIsHideTab() != null) entity.setIsHideTab(dto.getIsHideTab());
        if (dto.getKeepAlive() != null) entity.setKeepAlive(dto.getKeepAlive());
        if (dto.getIsIframe() != null) entity.setIsIframe(dto.getIsIframe());
        if (dto.getLink() != null) entity.setLink(dto.getLink());
        if (dto.getIsFullPage() != null) entity.setIsFullPage(dto.getIsFullPage());
        if (dto.getFixedTab() != null) entity.setFixedTab(dto.getFixedTab());
        if (dto.getActivePath() != null) entity.setActivePath(dto.getActivePath());
        if (dto.getShowBadge() != null) entity.setShowBadge(dto.getShowBadge());
        if (dto.getShowTextBadge() != null) entity.setShowTextBadge(dto.getShowTextBadge());
        if (dto.getPermission() != null) entity.setPermission(dto.getPermission());
        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());
        if (dto.getRemark() != null) entity.setRemark(dto.getRemark());
    }
}
