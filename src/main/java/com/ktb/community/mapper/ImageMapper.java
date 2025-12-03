package com.ktb.community.mapper;

import com.ktb.community.dto.UploadImageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@RequiredArgsConstructor
public class ImageMapper {

    public UploadImageResponseDto mapToUploadImageResponseDto(URL presignedImageUrl, String key) {
        return UploadImageResponseDto.builder()
                .presignedImageUrl(presignedImageUrl.toString())
                .imageKey(key)
                .build();
    }
}
