package com.biz.framework.mapper.system;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.system.CodeDto;
import com.biz.framework.dto.system.ProgramDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CodeMapper {
    List<CamelCaseMap> findCdpatt(CodeDto codeDto);

    int saveCdpatt(CodeDto codeDto);
    int updateCdpatt(CodeDto codeDto);
    int deleteCdpatt(CodeDto codeDto);
    int deleteCdbaseByPatternCode(CodeDto codeDto);

    List<CamelCaseMap> findCdbase(CodeDto codeDto);
    int saveCdbase(CodeDto codeDto);
    int updateCdbase(CodeDto codeDto);
    int deleteCdbase(CodeDto codeDto);

    int checkCdbase(CodeDto codeDto);

    List<CodeDto> findCodesByPage(CodeDto codeDto);
}
