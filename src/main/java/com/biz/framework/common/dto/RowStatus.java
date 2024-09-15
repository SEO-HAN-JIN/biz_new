package com.biz.framework.common.dto;

import lombok.Getter;

@Getter
public enum RowStatus {
    C("생성"), U("수정"), D("삭제");

    private final String desc;

    RowStatus(String desc) {
        this.desc = desc;
    }
}
