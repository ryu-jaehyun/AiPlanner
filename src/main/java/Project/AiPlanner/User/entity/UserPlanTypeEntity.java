package Project.AiPlanner.User.entity;


import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "user_id", nullable = false, length = 20)
    private String userId;

    @Column(name = "color", nullable = false, length = 30)
    private String color;

    @Column(name = "plan_type", nullable = false, length = 20)
    private String planType;


    public UserPlanTypeEntity(String userId, String color, String planType) {
        this.color = color;
        this.planType = planType;
        this.userId = userId;
    }
}
