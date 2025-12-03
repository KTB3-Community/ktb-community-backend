package com.ktb.community.service;

import com.ktb.community.common.enums.Code;
import com.ktb.community.common.exception.GeneralException;
import com.ktb.community.domain.Post;
import com.ktb.community.domain.PostImage;
import com.ktb.community.dto.UpdatePostImageResponseDto;
import com.ktb.community.dto.UploadImageRequestDto;
import com.ktb.community.dto.UploadImageResponseDto;
import com.ktb.community.mapper.PostMapper;
import com.ktb.community.repository.PostImageRepository;
import com.ktb.community.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostImageService {


    private final PostRepository postRepository;
    private final ImageService imageService;
    private final PostMapper postMapper;
    private final PostImageRepository postImageRepository;

    public UpdatePostImageResponseDto updatePostImage(Long postId, UploadImageRequestDto uploadImageRequestDto) {

        PostImage postImage = postImageRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(Code.POST_IMAGE_NOT_FOUND));

        if (postImage.getPostImageKey() != null) {
            imageService.deleteImage(postImage.getPostImageKey());
        }

        UploadImageResponseDto uploadImageResponseDto = imageService.generatePresignedUrl(uploadImageRequestDto);
        String postImageUrl = uploadImageResponseDto.getPresignedImageUrl();
        String key = uploadImageResponseDto.getImageKey();

        PostImage updatedPostImage = postImage.updatePostImage(key);

        return postMapper.mapToUpdatePostImageResponseDto(postId, postImageUrl, updatedPostImage);
    }

    public void deletePostImage(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(Code.POST_NOT_FOUND));

        if (post.getPostImageKey() != null) {
            imageService.deleteImage(post.getPostImageKey()); // s3 버킷에서 이미지 지우는 메서드
        }

        PostImage postImage = postImageRepository.findByPostId(postId);
        postImage.deletePostImage();
        postImageRepository.delete(postImage);
    }
}
