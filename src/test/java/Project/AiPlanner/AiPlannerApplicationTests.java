package Project.AiPlanner;

import Project.AiPlanner.User.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootTest
@EnableCaching
class AiPlannerApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void abc() {

		Class clazz = UserEntity.class;
		System.out.println("name" + clazz.getName());
	}

}
