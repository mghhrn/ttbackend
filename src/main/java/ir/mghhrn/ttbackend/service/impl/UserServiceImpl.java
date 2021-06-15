package ir.mghhrn.ttbackend.service.impl;

import ir.mghhrn.ttbackend.Repository.UserRepository;
import ir.mghhrn.ttbackend.dto.*;
import ir.mghhrn.ttbackend.entity.User;
import ir.mghhrn.ttbackend.exception.handler.TTBusinessException;
import ir.mghhrn.ttbackend.security.JwtTokenType;
import ir.mghhrn.ttbackend.security.TokenHelper;
import ir.mghhrn.ttbackend.service.UserService;
import ir.mghhrn.ttbackend.sms.KavenegarSmsService;
import ir.mghhrn.ttbackend.util.SecurityUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    public static final int VERIFICATION_CODE_LENGTH = 4;
    private final UserRepository userRepository;
    private final KavenegarSmsService kavenegarSmsService;
    private final String REGISTRATION_CACHE;
    private final CacheManager cacheManager;
    private final TokenHelper tokenHelper;


    @Override
    @Transactional
    public void registerAndLogin(LoginDto loginDto) {
        Optional<User> userOptional = userRepository.findByCellphone(loginDto.getCellphone());
        User user;
        if (userOptional.isEmpty()) {
            user = new User();
            user.setCellphone(loginDto.getCellphone());
            user.setCellphoneValidated(false);
            userRepository.save(user);
        } else {
            user = userOptional.get();
        }

        String verificationCode = RandomStringUtils.randomNumeric(VERIFICATION_CODE_LENGTH);
        SmsVerificationTry newTry = new SmsVerificationTry();
        newTry.setUserId(user.getId());
        newTry.setRemainedTries(3);
        newTry.setVerificationCode(verificationCode);
        newTry.setCreatedAt(LocalDateTime.now());
        cacheManager.getCache(REGISTRATION_CACHE).put(user.getCellphone(), newTry);

        kavenegarSmsService.sendVerificationSms(loginDto.getCellphone(), newTry.getVerificationCode());
    }


    @Override
    @Transactional
    public UserTokenDto verify(VerificationDto verificationDto) {
        SmsVerificationTry smsVerificationTry = cacheManager.getCache(REGISTRATION_CACHE)
                .get(verificationDto.getCellphone(), SmsVerificationTry.class);
        if (smsVerificationTry == null) {
            throw new TTBusinessException("error.user.no.login.code");
        }

        if (smsVerificationTry.getRemainedTries() == 0) {
            cacheManager.getCache(REGISTRATION_CACHE).evict(verificationDto.getCellphone());
            throw new TTBusinessException("error.user.exceeded.number.of.tries");
        }

        if (smsVerificationTry.getVerificationCode().equals(verificationDto.getVerificationCode())) {
            User user = userRepository.findByCellphone(verificationDto.getCellphone())
                    .orElseThrow(() -> new TTBusinessException("error.user.not.found"));
            user.setCellphoneValidated(true);
            userRepository.save(user);
            cacheManager.getCache(REGISTRATION_CACHE).evict(verificationDto.getCellphone());
            return tokenHelper.generateUserToken(user);
        } else {
            smsVerificationTry.setRemainedTries(smsVerificationTry.getRemainedTries() - 1);
            cacheManager.getCache(REGISTRATION_CACHE).put(verificationDto.getCellphone(), smsVerificationTry);
            throw new TTBusinessException("error.user.provided.code.is.not.correct");
        }
    }

    @Override
    public UserTokenDto createAccessTokenFromRefreshToken(String refreshToken) {
        Long userId = tokenHelper.getUserIdFromToken(refreshToken);
        JwtTokenType tokenType = tokenHelper.getTokenTypeFromToken(refreshToken);
        if (!tokenType.equals(JwtTokenType.REFRESH)) {
            throw new TTBusinessException("error.token.invalid.token.type");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new TTBusinessException("error.user.not.found"));
        return new UserTokenDto(tokenHelper.generateAccessToken(user),
                refreshToken, tokenHelper.getAccessTokenExpireTimeInMinute(), userId);
    }

    @Override
    @Transactional
    public void updateProfile(ProfileDto profileDto) {
        User user = userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new TTBusinessException("error.user.not.found"));
        if (user.getGender() != null) {
            throw new TTBusinessException("error.user.profile.update.not.allowed");
        }
        user.setGender(profileDto.getGender());
        user.setAge(profileDto.getAge());
        userRepository.save(user);
    }
}
