package com.ktb.community.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UploadImageResponseDto {

    private String s3UploadUrl;
    private String imageKey;

}
