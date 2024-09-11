package com.biz.framework.common.dto;

import com.google.common.base.CaseFormat;

import java.util.LinkedHashMap;

public class CamelCaseMap extends LinkedHashMap {
    @Override
    public Object put(Object key, Object value) {
        return super.put(toLowerCamel(key.toString()), value.toString());
    }

    private static String toLowerCamel(String key) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key);
    }
}
