package com.biz.framework.restcontroller.system;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.system.UserDto;
import com.biz.framework.service.system.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/system/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping
    public List<CamelCaseMap> findUsers(UserDto userDto) {
        return userService.findUsers(userDto);
    }
}
