package pl.springExercises.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.springExercises.users.dto.UserDto;
import pl.springExercises.users.service.UserService;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping //PostMapping - zapytanie HTTP jako POST
    public void createUser(@RequestBody UserDto userDto) {
        //@RequestBody - wyciąga parametry z zapytania POST
        userService.createUser(userDto);
    }

}
