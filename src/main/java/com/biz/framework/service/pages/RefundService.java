package com.biz.framework.service.pages;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.mapper.pages.ApplypaymentMapper;
import com.biz.framework.mapper.pages.RefundMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RefundService {

    private final ApplypaymentMapper applypaymentMapper;
    private final RefundMapper refundMapper;

    public List<CamelCaseMap> findApplypayment(SettlementDto settlementDto) {
        return applypaymentMapper.findApplypayment(settlementDto);
    }

    public int saveRefund(SettlementDto settlementDto) {
        int result = 0;

        // 정산번호 가져오기
        String settlementSeq = applypaymentMapper.createSettlementSeq(settlementDto);

        // 정산요청 저장
        settlementDto.setSettlementSeq(settlementSeq);
        result += applypaymentMapper.saveApplypayment(settlementDto);

        return result;
    }

    public int deleteRefund(SettlementDto settlementDto) {
        if (!"01".equals(applypaymentMapper.checkApplyStatus(settlementDto))) {
            throw new ServiceException("환불요청건만 삭제 가능합니다.");
        }

        return applypaymentMapper.deleteSettlement(settlementDto);
    }

    public List<CamelCaseMap> findRefund(SettlementDto settlementDto) {
        return refundMapper.findRefund(settlementDto);
    }

    public List<CamelCaseMap> findSettlement(SettlementDto settlementDto) {
        return refundMapper.findSettlement(settlementDto);
    }
}
