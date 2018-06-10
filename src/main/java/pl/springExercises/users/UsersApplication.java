package pl.springExercises.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.springExercises.users.dto.GroupDto;
import pl.springExercises.users.dto.NewUserDto;
import pl.springExercises.users.dto.UserDto;
import pl.springExercises.users.service.GroupService;
import pl.springExercises.users.service.UserService;

@SpringBootApplication
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
		GroupDto groupDto = new GroupDto();
		groupDto.setName("admin");
		groupService.createGroup(groupDto);

		NewUserDto newUserDto = new NewUserDto();
		newUserDto.setEmail("admin@wp.pl");
		newUserDto.setPassword("admin@wp.pl");
		userService.createUser(newUserDto);

		userService.attachGroup(2L, 1L);
	}

}
