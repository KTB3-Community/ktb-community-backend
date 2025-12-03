package com.ktb.community.mapper;

import com.ktb.community.domain.User;
import com.ktb.community.dto.CreateUserResponseDto;
import com.ktb.community.dto.UpdateUserImageResponseDto;
import com.ktb.community.dto.UpdateUserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {


    public CreateUserResponseDto mapToCreateUserResponseDto(User user) {
        return CreateUserResponseDto.builder()
                .userId(user.getId())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public UpdateUserInfoResponseDto mapToUpdateUserInfoResponseDto(Long userId, User user, String presignedProfileUrl) {
        System.out.println(presignedProfileUrl);
        return UpdateUserInfoResponseDto.builder()
                .userId(userId)
                .nickname(user.getNickname())
                .presignedProfileUrl(presignedProfileUrl)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public UpdateUserImageResponseDto mapToUpdateUserImageResponseDto(User user, String presignedProfileImageUrl) {
        return UpdateUserImageResponseDto.builder()
                .presignedProfileImageUrl(presignedProfileImageUrl)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
