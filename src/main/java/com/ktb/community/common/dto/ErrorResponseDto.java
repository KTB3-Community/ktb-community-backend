package com.ktb.community.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ktb.community.common.enums.Code;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.ALWAYS)
public class ErrorResponseDto extends ResponseDto {

    private final Object data = null;

    public ErrorResponseDto(Code code) {
        super(code.getCode(), code.getMessage());
    }
}
