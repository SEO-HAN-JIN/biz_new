package com.biz.framework.service.pages;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.EmpCustomerDto;
import com.biz.framework.dto.pages.MileageHisDto;
import com.biz.framework.dto.pages.SettlementmstDto;
import com.biz.framework.mapper.pages.CustomerMapper;
import com.biz.framework.mapper.pages.EmpCustomerMapper;
import com.biz.framework.mapper.pages.SettlementstatusMapper;
import com.biz.framework.security.dto.AuthenticationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class SettlementstatusService {

    private final SettlementstatusMapper settlementstatusMapper;

    public Map findSettlementstatus(SettlementmstDto settlementmstDto) {
        Map<String, Object> result = new HashMap<>();
        result.put("grid", settlementstatusMapper.findSettlementstatus(settlementmstDto));
        result.put("form", settlementstatusMapper.findSettlementstatusInfo(settlementmstDto));
        return result;
    }
}
