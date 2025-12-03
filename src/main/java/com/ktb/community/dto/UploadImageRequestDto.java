package com.ktb.community.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UploadImageRequestDto {

    private String fileName;
    private String contentType;

}
