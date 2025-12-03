package com.ktb.community.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UploadImageResponseDto {

    private String presignedImageUrl;
    private String imageKey;

}
