package com.biz.framework.service.pages;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.EmpDto;
import com.biz.framework.dto.system.UserDto;
import com.biz.framework.mapper.pages.CustomerMapper;
import com.biz.framework.mapper.pages.EmpMapper;
import com.biz.framework.mapper.system.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmpService {

    private final CustomerMapper customerMapper;
    private final EmpMapper empMapper;
    private final UserMapper userMapper;


    public List<CamelCaseMap> findEmps(EmpDto empDto) {
        return empMapper.findEmps(empDto);
    }

    public int saveEmp(EmpDto empDto) {
        int result = 0;

        if (empDto.isNew()) {
            if (empMapper.checkDuplEmpId(empDto) > 0) {
                throw new ServiceException("중복된 ID의 직원이 존재합니다.");
            }
        }

        result += empMapper.saveEmp(empDto);

//        UserDto userDto = new UserDto();
//        userDto.setUserId(empDto.getEmpId());
//        userDto.setUserNm(empDto.getEmpName());
//        userDto.setUserPw(empDto.getUserPw());
//
//        result += userMapper.saveUser(userDto);

        return result;
    }

    public int deleteEmp(EmpDto empDto) {
        int result = 0;

        result += empMapper.deleteEmp(empDto);

        UserDto userDto = new UserDto();
        userDto.setUserId(empDto.getEmpId());

        return result;
    }

    public List<CamelCaseMap> findAllEmps(EmpDto empDto) {
        return empMapper.findAllEmps(empDto);
    }

    public List<CamelCaseMap> findEmpSelect(EmpDto empDto) {
        return empMapper.findEmpSelect(empDto);
    }
}
