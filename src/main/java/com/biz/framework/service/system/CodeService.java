package com.biz.framework.service.system;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.system.CodeDto;
import com.biz.framework.dto.system.ProgramDto;
import com.biz.framework.mapper.system.CodeMapper;
import com.biz.framework.mapper.system.ProgramMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CodeService {

    private final CodeMapper codeMapper;

    public List<CamelCaseMap> findCdpatt(CodeDto codeDto) {
        return codeMapper.findCdpatt(codeDto);
    }

    public int saveProgramList(List<ProgramDto> programDtoList) {
        int result = 0;
        if (!CollectionUtils.isEmpty(programDtoList)) {
            for (ProgramDto programDto : programDtoList) {
                switch (programDto.getRowStatus()) {
                    case C -> result += codeMapper.saveProgramList(programDto);
                    case U -> result += codeMapper.updateProgramList(programDto);
                }
            }
        }

        return result;
    }

    public int deleteProgramList(List<ProgramDto> programDtoList) {
        int result = 0;
        if (!CollectionUtils.isEmpty(programDtoList)) {
            for (ProgramDto programDto : programDtoList) {
                result += codeMapper.deleteProgramList(programDto);
            }
        }
        return result;
    }

    public List<CamelCaseMap> findProgramMenuList(ProgramDto programDto) {
        return codeMapper.findProgramMenuList(programDto);
    }

    public int saveMenuProgramList(List<ProgramDto> programDtoList) {
        int result = 0;
        if (!CollectionUtils.isEmpty(programDtoList)) {
            for (ProgramDto programDto : programDtoList) {
                int checkResult = codeMapper.checkMenuPgmDupl(programDto);
                if (checkResult > 0) {
                    throw new ServiceException("중복된 데이터가 존재합니다.");
                }
                result += codeMapper.saveMenuProgramList(programDto);
            }
        }
        return result;
    }


    public int delteMenuProgramList(List<ProgramDto> programDtoList) {
        int result = 0;
        if (!CollectionUtils.isEmpty(programDtoList)) {
            for (ProgramDto programDto : programDtoList) {
                result += codeMapper.deleteMenuProgramList(programDto);
            }
        }
        return result;
    }

    public List<ProgramDto> findRelProgram() {
        return codeMapper.findRelProgram();
    }
}
