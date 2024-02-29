package Project.AiPlanner;

import Project.AiPlanner.User.entity.UserEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
public class AiPlannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiPlannerApplication.class, args);

        Class clazz = UserEntity.class;
        System.out.println("name : " + clazz.getName());
    }
}