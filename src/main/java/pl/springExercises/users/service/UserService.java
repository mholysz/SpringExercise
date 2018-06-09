package pl.springExercises.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.springExercises.users.dto.UserDto;
import pl.springExercises.users.entity.GroupEntity;
import pl.springExercises.users.entity.UserEntity;
import pl.springExercises.users.exception.GroupDoesntExistException;
import pl.springExercises.users.exception.UserDoesntExistException;
import pl.springExercises.users.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
    Różnica pomiędzy
    @Component jest tym samym co @Service.
 */

@Service
public class UserService {

    private final UserRepository userRepository;
    private final GroupService groupService;

    /*
    Autowired wpisywany przy polu korzysta z refleksji, która jest wolniejsza.
    Lepiej wrzucić @Autowired na konstruktor wtedy wszystkie parametry sobie tworzymy szybciej
     */

    @Autowired
    public UserService(UserRepository userRepository, GroupService groupService) {
        this.userRepository = userRepository;
        this.groupService = groupService;
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
//        List<UserDto> result2 = all.stream().map(this::convertUserToDTO).collect(Collectors.toList());
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
        if (userEntity.getGroupEntity() != null) {
            user.setGroup(userEntity.getGroupEntity().getName());
        }
        return user;
    }


    public UserDto updateUser(UserDto userDto) {
//        UserEntity user = convertUserToEntity(userDto);
//        UserEntity savedUser = userRepository.save(user); //wrzucenie usera do bazy, wraz ze zwrotką encji z bazy.
//        UserDto resultUser = convertUserToDTO(savedUser);
// return result;

        return createUser(userDto);
    }

    public void rmUser(Long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException e) {
            throw new UserDoesntExistException();
        }
    }


    public void attachGroup(Long userId, Long groupId) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId); //opakowanie dla danych w bazie,
        if (optionalUser.isPresent()) {
            GroupEntity groupEntity = groupService.getGroupById(groupId);
                UserEntity user = optionalUser.get();
                user.setGroupEntity(groupEntity);
                userRepository.save(user);
        } else {
            throw new UserDoesntExistException();
        }

    }

}