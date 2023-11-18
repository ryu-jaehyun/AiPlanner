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
    @Column(name = "dayPlanFlowId")
    private int dayPlanFlowId;

    @Column(name = "user_id", nullable = false,length = 20)
    private String userId;

    @Column(name = "plan_name", nullable = false,length = 30)
    private String planName;

    @Column(name = "plan_type",nullable = false, length = 20)
    private String planType;

    @Column(name = "start",nullable = false)
    private LocalDateTime start;

    @Column(name = "end",nullable = false)
    private LocalDateTime end;

    // 나머지 필드 및 메소드 추가
}
