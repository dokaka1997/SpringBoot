package com.viettel.arpu.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface IBaseResponse<T> {
    @JsonIgnore
    boolean isEmpty();

    T getData();
}
