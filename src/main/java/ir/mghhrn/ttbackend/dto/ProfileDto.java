package ir.mghhrn.ttbackend.dto;

import ir.mghhrn.ttbackend.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ProfileDto implements Serializable {
    private static final long serialVersionUID = -4699880980569490401L;

    @NotEmpty
    private Gender gender;

    @NotNull
    @Min(0)
    private Long age;
}
