package com.biz.framework.service.system;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.system.ProgramDto;
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

    public int deleteProgramList(List<ProgramDto> programDtoList) {
        int result = 0;
        if (!CollectionUtils.isEmpty(programDtoList)) {
            for (ProgramDto programDto : programDtoList) {
                result += programMapper.deleteProgramList(programDto);
            }
        }
        return result;
    }

    public List<CamelCaseMap> findProgramMenuList(ProgramDto programDto) {
        return programMapper.findProgramMenuList(programDto);
    }

    public int saveMenuProgramList(List<ProgramDto> programDtoList) {
        int result = 0;
        if (!CollectionUtils.isEmpty(programDtoList)) {
            for (ProgramDto programDto : programDtoList) {
                int checkResult = programMapper.checkMenuPgmDupl(programDto);
                if (checkResult > 0) {
                    throw new ServiceException("중복된 데이터가 존재합니다.");
                }
                result += programMapper.saveMenuProgramList(programDto);
            }
        }
        return result;
    }


    public int delteMenuProgramList(List<ProgramDto> programDtoList) {
        int result = 0;
        if (!CollectionUtils.isEmpty(programDtoList)) {
            for (ProgramDto programDto : programDtoList) {
                result += programMapper.deleteMenuProgramList(programDto);
            }
        }
        return result;
    }

    public List<ProgramDto> findRelProgram() {
        return programMapper.findRelProgram();
    }
}
