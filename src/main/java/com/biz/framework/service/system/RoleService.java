package com.biz.framework.service.system;

import com.biz.framework.common.exception.ServiceException;
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

    public int saveRoleUsers(List<RoleDto> roleDtoList) {
        int result = 0;
        if (!CollectionUtils.isEmpty(roleDtoList)) {
            for (RoleDto roleDto : roleDtoList) {
                if (roleMapper.checkRoleUserDupl(roleDto) > 0) {
                    throw new ServiceException("중복된 데이터가 존재합니다.");
                };
                result += roleMapper.saveRoleUser(roleDto);
            }
        }

        return result;
    }

    public List<CamelCaseMap> findRoleUser(RoleDto roleDto) {
        roleDto.setCoCode(roleDto.getLoginCoId());
        return roleMapper.findRoleUser(roleDto);
    }

    public int deleteRoleUser(List<RoleDto> roleDtoList) {
        int result = 0;
        if (!CollectionUtils.isEmpty(roleDtoList)) {
            for (RoleDto roleDto : roleDtoList) {
                result += roleMapper.deleteRoleUser(roleDto);
            }
        }
        return result;
    }

    public List<CamelCaseMap> findProgramList(RoleDto roleDto) {
        return roleMapper.findProgramList(roleDto);
    }

    public int saveRelProgramList(List<ProgramDto> programDtoList) {
        int result = 0;
        if (!CollectionUtils.isEmpty(programDtoList)) {
            for (ProgramDto programDto : programDtoList) {
                result += roleMapper.saveRelProgramList(programDto);
            }
        }

        return result;
    }

    public int deleteRelProgramList(List<ProgramDto> programDtoList) {
        int result = 0;
        if (!CollectionUtils.isEmpty(programDtoList)) {
            for (ProgramDto programDto : programDtoList) {
                result += roleMapper.deleteRelProgramList(programDto);
            }
        }

        return result;
    }
}
