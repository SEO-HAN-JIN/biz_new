package com.biz.framework.mapper.system;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.system.MenuDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface MenuMapper {

    List<CamelCaseMap> findUpMenuList(MenuDto menuDto);
    List<CamelCaseMap> findMenuListByMenuUpId(MenuDto menuDto);

    int saveMenuList(MenuDto menuDto);
    int updateMenuList(MenuDto menuDto);
    int deleteMenu(MenuDto menuDto);

    List<MenuDto> findMenuList(MenuDto menuDto);

    List<CamelCaseMap> findSideLayout(Map<String, Object> param);
}
