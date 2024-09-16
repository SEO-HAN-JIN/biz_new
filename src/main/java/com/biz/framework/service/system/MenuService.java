package com.biz.framework.service.system;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.MenuDto;
import com.biz.framework.mapper.system.MenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

    private final MenuMapper menuMapper;

    public List<CamelCaseMap> findUpMenuList(MenuDto menuDto) {
        return menuMapper.findUpMenuList(menuDto);
    }

    public List<CamelCaseMap> findMenuList(MenuDto menuDto) {
        return menuMapper.findMenuListByMenuUpId(menuDto);
    }

    public int saveMenuList(List<MenuDto> menuDtoList) {
        int result = 0;
        if (!CollectionUtils.isEmpty(menuDtoList)) {
            for (MenuDto menuDto : menuDtoList) {
                switch (menuDto.getRowStatus()) {
                    case C -> result += menuMapper.saveMenuList(menuDto);
                    case U -> result += menuMapper.updateMenuList(menuDto);
                }
            }
        }

        return result;
    }

    public int deleteMenuList(MenuDto menuDto) {
        int result = 0;
        result += menuMapper.deleteMenu(menuDto);
        return result;
    }

    public List<MenuDto> findMenuHierarchy(MenuDto menuDto) {
        List<MenuDto> findList = menuMapper.findMenuList(menuDto);

        List<MenuDto> upMenuList = findList.stream().filter(s -> s.getMenuUpId() == null).toList();
        for (MenuDto upMenu : upMenuList) {
            String menuId = upMenu.getMenuId();
            List<MenuDto> menuList = findList.stream().filter(s -> menuId.equals(s.getMenuUpId())).toList();

            if (!CollectionUtils.isEmpty(menuList)) {
                upMenu.set_children(menuList);
            }
        }
        return upMenuList;
    }
}
