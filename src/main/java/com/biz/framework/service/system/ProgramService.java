package com.biz.framework.service.system;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.MenuDto;
import com.biz.framework.dto.ProgramDto;
import com.biz.framework.mapper.system.ProgramMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProgramService {

    private final ProgramMapper programMapper;

    public List<CamelCaseMap> findProgramList(ProgramDto programDto) {
        return programMapper.findProgramList(programDto);
    }

    public int saveProgramList(List<ProgramDto> programDtoList) {
        int result = 0;
        if (!CollectionUtils.isEmpty(programDtoList)) {
            for (ProgramDto programDto : programDtoList) {
                switch (programDto.getRowStatus()) {
                    case C -> result += programMapper.saveProgramList(programDto);
                    case U -> result += programMapper.updateProgramList(programDto);
                }
            }
        }

        return result;
    }

    public int deleteProgramList(ProgramDto programDto) {
        int result = 0;
        result += programMapper.deleteProgramList(programDto);
        return result;
    }
}
