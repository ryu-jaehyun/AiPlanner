package Project.AiPlanner.Plan.entity.month;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    @Column(name = "plan_id")
    private int planId;

    @Column(name = "user_id", nullable = false,length = 20)
    private String userId;

    @Column(name = "plan_name", nullable = false,length = 30)
    private String planName;

    @Column(name = "plan_type",nullable = false, length = 20)
    private String planType;

    @Column(name = "start",nullable = false)
    private LocalDate start;

    @Column(name = "end",nullable = false)
    private LocalDate end;

    @Column(name="day_of_week",nullable = true,length = 20)
    private String dayOfWeek;

    @Column(name="color" ,nullable = false ,length = 30)
    private String color;

    @Column(name = "success",nullable = false)
    private int success=0 ;
    // 나머지 필드 및 메소드 추가

}