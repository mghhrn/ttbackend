package ir.mghhrn.ttbackend.service;

import ir.mghhrn.ttbackend.dto.TherapySessionDto;

import java.util.List;

public interface TherapySessionService {

    void saveAll(List<TherapySessionDto> therapySessionDtoList);
}
