package com.biz.framework.service.system;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.system.CodeDto;
import com.biz.framework.dto.system.ProgramDto;
import com.biz.framework.mapper.system.CodeMapper;
import com.biz.framework.mapper.system.ProgramMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CodeService {

    private final CodeMapper codeMapper;

    public List<CamelCaseMap> findCdpatt(CodeDto codeDto) {
        return codeMapper.findCdpatt(codeDto);
    }

    public int saveMaster(List<CodeDto> codeDtoList) {
        int result = 0;
        if (!CollectionUtils.isEmpty(codeDtoList)) {
            for (CodeDto codeDto : codeDtoList) {
                switch (codeDto.getRowStatus()) {
                    case C -> result += codeMapper.saveCdpatt(codeDto);
                    case U -> result += codeMapper.updateCdpatt(codeDto);
                }
            }
        }

        return result;
    }

    public int deleteMaster(List<CodeDto> codeDtoList) {
        int result = 0;
        if (!CollectionUtils.isEmpty(codeDtoList)) {
            for (CodeDto codeDto : codeDtoList) {
                result += codeMapper.deleteCdpatt(codeDto);
                result += codeMapper.deleteCdbaseByPatternCode(codeDto);
            }
        }
        return result;
    }

    public List<CamelCaseMap> findDetailList(CodeDto codeDto) {
        return codeMapper.findCdbase(codeDto);
    }

    public int saveDetail(List<CodeDto> codeDtoList) {
        int result = 0;
        if (!CollectionUtils.isEmpty(codeDtoList)) {
            for (CodeDto codeDto : codeDtoList) {
                switch (codeDto.getRowStatus()) {
                    case C:
                        int checkResult = codeMapper.checkCdbase(codeDto);
                        if (checkResult > 0) {
                            throw new ServiceException("중복된 데이터가 존재합니다.");
                        }
                        result += codeMapper.saveCdbase(codeDto);
                        break;
                    case U :
                        result += codeMapper.updateCdbase(codeDto);
                        break;
                    default:
                        break;
                }
            }
        }
        return result;
    }


    public int deleteDetail(List<CodeDto> codeDtoList) {
        int result = 0;
        if (!CollectionUtils.isEmpty(codeDtoList)) {
            for (CodeDto codeDto : codeDtoList) {
                result += codeMapper.deleteCdbase(codeDto);
            }
        }
        return result;
    }

    public HashMap<String, List<CodeDto>> findCodesByPage(List<CodeDto> codeDtoList) {
        HashMap<String, List<CodeDto>> map = new HashMap<>();

        if (!CollectionUtils.isEmpty(codeDtoList)) {
            for (CodeDto dto : codeDtoList) {
                map.put(dto.getPatternCode(), codeMapper.findCodesByPage(dto));
            }
        }

        return map;
    }
}
