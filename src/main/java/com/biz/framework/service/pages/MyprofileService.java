package com.biz.framework.service.pages;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.EmpDto;
import com.biz.framework.dto.system.UserDto;
import com.biz.framework.mapper.pages.CustomerMapper;
import com.biz.framework.mapper.pages.EmpMapper;
import com.biz.framework.mapper.pages.MyprofileMapper;
import com.biz.framework.mapper.system.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MyprofileService {

    private final MyprofileMapper myprofileMapper;


    public CamelCaseMap findMyprofile(EmpDto empDto) {
        return myprofileMapper.findMyprofile(empDto);
    }

    public int saveMyprofile(EmpDto empDto) {
        return 1;
    }

    public int changePassword(EmpDto empDto) {
        return 1;
    }

//    public int saveEmp(EmpDto empDto) {
//        int result = 0;
//
//        if (empDto.isNew()) {
//            if (empMapper.checkDuplEmpId(empDto) > 0) {
//                throw new ServiceException("중복된 ID의 직원이 존재합니다.");
//            }
//        }
//
//        result += empMapper.saveEmp(empDto);
//
//        UserDto userDto = new UserDto();
//        userDto.setUserId(empDto.getEmpId());
//        userDto.setUserNm(empDto.getEmpName());
//        userDto.setUserPw(empDto.getUserPw());
//
//        result += userMapper.saveUser(userDto);
//
//        return result;
//    }
//
//    public int deleteEmp(EmpDto empDto) {
//        int result = 0;
//
//        result += empMapper.deleteEmp(empDto);
//
//        UserDto userDto = new UserDto();
//        userDto.setUserId(empDto.getEmpId());
//
//        result += userMapper.deleteUser(userDto);
//        return result;
//    }
//
//    public List<CamelCaseMap> findAllEmps(EmpDto empDto) {
//        return empMapper.findAllEmps(empDto);
//    }
}