package com.biz.framework.restcontroller;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.service.system.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sidelayout")
@RequiredArgsConstructor
public class SideLayoutRestController {

    private final MenuService menuService;

    @GetMapping
    public List<CamelCaseMap> findAllList() {
        return menuService.findSideLayout();
    }

}
