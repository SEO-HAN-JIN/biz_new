package com.biz.framework.restcontroller;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.security.dto.AuthenticationDto;
import com.biz.framework.service.system.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sidelayout")
@RequiredArgsConstructor
public class SideLayoutRestController {

    private final MenuService menuService;

    @GetMapping
    public Map<String, Object> findAllList(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthenticationDto authenticationDto = (AuthenticationDto) authentication.getPrincipal();

        List<String> authorityList = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        List<CamelCaseMap> sideLayout = menuService.findSideLayout(authorityList);

        Map<String, Object> result = new HashMap<>();
        result.put("userName", authenticationDto.getUserNm());
        result.put("menuList", sideLayout);

        return result;
    }

}
