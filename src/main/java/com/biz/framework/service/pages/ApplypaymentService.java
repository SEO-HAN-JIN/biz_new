package com.biz.framework.service.pages;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.MileageHisDto;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.mapper.pages.ApplypaymentMapper;
import com.biz.framework.mapper.pages.CustomerMapper;
import com.biz.framework.mapper.pages.MileageHisMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplypaymentService {

    private final ApplypaymentMapper applypaymentMapper;
    private final CustomerMapper customerMapper;
    private final MileageHisMapper mileageHisMapper;

    public List<CamelCaseMap> findApplypayment(SettlementDto settlementDto) {
        return applypaymentMapper.findApplypayment(settlementDto);
    }

    /*
        환불요청인 경우에도 해당 메서드 사용 중 (컨트롤러에서 요청구분, 승인여부 세팅)
     */
    public int saveApplypayment(SettlementDto settlementDto) {
        int result = 0;

        switch (settlementDto.getRowStatus()) {
            case C -> {
                // 정산번호 가져오기
                String settlementSeq = applypaymentMapper.createSettlementSeq(settlementDto);

                // 정산요청 저장
                settlementDto.setSettlementSeq(settlementSeq);
                result += applypaymentMapper.saveApplypayment(settlementDto);

                // 킵사용시 마일리지 유효성 검사 및 저장
                if ("Y".equals(settlementDto.getMileageUseInd())) {
                    saveMileageAq(settlementDto, "C");
                }
            }
            case U -> {
                result += applypaymentMapper.updateApplypayment(settlementDto);

                if ("Y".equals(settlementDto.getMileageUseInd())) {
                    saveMileageAq(settlementDto, "U");
                }
            }
        }
        return result;
    }

    public int deleteSettlement(SettlementDto settlementDto) {
        int result = 0;
        if (!"01".equals(applypaymentMapper.checkApplyStatus(settlementDto))) {
            throw new ServiceException("승인요청건만 삭제 가능합니다.");
        }

        result += applypaymentMapper.deleteSettlement(settlementDto);

        if ("Y".equals(settlementDto.getMileageUseInd())) {
            // 고객정보 가져오기
            CustomerDto customerDto = new CustomerDto();
            customerDto.setBizNo(settlementDto.getCustId());
            CamelCaseMap customerInfo = customerMapper.findCustomerInfo(customerDto);

            String bizNo = (String) customerInfo.get("bizNo");
            int existMileage = Integer.parseInt((String) customerInfo.get("mileage"));

            int useMileage = Integer.parseInt(settlementDto.getUseMileage());
            int saveMileage = existMileage + useMileage;

            customerMapper.updateMileage(bizNo, saveMileage);

            if (useMileage > 0) {
                MileageHisDto mileageHisDto = new MileageHisDto();
                mileageHisDto.setBizNo(bizNo);
                mileageHisDto.setEmpId(settlementDto.getUserId());
                mileageHisDto.setMileagePrev(existMileage);
                mileageHisDto.setMileageAft(saveMileage);
                mileageHisDto.setCreatedPage("AQ"); // 정산요청: AQ
                mileageHisDto.setCreatedId(settlementDto.getLoginUserId());
                mileageHisDto.setSettlementSeq(settlementDto.getSettlementSeq());

                mileageHisMapper.saveMileageHis(mileageHisDto);
            }
        }
        return result;
    }

    public List<CamelCaseMap> findSettlement(SettlementDto settlementDto) {
        return applypaymentMapper.findSettlement(settlementDto);
    }

    public double findIncentiveRate(SettlementDto settlementDto) {
        return applypaymentMapper.findIncentiveRate(settlementDto);
    }

    private void saveMileageAq(SettlementDto settlementDto, String rowStatus) {

        // 고객정보 가져오기
        CustomerDto customerDto = new CustomerDto();
        customerDto.setBizNo(settlementDto.getCustId());

        CamelCaseMap customerInfo = customerMapper.findCustomerInfo(customerDto);
        String bizNo = (String) customerInfo.get("bizNo");
        int existMileage = Integer.parseInt((String) customerInfo.get("mileage"));
        int useMileage = Integer.parseInt(settlementDto.getUseMileage());       // 새로 기입한 마일리지
        int restMileage;
        restMileage = existMileage - useMileage;

        if (restMileage < 0) {
            NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(Locale.KOREA);
            String formattedExistMileage = currencyInstance.format(existMileage).replace("₩", "");
            String formattedUseMileage = currencyInstance.format(useMileage).replace("₩", "");;
            throw new ServiceException("킵 사용금액이 잔여킵금액보다 같거나 더 적어야 합니다.\n잔여킵금액: " + formattedExistMileage + "\n킵사용금액: " + formattedUseMileage);
        }

        customerMapper.updateMileage(bizNo, restMileage);
        if (useMileage > 0) {
            MileageHisDto mileageHisDto = new MileageHisDto();
            mileageHisDto.setBizNo(bizNo);
            mileageHisDto.setEmpId(settlementDto.getUserId());
            mileageHisDto.setMileagePrev(existMileage);
            mileageHisDto.setMileageAft(restMileage);
            mileageHisDto.setCreatedPage("AQ"); // 정산요청: AQ
            mileageHisDto.setCreatedId(settlementDto.getLoginUserId());
            mileageHisDto.setSettlementSeq(settlementDto.getSettlementSeq());

            mileageHisMapper.saveMileageHis(mileageHisDto);
        }
    }

}
