package com.biz.framework.restcontroller.system;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.ProgramDto;
import com.biz.framework.service.system.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system/program")
@RequiredArgsConstructor
public class ProgramRestController {

    private final ProgramService programService;

    @GetMapping
    public List<CamelCaseMap> findProgramList(ProgramDto programDto) {
        return programService.findProgramList(programDto);
    }

    @PostMapping
    public int saveProgramList(@RequestBody List<ProgramDto> programDtoList) {
        return programService.saveProgramList(programDtoList);
    }
}
