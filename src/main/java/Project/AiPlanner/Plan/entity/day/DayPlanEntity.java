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
@Table(name = "dayPlan")
public class DayPlanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Integer planId;

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

    @Column(name = "plan",nullable = false, length = 10)
    private String plan;

    @Column(name="day_of_week",nullable = true,length = 20)
    private String dayOfWeek;

    @Column(name="color" ,nullable = false ,length = 30)
    private String color;
    // 나머지 필드 및 메소드 추가
    @Column(name = "success",nullable = false)
    private int success =0;
}


