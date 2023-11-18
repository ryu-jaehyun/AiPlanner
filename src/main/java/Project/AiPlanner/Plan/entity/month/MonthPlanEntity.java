package Project.AiPlanner.Plan.entity.month;

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
@Table(name = "monthPlan")
public class MonthPlanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "monthPlanId")
    private int monthPlanId;

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