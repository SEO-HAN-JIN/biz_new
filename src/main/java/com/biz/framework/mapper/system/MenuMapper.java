package com.biz.framework.mapper.system;

import com.biz.framework.common.dto.CamelCaseMap;
import com.biz.framework.dto.MenuDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {

    List<CamelCaseMap> findUpMenuList();
    List<CamelCaseMap> findMenuListByMenuUpId(MenuDto menuDto);
}
