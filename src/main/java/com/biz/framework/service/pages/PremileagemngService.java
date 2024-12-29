package com.biz.framework.service.pages;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.MileageHisDto;
import com.biz.framework.dto.pages.MileageReqDto;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.dto.pages.SettlementmstDto;
import com.biz.framework.mapper.pages.ApplypaymentapprmngMapper;
import com.biz.framework.mapper.pages.CustomerMapper;
import com.biz.framework.mapper.pages.MileageHisMapper;
import com.biz.framework.mapper.pages.PremileagemngMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.util.StringUtils;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
@RequiredArgsConstructor
public class PremileagemngService {

    private final PremileagemngMapper premileagemngMapper;
    private final ApplypaymentapprmngMapper applypaymentapprmngMapper;
    private final MileageHisMapper mileageHisMapper;
    private final CustomerMapper customerMapper;

    public List<CamelCaseMap> findPremileagemngList(MileageReqDto mileageReqDto) {
        return premileagemngMapper.findPremileagemngList(mileageReqDto);
    }

    public int confirmMileageReq(MileageReqDto mileageReqDto) {

        int result = 0;
        int reqAmt;

        // 잔여킵환불시 기존 마일리지와 비교하여 요청 마일리지가 더 큰지 확인
        if ("02".equals(mileageReqDto.getReqGubun())) {
            int mileage = customerMapper.findMileageByBizNo(mileageReqDto.getCustId());
            if (mileage - Integer.parseInt(mileageReqDto.getReqAmt()) < 0) {
                NumberFormat formatter = NumberFormat.getNumberInstance(Locale.KOREA);
                String formattedMileage = formatter.format(mileage);

                throw new ServiceException("요청금액은 현재 고객의 잔여마일리지 보다 작아야합니다.\n고개마일리지: " + formattedMileage);
            }

            reqAmt = -(Integer.parseInt(mileageReqDto.getReqAmt()));
        } else {
            reqAmt = Integer.parseInt(mileageReqDto.getReqAmt());
        }

        // 기승인건인지 확인
        this.alreadyApply(mileageReqDto);

        // 결재상태 변경
        result += premileagemngMapper.updateApplyStatus(mileageReqDto);

        if (result > 0) {

            customerMapper.updateFinalMileage(mileageReqDto.getCustId(), reqAmt);

            MileageHisDto mileageHisDto = new MileageHisDto();
            mileageHisDto.setBizNo(mileageReqDto.getCustId());           // 고객 key
            mileageHisDto.setEmpId(mileageReqDto.getUserId());           // 담당자 ID
            mileageHisDto.setSettlementSeq(mileageReqDto.getReqNo());    // 승인번호
            mileageHisDto.setMileageAmt(reqAmt);
            mileageHisDto.setCreatedPage("MR");                           // 마일리지_선입금킵
            mileageHisDto.setCreatedId(mileageReqDto.getLoginUserId());
            mileageHisMapper.addMileageHistory(mileageHisDto);

        }
        else {
            throw new ServiceException("처리 도중 오류가 발생했습니다.");
        }

        if(result == 0) {
            throw new ServiceException("처리 도중 오류가 발생했습니다11.");
        }

        return result;

    }

    public int cancelMileageReq(MileageReqDto mileageReqDto) {

        int result = 0;

        // 기승인건인지 확인
        this.alreadyApply(mileageReqDto);

        // 결재상태 변경
        result += premileagemngMapper.updateApplyStatus(mileageReqDto);

        return result;
    }

    // 기승인건인지 확인
    private void alreadyApply(MileageReqDto mileageReqDto) {
        String applyStatus = premileagemngMapper.findApplyStatus(mileageReqDto);
        if ("02".equals(applyStatus)) {
            throw new ServiceException("이미 승인되었습니다.");
        }

        if ("03".equals(applyStatus)) {
            throw new ServiceException("이미 승인취소되었습니다.");
        }
    }
}
