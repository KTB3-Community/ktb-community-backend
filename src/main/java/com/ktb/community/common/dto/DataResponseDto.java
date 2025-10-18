package com.ktb.community.common.dto;

import com.ktb.community.common.enums.Code;
import lombok.Getter;

@Getter
public class DataResponseDto<T> extends ResponseDto {

    private final T data;

    public DataResponseDto(Code code, T data) {
        super(code.getCode(), code.getMessage());
        this.data = data;
    }

    public DataResponseDto(Code code, String message, T data) {
        super(code.getCode(), message);
        this.data = data;
    }
}
