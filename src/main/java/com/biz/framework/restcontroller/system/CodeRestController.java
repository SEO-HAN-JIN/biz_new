package com.biz.framework.restcontroller.system;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.system.CodeDto;
import com.biz.framework.dto.system.ProgramDto;
import com.biz.framework.service.system.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system/codes")
@RequiredArgsConstructor
public class CodeRestController {

    private final CodeService codeService;

    @GetMapping
    public List<CamelCaseMap> findCdpatt(CodeDto codeDto) {
        return codeService.findCdpatt(codeDto);
    }

    @PostMapping
    public int saveProgramList(@RequestBody List<ProgramDto> programDtoList) {
        return codeService.saveProgramList(programDtoList);
    }

    @DeleteMapping
    public int deleteProgramList(@RequestBody List<ProgramDto> programDtoList) {
        return codeService.deleteProgramList(programDtoList);
    }

    @GetMapping("/rel/menu")
    public List<CamelCaseMap> findMenuProgramList(ProgramDto programDto) {
        return codeService.findProgramMenuList(programDto);
    }

    @PostMapping("/rel/menu")
    public int saveMenuProgramList(@RequestBody List<ProgramDto> programDtoList) {
        return codeService.saveMenuProgramList(programDtoList);
    }

    @DeleteMapping("/rel/menu")
    public int deleteMenuProgramList(@RequestBody List<ProgramDto> programDtoList) {
        return codeService.delteMenuProgramList(programDtoList);
    }
}
