package com.ktb.community.common.exception;

import com.ktb.community.common.enums.Code;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException{

    private final Code code;

    public GeneralException(Code code) {
        super(code.getMessage());
        this.code = code;
    }

}
