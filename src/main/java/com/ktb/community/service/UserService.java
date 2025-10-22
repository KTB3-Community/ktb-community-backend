package com.ktb.community.service;

import com.ktb.community.common.enums.Code;
import com.ktb.community.common.exception.GeneralException;
import com.ktb.community.domain.User;
import com.ktb.community.domain.enums.Role;
import com.ktb.community.dto.*;
import com.ktb.community.mapper.UserMapper;
import com.ktb.community.repository.UserRepository;
import com.ktb.community.util.password.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final ImageService imageService;
    private final UserMapper userMapper;

    public CreateUserResponseDto createUser(CreateUserRequestDto createUserRequestBody) {

        // todo : email 중복 검사 (id 역할)

        String email = createUserRequestBody.getEmail();
        String nickname = createUserRequestBody.getNickname();
        String password = PasswordUtils.hashPassword(createUserRequestBody.getPassword());
        String profileImageKey = createUserRequestBody.getProfileImageKey();

        if (profileImageKey == null || profileImageKey.isBlank()) {
            profileImageKey = null;
        }

        User user = userRepository.save(User.createUser(email, nickname, password, profileImageKey, Role.USER));

        return userMapper.mapToCreateUserResponseDto(user);
    }

    public UpdateUserInfoResponseDto updateUserInfo(Long userId, UpdateUserInfoRequestDto updateUserInfoRequestDto) {

        User user = userRepository.findById(userId);

        String updatedNickname = updateUserInfoRequestDto.getNickname();

        if (user.getNickname().equals(updatedNickname)) {

        }

        userRepository.save(user.updateProfileInfo(updatedNickname));

        String profileImageUrl = imageService.generatePresignedUrlWithKey(user.getProfileImageKey(), Duration.ofHours(1));

        return userMapper.mapToUpdateUserInfoResponseDto(userId, user, profileImageUrl);
    }

    public void updateUserPassword(Long userId, UpdateUserPasswordRequestDto updateUserPasswordRequestDto) {

        User user = userRepository.findById(userId);

        String updatedPlainPassword = updateUserPasswordRequestDto.getPassword();
        String updatedHashPassword = PasswordUtils.hashPassword(updatedPlainPassword);


        if (PasswordUtils.checkPassword(updatedPlainPassword, user.getPassword())) {
            throw new GeneralException(Code.CONFLICT);
        }

        userRepository.save(user.updatePassword(updatedHashPassword));
    }

    public UpdateUserImageResponseDto updateUserImage(Long userId, UploadImageRequestDto uploadImageRequestDto) {

        User user = userRepository.findById(userId);

        if (user.getProfileImageKey() != null) {
            imageService.deleteImage(user.getProfileImageKey());
        }

        UploadImageResponseDto uploadImageResponseDto = imageService.generatePresignedUrl(uploadImageRequestDto);
        String profileImageUrl = uploadImageResponseDto.getS3UploadUrl();

        userRepository.save(user.updateProfileImage(profileImageUrl));

        return userMapper.mapToUpdateUserImageResponseDto(user, profileImageUrl);
    }

    public void deleteUserImage(Long userId) {

        User user = userRepository.findById(userId);

        if (user.getProfileImageKey() != null) {
            imageService.deleteImage(user.getProfileImageKey());
        }

        userRepository.save(user.deleteProfileImage());
    }

    public void deleteUser(Long userId) {
        userRepository.delete(userId);
    }
}

