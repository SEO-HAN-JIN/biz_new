package com.biz.framework.restcontroller.system;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.system.RoleDto;
import com.biz.framework.service.system.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system/roles")
@RequiredArgsConstructor
public class RoleRestController {

    private final RoleService roleService;

    @GetMapping
    public List<CamelCaseMap> findRoles(RoleDto roleDto) {
        return roleService.findRoels(roleDto);
    }

    @PostMapping
    public int saveRoles(@RequestBody List<RoleDto> roleDtoList) {
        return roleService.saveRoles(roleDtoList);
    }

    @DeleteMapping
    public int deleteRoles(@RequestBody List<RoleDto> roleDtoList) {
        return roleService.deleteRoles(roleDtoList);
    }

//    @GetMapping("/users")
//    public List<CamelCaseMap> findRelUsers(RoleDto roleDto) {
//        return
//    }

    @PostMapping("/users")
    public int saveRoleUser(@RequestBody List<RoleDto> roleDtoList) {
        return roleService.saveRoleUsers(roleDtoList);
    }

}
