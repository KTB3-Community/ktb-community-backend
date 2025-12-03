package com.ktb.community.service;

import com.ktb.community.common.enums.Code;
import com.ktb.community.common.exception.GeneralException;
import com.ktb.community.domain.User;
import com.ktb.community.domain.enums.Role;
import com.ktb.community.dto.*;
import com.ktb.community.mapper.UserMapper;
import com.ktb.community.repository.UserRepository;
import com.ktb.community.util.password.PasswordUtils;
import com.ktb.community.util.user.UserAuthenticationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

import static com.ktb.community.util.validation.UserInfoValidationUtils.isValidEmail;
import static com.ktb.community.util.validation.UserInfoValidationUtils.isValidPassword;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final ImageService imageService;
    private final UserMapper userMapper;
    private final UserAuthenticationUtils userAuthenticationUtils;

    public CreateUserResponseDto createUser(CreateUserRequestDto createUserRequestBody) {

        String email = createUserRequestBody.getEmail();
        String nickname = createUserRequestBody.getNickname();
        String rawPassword = createUserRequestBody.getPassword();
        String profileImageKey = createUserRequestBody.getProfileImageKey();

        // 이메일 중복 검증
        if (userRepository.findByEmail(email).isPresent()) {
            throw new GeneralException(Code.EMAIL_CONFLICT);
        }
        // 닉네임 중복 검증
        if (userRepository.findByNickname(nickname).isPresent()) {
            throw new GeneralException(Code.NICKNAME_CONFLICT);
        }
        // 이메일 형식 검증
        if (!isValidEmail(email)) {
            throw new GeneralException(Code.INVALID_EMAIL_FORMAT);
        }
        // 비밀번호 형식 검증
        if (!isValidPassword(rawPassword)) {
            throw new GeneralException(Code.INVALID_PASSWORD_FORMAT);
        }
        // 프로필 이미지 키 값 초기화
        if (profileImageKey == null || profileImageKey.isBlank()) {
            profileImageKey = null;
        }

        String hashedPassword = PasswordUtils.hashPassword(rawPassword);

        User user = userRepository.save(User.createUser(email, nickname, hashedPassword, profileImageKey, Role.USER));

        return userMapper.mapToCreateUserResponseDto(user);
    }

    public UserProfileDto getUserProfileInfo() {
        User user = userAuthenticationUtils.getCurrentUser();
        String presignedProfileUrl = imageService.generatePresignedUrlWithKey(user.getProfileImageKey(), Duration.ofHours(1));
        return UserProfileDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .presignedProfileUrl(presignedProfileUrl)
                .build();
    }

    public UpdateUserInfoResponseDto updateUserInfo(UpdateUserInfoRequestDto updateUserInfoRequestDto) {

        User user = userAuthenticationUtils.getCurrentUser();

        if (updateUserInfoRequestDto.getNickname() == null || updateUserInfoRequestDto.getNickname().isBlank()) {
            throw new GeneralException(Code.INVALID_INPUT_FORMAT);
        }
        if (updateUserInfoRequestDto.getProfileImageKey() == null || updateUserInfoRequestDto.getProfileImageKey().isBlank()) {
            throw new GeneralException(Code.INVALID_INPUT_FORMAT);
        }
        if (updateUserInfoRequestDto.getPassword() == null || updateUserInfoRequestDto.getPassword().isBlank()) {
            throw new GeneralException(Code.INVALID_INPUT_FORMAT);
        }

        String nickname = updateUserInfoRequestDto.getNickname();
        String imageKey = updateUserInfoRequestDto.getProfileImageKey();
        String plainPassword = updateUserInfoRequestDto.getPassword();

        boolean nicknameSame = user.getNickname().equals(nickname);
        boolean imageSame = user.getProfileImageKey().equals(imageKey);
        boolean passwordSame = PasswordUtils.checkPassword(plainPassword, user.getPassword());

        // 변경사항이 하나도 없으면 에러
        if (nicknameSame && imageSame && passwordSame) {
            throw new GeneralException(Code.IDENTITY_DATA);
        }

        // 비밀번호 형식 검증
        if (!isValidPassword(plainPassword)) {
            throw new GeneralException(Code.INVALID_PASSWORD_FORMAT);
        }

        // 최종 비밀번호 (변경 요청 없으면 기존 값 유지)
        String hashedPassword = PasswordUtils.hashPassword(plainPassword);


        // 업데이트
        user.updateProfileInfo(nickname, imageKey, hashedPassword);
        userRepository.save(user);

        String profileImageUrl = imageService.generatePresignedUrlWithKey(imageKey, Duration.ofHours(1));

        return userMapper.mapToUpdateUserInfoResponseDto(user.getId(), user, profileImageUrl);
    }


    public void deleteUser() {
        User user = userAuthenticationUtils.getCurrentUser();
        userRepository.delete(user);
    }


}

