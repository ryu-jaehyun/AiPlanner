package Project.AiPlanner.User.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "user_plan_type")
public class UserPlanTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id", nullable = false,length = 20)
    private String userId;

    @Column(name = "color", nullable = false,length = 30)
    private String color;

    @Column(name = "plan_type",nullable = false, length = 20)
    private String planType;






}
