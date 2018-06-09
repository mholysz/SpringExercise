package pl.springExercises.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.springExercises.users.dto.UserDto;
import pl.springExercises.users.service.UserService;

import java.util.List;

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

    @GetMapping
    public List<UserDto> getAllUsers(){
        return userService.getAllUsers();
    }


    @PutMapping(value = "/{userId}")
    public void updateUser(@RequestBody UserDto userDto, @PathVariable Long userId){
       userDto.setId(userId);
       userService.updateUser(userDto);
    }
}
