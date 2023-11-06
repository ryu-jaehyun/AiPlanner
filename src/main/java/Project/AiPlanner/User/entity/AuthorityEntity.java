package Project.AiPlanner.User.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "authority")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityEntity {

    @Id
    @Column(name = "authority_name", length = 50)
    private String authorityName;
}