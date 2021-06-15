package ir.mghhrn.ttbackend.controller;

import ir.mghhrn.ttbackend.dto.*;
import ir.mghhrn.ttbackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserRestController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<String> sample() {
        return ResponseEntity.ok("Hello there");
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginDto loginDto) {
        userService.registerAndLogin(loginDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login/sms-verification")
    public ResponseEntity<UserTokenDto> verifyLogin(@Valid @RequestBody VerificationDto verificationDto) {
        return ResponseEntity.ok(userService.verify(verificationDto));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<UserTokenDto> refreshAccessToken(@Valid @RequestBody RefreshTokenDto refreshTokenDto) {
        return ResponseEntity.ok(userService.createAccessTokenFromRefreshToken(refreshTokenDto.getRefreshToken()));
    }

    @PostMapping("/profile")
    public ResponseEntity<Void> updateProfile(@Valid @RequestBody ProfileDto profileDto) {
        userService.updateProfile(profileDto);
        return ResponseEntity.ok().build();
    }
}
