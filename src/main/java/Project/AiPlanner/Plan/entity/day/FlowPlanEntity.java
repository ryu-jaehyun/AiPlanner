package Project.AiPlanner.Plan.entity.day;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "dayPlanFlow")
public class FlowPlanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dayPlanFixId")
    private int dayPlanFixId;

    @Column(name = "userId", nullable = false,length = 20)
    private String userId;

    @Column(name = "planName", nullable = false,length = 30)
    private String planName;

    @Column(name = "planType",nullable = false, length = 20)
    private String planType;

    @Column(name = "start",nullable = false)
    private LocalDateTime start;

    @Column(name = "end",nullable = false)
    private LocalDateTime end;

    // 나머지 필드 및 메소드 추가
}
