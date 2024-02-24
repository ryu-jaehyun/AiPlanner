package Project.AiPlanner;

import Project.AiPlanner.User.entity.UserEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AiPlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiPlannerApplication.class, args);

		Class clazz = UserEntity.class;
		System.out.println("name : " + clazz.getName());
	}
}