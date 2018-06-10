package pl.springExercises.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.springExercises.users.dto.NewUserDto;
import pl.springExercises.users.dto.UserDto;
import pl.springExercises.users.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping //PostMapping - zapytanie HTTP jako POST
    public void createUser(@RequestBody NewUserDto newUserDto) {
        //@RequestBody - wyciąga parametry z zapytania POST
        userService.createUser(newUserDto);
    }

    @GetMapping
    public List<UserDto> getAllUsers(){
        return userService.getAllUsers();
    }


    @PutMapping(value = "/{userId}")
    public void updateUser(@RequestBody NewUserDto newUserDto, @PathVariable Long userId){
       newUserDto.setId(userId);
       userService.updateUser(newUserDto);
    }

    @DeleteMapping(value = "/{userId}")
    public void rmUser(@PathVariable Long userId){
        userService.rmUser(userId);
    }


    @PutMapping(value = "/{userId}/group/{groupId}")
    public void attachGroup(@PathVariable Long userId, @PathVariable Long groupId){
       userService.attachGroup(userId, groupId);
    }
}
