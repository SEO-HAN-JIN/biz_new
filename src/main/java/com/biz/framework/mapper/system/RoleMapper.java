package com.biz.framework.mapper.system;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.system.RoleDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

    List<CamelCaseMap> findRoles(RoleDto roleDto);
    int saveRoles(RoleDto roleDto);
    int updateRoles(RoleDto roleDto);
    int deleteRoles(RoleDto roleDto);

    int saveRoleUser(RoleDto roleDto);
}
