package com.ktb.community.controller;

import com.ktb.community.common.dto.DataResponseDto;
import com.ktb.community.common.enums.Code;
import com.ktb.community.dto.UpdateUserImageResponseDto;
import com.ktb.community.dto.UploadImageRequestDto;
import com.ktb.community.dto.UploadImageResponseDto;
import com.ktb.community.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/images/uploads")
    public DataResponseDto<UploadImageResponseDto> uploadImage(@RequestBody UploadImageRequestDto uploadImageRequestDto) {
        UploadImageResponseDto uploadImageResponseDto = imageService.generatePresignedUrl(uploadImageRequestDto);
        return new DataResponseDto<>(Code.OK, "이미지 업로드가 성공적으로 완료되었습니다.", uploadImageResponseDto);
    }

    @DeleteMapping("/images/{imageKey}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserImage(@PathVariable String imageKey) {
        imageService.deleteImage(imageKey);
    }
}
