package ir.mghhrn.ttbackend.dto;

import ir.mghhrn.ttbackend.enums.AudioBalance;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class TherapySessionDto {

    private Double selectedFrequency;
    private Integer filteredRange;
    private AudioBalance audioBalance;
    private Integer duration;
    private Date startedAt;
    private Integer volume;
    private Integer satisfactionPoint;
}
