package ir.mghhrn.ttbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserTokenDto {
    private String accessToken;
    private String refreshToken;
    private Long expiresInMinutes; // minute
    private Long userId;
}
