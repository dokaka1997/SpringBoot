package com.viettel.arpu.model.response;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.Optional;

@Getter
public class PageResponse<T extends Page<?>> extends BaseResponse<T> {

    public PageResponse(T data) {
        super(data);
    }

    @Override
    public boolean isEmpty() {
        return Optional.ofNullable(data).map(Page::isEmpty).orElse(false);
    }
}
