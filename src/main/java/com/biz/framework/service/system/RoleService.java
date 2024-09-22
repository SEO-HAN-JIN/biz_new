package com.biz.framework.service.system;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.system.ProgramDto;
import com.biz.framework.dto.system.RoleDto;
import com.biz.framework.mapper.system.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {

    private final RoleMapper roleMapper;

    public List<CamelCaseMap> findRoels(RoleDto roleDto) {
        return roleMapper.findRoles(roleDto);
    }

    public int saveRoles(List<RoleDto> roleDtoList) {
        int result = 0;
        if (!CollectionUtils.isEmpty(roleDtoList)) {
            for (RoleDto roleDto : roleDtoList) {
                switch (roleDto.getRowStatus()) {
                    case C -> result += roleMapper.saveRoles(roleDto);
                    case U -> result += roleMapper.updateRoles(roleDto);
                }
            }
        }

        return result;
    }

    public int deleteRoles(List<RoleDto> roleDtoList) {
        int result = 0;
        if (!CollectionUtils.isEmpty(roleDtoList)) {
            for (RoleDto roleDto : roleDtoList) {
                result += roleMapper.deleteRoles(roleDto);
            }
        }
        return result;
    }

}
