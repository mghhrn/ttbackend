package ir.mghhrn.ttbackend.service;

import ir.mghhrn.ttbackend.dto.LoginDto;
import ir.mghhrn.ttbackend.dto.UserTokenDto;
import ir.mghhrn.ttbackend.dto.VerificationDto;

public interface UserService {
    void registerAndLogin(LoginDto loginDto);
    UserTokenDto verify(VerificationDto verificationDto);
    UserTokenDto createAccessTokenFromRefreshToken(String refreshToken);
}
