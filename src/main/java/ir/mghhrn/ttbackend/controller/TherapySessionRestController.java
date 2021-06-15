package ir.mghhrn.ttbackend.controller;

import ir.mghhrn.ttbackend.dto.TherapySessionDto;
import ir.mghhrn.ttbackend.service.TherapySessionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/therapy-session")
public class TherapySessionRestController {

    private final TherapySessionService therapySessionService;

    @PostMapping
    public ResponseEntity<Void> saveTherapySessions(@RequestBody List<TherapySessionDto> therapySessionDtoList) {
        therapySessionService.saveAll(therapySessionDtoList);
        return ResponseEntity.ok().build();
    }
}
