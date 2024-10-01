package com.biz.framework.mapper.system;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.MenuDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MenuMapper {

    List<CamelCaseMap> findUpMenuList(MenuDto menuDto);
    List<CamelCaseMap> findMenuListByMenuUpId(MenuDto menuDto);

    int saveMenuList(MenuDto menuDto);
    int updateMenuList(MenuDto menuDto);
    int deleteMenu(MenuDto menuDto);

    List<MenuDto> findMenuList();

    List<CamelCaseMap> findSideLayout();
}
