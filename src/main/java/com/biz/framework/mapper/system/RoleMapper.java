package com.biz.framework.mapper.system;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.system.ProgramDto;
import com.biz.framework.dto.system.RoleDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

    List<CamelCaseMap> findRoles(RoleDto roleDto);
    int saveRoles(RoleDto roleDto);
    int updateRoles(RoleDto roleDto);
    int deleteRoles(RoleDto roleDto);

    int checkRoleUserDupl(RoleDto roleDto);

    int saveRoleUser(RoleDto roleDto);
    List<CamelCaseMap> findRoleUser(RoleDto roleDto);

    int deleteRoleUser(RoleDto roleDto);

    List<CamelCaseMap> findProgramList(RoleDto roleDto);

    int saveRelProgramList(ProgramDto programDto);

    int deleteRelProgramList(ProgramDto programDto);
}
