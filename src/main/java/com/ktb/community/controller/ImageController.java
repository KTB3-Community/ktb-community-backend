package com.ktb.community.controller;

import com.ktb.community.common.dto.DataResponseDto;
import com.ktb.community.common.enums.Code;
import com.ktb.community.dto.UploadImageRequestDto;
import com.ktb.community.dto.UploadImageResponseDto;
import com.ktb.community.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/uploads")
    public DataResponseDto<UploadImageResponseDto> uploadImage(@RequestBody UploadImageRequestDto uploadImageRequestDto) {
        UploadImageResponseDto uploadImageResponseDto = imageService.generatePresignedUrl(uploadImageRequestDto);
        return new DataResponseDto<>(Code.OK, "이미지 업로드 URL이 성공적으로 발급되었습니다.", uploadImageResponseDto);
    }
}
