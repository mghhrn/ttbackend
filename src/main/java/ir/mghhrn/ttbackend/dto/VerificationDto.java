package ir.mghhrn.ttbackend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static ir.mghhrn.ttbackend.service.impl.UserServiceImpl.VERIFICATION_CODE_LENGTH;

@Getter
@Setter
@NoArgsConstructor
public class VerificationDto {

    @NotEmpty
    @Size(min = VERIFICATION_CODE_LENGTH, max = VERIFICATION_CODE_LENGTH)
    private String verificationCode;

    @NotEmpty
    @Pattern(regexp = "09[0-9]{9}")
    private String cellphone;
}
