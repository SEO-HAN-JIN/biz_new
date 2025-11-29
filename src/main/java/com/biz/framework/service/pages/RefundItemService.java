package com.biz.framework.service.pages;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.RefundItemDto;
import com.biz.framework.mapper.pages.RefundItemMapper;
import com.biz.framework.security.dto.AuthenticationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RefundItemService {

    private final RefundItemMapper refundItemMapper;

    public List<CamelCaseMap> findRefundItems(RefundItemDto refundItemDto) {
        return refundItemMapper.findRefundItems(refundItemDto);
    }

    public int saveRefundItems(RefundItemDto refundItemDto) {
        int result = 0;
        switch (refundItemDto.getRowStatus()) {
            case C -> {
                result += refundItemMapper.saveRefundItem(refundItemDto);
            }
            case U -> {
                result += refundItemMapper.updateRefundItem(refundItemDto);
            }
        }

        return result;
    }

    public int deleteRefundItems(RefundItemDto refundItemDto) {
        int result = 0;
        result += refundItemMapper.deleteRefundItem(refundItemDto);
        return result;
    }
}
