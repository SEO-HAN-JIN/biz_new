package com.biz.framework.service.pages;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.MileageHisDto;
import com.biz.framework.dto.pages.MileageReqDto;
import com.biz.framework.mapper.pages.CustomerMapper;
import com.biz.framework.mapper.pages.PreMileageReqMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
@RequiredArgsConstructor
public class PreMileageReqService {

    private final PreMileageReqMapper preMileageReqMapper;
    private final CustomerMapper customerMapper;

    public List<CamelCaseMap> findPreMileageReq(MileageReqDto mileageReqDto) {
        return preMileageReqMapper.findPreMileageReq(mileageReqDto);
    }

    public int savePreMileageReq(MileageReqDto mileageReqDto) {
        int result = 0;

        if ("02".equals(mileageReqDto.getReqGubun())) {
            int mileage = customerMapper.findMileageByBizNo(mileageReqDto.getCustId());
            if (mileage - Integer.parseInt(mileageReqDto.getReqAmt()) < 0) {
                NumberFormat formatter = NumberFormat.getNumberInstance(Locale.KOREA);
                String formattedMileage = formatter.format(mileage);

                throw new ServiceException("요청금액은 현재 고객의 잔여마일리지 보다 작아야합니다.\n고개마일리지: " + formattedMileage);
            }
        }

        switch (mileageReqDto.getRowStatus()) {
            case C -> {
                result += preMileageReqMapper.saveMileageReq(mileageReqDto);
            }
            case U -> {
                if (preMileageReqMapper.checkAlreadyApprov(mileageReqDto) > 0 ) {
                    throw new ServiceException("승인요청건만 변경이 가능합니다.");
                }
                result += preMileageReqMapper.updateMileageReq(mileageReqDto);
            }
        }

        return result;
    }

    public int deletePreMileageReq(MileageReqDto mileageReqDto) {
        if (preMileageReqMapper.checkAlreadyApprov(mileageReqDto) > 0) {
            throw new ServiceException("승인요청건만 삭제가 가능합니다.");
        };
        return preMileageReqMapper.deletePreMileageReq(mileageReqDto);
    }
}
