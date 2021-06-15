package ir.mghhrn.ttbackend.entity;

import ir.mghhrn.ttbackend.enums.AudioBalance;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(schema = "public", name = "therapy_session")
public class TherapySession {

    @Id
    @SequenceGenerator(name = "roleSeq", sequenceName="role_sequence", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "selected_frequency")
    private Double selectedFrequency;

    @Column(name = "filtered_range")
    private Integer filteredRange;

    @Enumerated(EnumType.STRING)
    @Column(name = "audio_balance")
    private AudioBalance audioBalance;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "started_at")
    private Date startedAt;

    @Column(name = "volume")
    private Integer volume;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private User user;

    @Column(name = "satisfaction_point")
    private Integer satisfactionPoint;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;
}
