package com.biz.framework.service.system;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.system.UserDto;
import com.biz.framework.mapper.system.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    public List<CamelCaseMap> findAllUsers() {
        return userMapper.findAllUsers();
    }

    public List<CamelCaseMap> findUsers(UserDto userDto) {
        return userMapper.findUsers(userDto);
    }
}
