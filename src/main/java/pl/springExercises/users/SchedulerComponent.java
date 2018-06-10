package pl.springExercises.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.springExercises.users.dto.UserDto;
import pl.springExercises.users.service.UserService;

@Component
public class SchedulerComponent {


    private final UserService userService;

    @Autowired
    public SchedulerComponent(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(fixedRateString = "5000")
    public void fillMissingGroups() {
        System.out.println("Scheduled");
        userService.getAllUsers()
                .stream()
                .filter(u -> u.getGroup() == null)
                .forEach(this::addGroup);
    }

    private void addGroup(UserDto user) {
        System.out.println("Attaching user: " + user);
        userService.attachGroup(user.getId(), 3L);
    }

}
