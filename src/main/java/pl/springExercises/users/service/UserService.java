package pl.springExercises.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.springExercises.users.dto.UserDto;
import pl.springExercises.users.entity.UserEntity;
import pl.springExercises.users.repository.UserRepository;

/*
    Różnica pomiędzy
    @Component jest tym samym co @Service.
 */

@Service
public class UserService {

    private final UserRepository userRepository;

    /*
    Autowired wpisywany przy polu korzysta z refleksji, która jest wolniejsza.
    Lepiej wrzucić @Autowired na konstruktor wtedy wszystkie parametry sobie tworzymy szybciej
     */

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto createUser (UserDto userDto){
        UserEntity user = new UserEntity();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());

        UserEntity savedUser = userRepository.save(user); //wrzucenie usera do bazy, wraz ze zwrotką encji z bazy.

        UserDto savedUserDto = new UserDto();
        savedUserDto.setEmail(savedUser.getEmail());
        savedUserDto.setId(savedUser.getId());
        savedUserDto.setName(savedUser.getName());
        savedUserDto.setSurname(savedUser.getSurname());

        return savedUserDto;
    }
}
