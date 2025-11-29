package com.biz.framework.mapper.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.RefundItemDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RefundItemMapper {

    List<CamelCaseMap> findRefundItems(RefundItemDto refundItemDto);

    int saveRefundItem(RefundItemDto refundItemDto);

    int updateRefundItem(RefundItemDto refundItemDto);

    int deleteRefundItem(RefundItemDto refundItemDto);
}

