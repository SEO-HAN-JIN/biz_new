package com.biz.framework.restcontroller.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.RefundItemDto;
import com.biz.framework.service.pages.RefundItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages/refunditem")
@RequiredArgsConstructor
public class RefundItemRestController {

    private final RefundItemService refundItemService;


    @GetMapping
    public List<CamelCaseMap> findRefundItems(RefundItemDto refundItemDto) {
        return refundItemService.findRefundItems(refundItemDto);
    }

    @PostMapping
    public int saveRefundItems(@RequestBody RefundItemDto refundItemDto) {
        return refundItemService.saveRefundItems(refundItemDto);
    }

    @DeleteMapping
    public int deleteRefundItems(@RequestBody RefundItemDto refundItemDto) {
        return refundItemService.deleteRefundItems(refundItemDto);
    }
}
