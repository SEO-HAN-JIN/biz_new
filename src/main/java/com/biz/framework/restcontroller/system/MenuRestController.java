package com.biz.framework.restcontroller.system;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.MenuDto;
import com.biz.framework.service.system.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/system/menu")
@RequiredArgsConstructor
public class MenuRestController {

    private final MenuService menuService;

    @GetMapping("/up/list")
    public List<CamelCaseMap> findUpMenuList(MenuDto menuDto) {
        return menuService.findUpMenuList(menuDto);
    }

    @PostMapping("/up/list")
    public int saveUpMenuList(@RequestBody List<MenuDto> menuDtoList) {
        return menuService.saveMenuList(menuDtoList);
    }

    @DeleteMapping("/up/list")
    public int deleteUpMenuList(@RequestBody MenuDto menuDto) {
        return menuService.deleteMenuList(menuDto);
    }

    @GetMapping("/list")
    public List<CamelCaseMap> findMenuList(MenuDto menuDto) {
        return menuService.findMenuList(menuDto);
    }

    @PostMapping("/list")
    public int saveMenuList(@RequestBody List<MenuDto> menuDtoList) {
        return menuService.saveMenuList(menuDtoList);
    }

    @DeleteMapping("/list")
    public int deleteMenuList(@RequestBody MenuDto menuDto) {
        return menuService.deleteMenuList(menuDto);
    }

    @GetMapping("/hierarchy")
    public List<MenuDto> findMenuHierarchy(MenuDto menuDto) {
        return menuService.findMenuHierarchy(menuDto);
    }

    @GetMapping("/sidelayout")
    public List<CamelCaseMap> findAllList() {
        return menuService.findSideLayout();
    }
}
