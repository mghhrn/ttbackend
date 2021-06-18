package ir.mghhrn.ttbackend.service.impl;

import ir.mghhrn.ttbackend.repository.TherapySessionRepository;
import ir.mghhrn.ttbackend.dto.TherapySessionDto;
import ir.mghhrn.ttbackend.entity.TherapySession;
import ir.mghhrn.ttbackend.service.TherapySessionService;
import ir.mghhrn.ttbackend.util.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TherapySessionServiceImpl implements TherapySessionService {

    private final TherapySessionRepository repository;

    @Override
    @Transactional
    public void saveAll(List<TherapySessionDto> therapySessionDtoList) {
        repository.saveAll(
                therapySessionDtoList.stream()
                    .map(this::convertDtoToEntity)
                    .collect(Collectors.toList())
        );
    }

    private TherapySession convertDtoToEntity(TherapySessionDto dto) {
        TherapySession session = new TherapySession();
        session.setSelectedFrequency(dto.getSelectedFrequency());
        session.setFilteredRange(dto.getFilteredRange());
        session.setAudioBalance(dto.getAudioBalance());
        session.setDuration(dto.getDuration());
        session.setStartedAt(dto.getStartedAt());
        session.setVolume(dto.getVolume());
        session.setSatisfactionPoint(dto.getSatisfactionPoint());
        session.setUserId(SecurityUtil.getCurrentUserId());
        return session;
    }
}
