package com.biz.framework.restcontroller.system;

import com.biz.framework.common.dto.CamelCaseMap;
import com.biz.framework.dto.MenuDto;
import com.biz.framework.service.system.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/system/menu")
@RequiredArgsConstructor
public class MenuRestController {

    private final MenuService menuService;

    @GetMapping("/up/list")
    public List<CamelCaseMap> findUpMenuList() {
        return menuService.findUpMenuList();
    }

    @GetMapping("/list")
    public List<CamelCaseMap> findMenuList(MenuDto menuDto) {
        return menuService.findMenuList(menuDto);
    }
}
