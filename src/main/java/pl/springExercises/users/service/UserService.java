package pl.springExercises.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.springExercises.users.dto.UserDto;
import pl.springExercises.users.entity.UserEntity;
import pl.springExercises.users.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public UserDto createUser(UserDto userDto) {
        UserEntity user = convertUserToEntity(userDto);
        UserEntity savedUser = userRepository.save(user); //wrzucenie usera do bazy, wraz ze zwrotką encji z bazy.
        UserDto savedUserDto = convertUserToDTO(savedUser);
        return savedUserDto;
    }

    public List<UserDto> getAllUsers() {
        List<UserEntity> all = userRepository.findAll();
        List<UserDto> result = new ArrayList<>();

        for (UserEntity user : all) {
            result.add(convertUserToDTO(user));
        }

        List<UserDto> result2 = all.stream().map(this::convertUserToDTO).collect(Collectors.toList());


        return result;
    }

    private UserEntity convertUserToEntity(UserDto userDto) { //z wyswietlenia do bazy
        UserEntity user = new UserEntity();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        return user;
    }

    private UserDto convertUserToDTO(UserEntity userEntity) { //z bazy do wyswietlenia
        UserDto user = new UserDto();
        user.setId(userEntity.getId());

        user.setName(userEntity.getName());
        user.setSurname(userEntity.getSurname());
        user.setEmail(userEntity.getEmail());
        return user;
    }


    public UserDto updateUser(UserDto userDto) {
//        UserEntity user = convertUserToEntity(userDto);
//        UserEntity savedUser = userRepository.save(user); //wrzucenie usera do bazy, wraz ze zwrotką encji z bazy.
//        UserDto resultUser = convertUserToDTO(savedUser);
// return result;

        return createUser(userDto);
    }


}
