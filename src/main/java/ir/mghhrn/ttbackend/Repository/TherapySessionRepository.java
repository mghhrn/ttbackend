package ir.mghhrn.ttbackend.Repository;

import ir.mghhrn.ttbackend.entity.TherapySession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TherapySessionRepository extends JpaRepository<TherapySession, Long> {
}
