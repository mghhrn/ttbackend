package ir.mghhrn.ttbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@NoArgsConstructor
public class RefreshTokenDto {

    @NotEmpty
    private String refreshToken;
}
