package com.biz.framework.service.system;

import com.biz.framework.common.dto.CamelCaseMap;
import com.biz.framework.dto.MenuDto;
import com.biz.framework.mapper.system.MenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

    private final MenuMapper menuMapper;

    public List<CamelCaseMap> findUpMenuList() {
        return menuMapper.findUpMenuList();
    }

    public List<CamelCaseMap> findMenuList(MenuDto menuDto) {
        return menuMapper.findMenuListByMenuUpId(menuDto);
    }
}
