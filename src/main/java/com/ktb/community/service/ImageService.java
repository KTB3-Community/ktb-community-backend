package com.ktb.community.service;

import com.ktb.community.dto.UploadImageRequestDto;
import com.ktb.community.dto.UploadImageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URL;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3Presigner s3Presigner;
    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 클라이언트로부터 파일명, content type 받아서 url 생성
    public UploadImageResponseDto generatePresignedUrl(UploadImageRequestDto uploadImageRequestDto) {

        String filename = uploadImageRequestDto.getFileName();
        String contentType = uploadImageRequestDto.getContentType();

        String key = "uploads/" + UUID.randomUUID() + "-" + filename;

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(5))
                .putObjectRequest(objectRequest)
                .build();

        URL uploadUrl = s3Presigner.presignPutObject(presignRequest).url();

        return UploadImageResponseDto.builder()
                .s3UploadUrl(uploadUrl.toString())
                .imageKey(key)
                .build();
    }

    // DB에 저장된 프로필 이미지 Key 값을 기반으로 url 생성, 주로 프로필 이미지 GET 하는 경우에 사용
    public String generatePresignedUrlWithKey(String key, Duration duration) {
        if (key == null || key.isEmpty()) return null;

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        GetObjectPresignRequest getPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(duration)
                .getObjectRequest(getObjectRequest)
                .build();

        return s3Presigner.presignGetObject(getPresignRequest).url().toString();
    }

    public void deleteImage(String key) {
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build());
    }

}
