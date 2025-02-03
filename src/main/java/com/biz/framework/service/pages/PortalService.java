package com.biz.framework.service.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.dto.pages.SettlementmstDto;
import com.biz.framework.mapper.pages.PortalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PortalService {
    private final PortalMapper portalMapper;

    public List<CamelCaseMap> getApprovalData() {
        return portalMapper.getApprovalData();
    }

    public List<CamelCaseMap> getSettlementData(SettlementmstDto settlementmstDto) {
        return portalMapper.getSettlementData(settlementmstDto);
    }

    public List<CamelCaseMap> getMonthlyPayData(SettlementmstDto settlementmstDto) {
        return portalMapper.getMonthlyPayData(settlementmstDto);
    }
}
