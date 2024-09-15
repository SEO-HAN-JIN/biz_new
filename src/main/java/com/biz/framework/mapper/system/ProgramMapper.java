package com.biz.framework.mapper.system;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.ProgramDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProgramMapper {
    List<CamelCaseMap> findProgramList(ProgramDto programDto);

    int saveProgramList(ProgramDto programDto);
    int updateProgramList(ProgramDto programDto);
    int deleteProgramList(ProgramDto programDto);
}
