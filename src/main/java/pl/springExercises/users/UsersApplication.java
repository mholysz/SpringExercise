package pl.springExercises.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.springExercises.users.dto.GroupDto;
import pl.springExercises.users.dto.NewUserDto;
import pl.springExercises.users.dto.UserDto;
import pl.springExercises.users.service.GroupService;
import pl.springExercises.users.service.UserService;

@SpringBootApplication
@EnableScheduling
public class UsersApplication implements CommandLineRunner{

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserService userService;


	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		GroupDto group = new GroupDto();
		group.setName("admin");
		groupService.createGroup(group);

		NewUserDto user = new NewUserDto();
		user.setEmail("admin@wp.pl");
		user.setPassword("test123");
		userService.createUser(user);

		group = new GroupDto();
		group.setName("users");
		groupService.createGroup(group);

		userService.attachGroup(2L, 1L);
	}
}
