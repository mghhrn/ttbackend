package ir.mghhrn.ttbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class LoginDto {
    @NotEmpty
    @Pattern(regexp = "09[0-9]{9}")
    private String cellphone;
}
