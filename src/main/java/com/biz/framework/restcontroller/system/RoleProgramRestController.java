package com.biz.framework.restcontroller.system;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.system.ProgramDto;
import com.biz.framework.dto.system.RoleDto;
import com.biz.framework.service.system.ProgramService;
import com.biz.framework.service.system.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/system/roles/role_program")
@RequiredArgsConstructor
public class RoleProgramRestController {

    private final RoleService roleService;
    private final ProgramService programService;

    @GetMapping
    public List<CamelCaseMap> findRoles(RoleDto roleDto) {
        return roleService.findRoels(roleDto);
    }

    @GetMapping("/program/list")
    public List<CamelCaseMap> findProgramList(ProgramDto programDto) {
        return programService.findProgramList(programDto);
    }

    @GetMapping("/program")
    public List<CamelCaseMap> findRelProgramList(RoleDto roleDto) {
        return roleService.findProgramList(roleDto);
    }

    @PostMapping("/program/save")
    public int saveRelProgramList(@RequestBody List<ProgramDto> programDtoList) {
        return roleService.saveRelProgramList(programDtoList);
    }

    @DeleteMapping("/program/delete")
    public int deleteRelProgramList(@RequestBody List<ProgramDto> programDtoList) {
        return roleService.deleteRelProgramList(programDtoList);
    }
}
