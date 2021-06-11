package ir.mghhrn.ttbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class SmsVerificationTry implements Serializable {
    private static final long serialVersionUID = 1944137634963817273L;

    private Long userId;
    private String verificationCode;
    private Integer remainedTries;
    private LocalDateTime createdAt;
}
