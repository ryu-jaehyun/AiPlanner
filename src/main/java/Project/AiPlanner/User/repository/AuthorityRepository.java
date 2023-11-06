package Project.AiPlanner.User.repository;

import Project.AiPlanner.User.entity.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, String> {
}