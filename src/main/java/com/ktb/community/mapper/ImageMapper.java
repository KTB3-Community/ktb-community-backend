package com.ktb.community.mapper;

import com.ktb.community.dto.UploadImageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@RequiredArgsConstructor
public class ImageMapper {

    public UploadImageResponseDto mapToUploadImageResponseDto(URL s3UploadUrl, String key) {
        return UploadImageResponseDto.builder()
                .s3UploadUrl(s3UploadUrl.toString())
                .imageKey(key)
                .build();
    }
}
