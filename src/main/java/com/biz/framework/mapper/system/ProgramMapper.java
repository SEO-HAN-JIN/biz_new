package com.biz.framework.mapper.system;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.system.ProgramDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProgramMapper {
    List<CamelCaseMap> findProgramList(ProgramDto programDto);

    int saveProgramList(ProgramDto programDto);
    int updateProgramList(ProgramDto programDto);
    int deleteProgramList(ProgramDto programDto);

    List<CamelCaseMap> findProgramMenuList(ProgramDto programDto);
    int saveMenuProgramList(ProgramDto programDto);
    int deleteMenuProgramList(ProgramDto programDto);

    int checkMenuPgmDupl(ProgramDto programDto);

    List<ProgramDto> findRelProgram();

}
