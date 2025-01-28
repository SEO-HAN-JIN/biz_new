package com.biz.framework.service.pages;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.AgencyReqDto;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.MileageHisDto;
import com.biz.framework.dto.pages.AgencyReqDto;
import com.biz.framework.mapper.pages.AgencyReqMapper;
import com.biz.framework.mapper.pages.ApplypaymentMapper;
import com.biz.framework.mapper.pages.CustomerMapper;
import com.biz.framework.mapper.pages.MileageHisMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
@RequiredArgsConstructor
public class AgencyReqService {

    private final AgencyReqMapper agencyReqMapper;
    private final CustomerMapper customerMapper;
    private final MileageHisMapper mileageHisMapper;

    public List<CamelCaseMap> findApplypayment(AgencyReqDto agencyReqDto) {
        return agencyReqMapper.findApplypayment(agencyReqDto);
    }

    /*
        환불요청인 경우에도 해당 메서드 사용 중 (컨트롤러에서 요청구분, 승인여부 세팅)
     */
    public int saveApplypayment(AgencyReqDto agencyReqDto) {
        int result = 0;

        switch (agencyReqDto.getRowStatus()) {
            case C -> {
                // 정산번호 가져오기
                String settlementSeq = agencyReqMapper.createSettlementSeq(agencyReqDto);

                // 정산요청 저장
                agencyReqDto.setSettlementSeq(settlementSeq);
                result += agencyReqMapper.saveApplypayment(agencyReqDto);

                // 킵사용시 마일리지 유효성 검사 및 저장
                if ("Y".equals(agencyReqDto.getMileageUseInd())) {
                    saveMileageAq(agencyReqDto);
                }
            }
            case U -> {
                result += agencyReqMapper.updateApplypayment(agencyReqDto);

                if ("Y".equals(agencyReqDto.getMileageUseInd())) {
                    saveMileageAq(agencyReqDto);
                }
            }
        }
        return result;
    }

    public int deleteSettlement(AgencyReqDto agencyReqDto) {
        int result = 0;

        if(!CollectionUtils.isEmpty(agencyReqDto.getAgencyReqDtoList()))
        {
            for(AgencyReqDto dto : agencyReqDto.getAgencyReqDtoList())
            {
                if (!"01".equals(agencyReqMapper.checkApplyStatus(dto))) {
                    throw new ServiceException("승인요청건만 삭제 가능합니다.");
                }

                result += agencyReqMapper.deleteSettlement(dto);

                if ("Y".equals(dto.getMileageUseInd())) {
                    // 고객정보 가져오기
                    CustomerDto customerDto = new CustomerDto();
                    customerDto.setBizNo(dto.getCustId());
                    CamelCaseMap customerInfo = customerMapper.findCustomerInfo(customerDto);

                    String bizNo = (String) customerInfo.get("bizNo");
                    int useMileage = Integer.parseInt(dto.getUseMileage());

                    customerMapper.updateFinalMileage(dto.getLoginCoId(), bizNo, useMileage);

                    if (useMileage > 0) {
                        MileageHisDto mileageHisDto = new MileageHisDto();
                        mileageHisDto.setBizNo(bizNo);
                        mileageHisDto.setEmpId(dto.getUserId());
                        mileageHisDto.setSettlementSeq(dto.getSettlementSeq());
                        mileageHisDto.setMileageAmt(useMileage);
                        mileageHisDto.setCreatedPage("AQ"); // 정산요청: AQ
                        mileageHisDto.setCreatedId(dto.getLoginUserId());

                        mileageHisMapper.addMileageHistory(mileageHisDto);
                    }
                }
            }
        }


        return result;
    }

    public List<CamelCaseMap> findSettlement(AgencyReqDto agencyReqDto) {
        return agencyReqMapper.findSettlement(agencyReqDto);
    }

    public double findIncentiveRate(AgencyReqDto agencyReqDto) {
        return agencyReqMapper.findIncentiveRate(agencyReqDto);
    }

    private void saveMileageAq(AgencyReqDto agencyReqDto) {

        // 고객정보 가져오기
        CustomerDto customerDto = new CustomerDto();
        customerDto.setBizNo(agencyReqDto.getCustId());
        CamelCaseMap customerInfo = customerMapper.findCustomerInfo(customerDto);

        String bizNo = (String) customerInfo.get("bizNo");
        int existMileage = Integer.parseInt((String) customerInfo.get("mileage"));  // 기존 고객 잔여 마일리지
        int useMileage = -(Integer.parseInt(agencyReqDto.getUseMileage()));        // 사용할 마일리지
        int restMileage = existMileage + useMileage;                                // 잔여 마일리지 - 사용 마일리지

        if (restMileage < 0) {
            NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(Locale.KOREA);
            String formattedExistMileage = currencyInstance.format(existMileage).replace("₩", "");
            String formattedUseMileage = currencyInstance.format(useMileage).replace("₩", "");;
            throw new ServiceException("킵 사용금액이 잔여킵금액보다 같거나 더 적어야 합니다.\n잔여킵금액: " + formattedExistMileage + "\n킵사용금액: " + formattedUseMileage);
        }

        customerMapper.updateFinalMileage(agencyReqDto.getLoginCoId(), bizNo, useMileage);
        MileageHisDto mileageHisDto = new MileageHisDto();
        mileageHisDto.setBizNo(bizNo);
        mileageHisDto.setEmpId(agencyReqDto.getUserId());
        mileageHisDto.setSettlementSeq(agencyReqDto.getSettlementSeq());
        mileageHisDto.setMileageAmt(useMileage);
        mileageHisDto.setCreatedPage("AQ"); // 정산요청: AQ
        mileageHisDto.setCreatedId(agencyReqDto.getLoginUserId());
        mileageHisMapper.addMileageHistory(mileageHisDto);
    }

    public int cancelSettlement(AgencyReqDto agencyReqDto) {
        int result = 0;

        if(!CollectionUtils.isEmpty(agencyReqDto.getAgencyReqDtoList()))
        {
            for(AgencyReqDto dto : agencyReqDto.getAgencyReqDtoList())
            {
                if (!"01".equals(agencyReqMapper.checkApplyStatus(dto))) {
                    throw new ServiceException("승인요청건만 삭제 가능합니다.");
                }

                result += agencyReqMapper.cancelSettlement(dto);

                if ("Y".equals(dto.getMileageUseInd())) {
                    // 고객정보 가져오기
                    CustomerDto customerDto = new CustomerDto();
                    customerDto.setBizNo(dto.getCustId());
                    CamelCaseMap customerInfo = customerMapper.findCustomerInfo(customerDto);

                    String bizNo = (String) customerInfo.get("bizNo");
                    int useMileage = Integer.parseInt(dto.getUseMileage());

                    customerMapper.updateFinalMileage(dto.getLoginCoId(), bizNo, useMileage);

                    if (useMileage > 0) {
                        MileageHisDto mileageHisDto = new MileageHisDto();
                        mileageHisDto.setBizNo(bizNo);
                        mileageHisDto.setEmpId(dto.getUserId());
                        mileageHisDto.setSettlementSeq(dto.getSettlementSeq());
                        mileageHisDto.setMileageAmt(useMileage);
                        mileageHisDto.setCreatedPage("CQ"); // 취소요청: CQ
                        mileageHisDto.setCreatedId(dto.getLoginUserId());

                        mileageHisMapper.addMileageHistory(mileageHisDto);
                    }
                }
            }
        }


        return result;
    }
}
