package com.viettel.arpu.model.response;

import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * @Author VuHQ
 * @Since 6/10/2020
 */
public class CollectionResponse<T extends Collection<?>> extends BaseResponse<T> {

    public CollectionResponse(T data) {
        super(data);
    }

    @Override
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(data);
    }
}
