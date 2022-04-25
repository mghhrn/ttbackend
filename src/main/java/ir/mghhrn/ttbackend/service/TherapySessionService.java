package ir.mghhrn.ttbackend.service;

import ir.mghhrn.ttbackend.dto.TherapySessionDto;
import ir.mghhrn.ttbackend.entity.TherapySession;

import java.util.List;

public interface TherapySessionService {

    List<TherapySession> findAll();
    void saveAll(List<TherapySessionDto> therapySessionDtoList);
}
