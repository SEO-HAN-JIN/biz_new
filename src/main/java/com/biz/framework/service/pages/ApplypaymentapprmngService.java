package com.biz.framework.service.pages;

import ch.qos.logback.core.util.StringUtil;
import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.MileageHisDto;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.mapper.pages.ApplypaymentMapper;
import com.biz.framework.mapper.pages.ApplypaymentapprmngMapper;
import com.biz.framework.mapper.pages.MileageHisMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplypaymentapprmngService {

    private final ApplypaymentapprmngMapper applypaymentapprmngMapper;
    private final MileageHisMapper mileageHisMapper;

    public List<CamelCaseMap> findApplypaymentmngList(SettlementDto settlementDto) {
        return applypaymentapprmngMapper.findApplypaymentmngList(settlementDto);
    }

    public int confirmApplypayment(SettlementDto settlementDto) {

        int result = 0;

        // 입금확인시 실제입금금액 및 상태 변경
        result = applypaymentapprmngMapper.confirmApplypayment(settlementDto);

        if(result > 0) {
            int mileageAmt = 0;
            if (!StringUtil.isNullOrEmpty(settlementDto.getSaveMileage()))
                mileageAmt = Integer.parseInt(settlementDto.getSaveMileage());

            // 누적마일리지가 있을 경우 고객 마일리지 누적 HIS UPDATE
            if (mileageAmt > 0) {

                MileageHisDto mileageHisDto = new MileageHisDto();
                mileageHisDto.setBizNo(settlementDto.getCustId());          // 고객 key
                mileageHisDto.setEmpId(settlementDto.getUserId());          // 담당자 ID
                mileageHisDto.setMileageAmt(mileageAmt);
                mileageHisDto.setCreatedPage("SC");     // 정산승인
                mileageHisDto.setCreatedId(settlementDto.getLoginUserId());
                result += mileageHisMapper.addMileageHistory(mileageHisDto);
            }
        }

        return result;

    }


    public int cancelApplypayment(SettlementDto settlementDto) {

        if(applypaymentapprmngMapper.checkPayrollInd(settlementDto) >= 0)
            throw new ServiceException("이미 급여처리가 진행된 요청 건입니다.");

        return applypaymentapprmngMapper.cancelApplypayment(settlementDto);
    }
}
