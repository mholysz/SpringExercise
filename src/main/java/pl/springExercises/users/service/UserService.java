package pl.springExercises.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.springExercises.users.dto.UserDto;
import pl.springExercises.users.dto.NewUserDto;
import pl.springExercises.users.entity.GroupEntity;
import pl.springExercises.users.entity.UserEntity;
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
    private final PasswordEncryptionService passwordEncryptionService;

    /*
    Autowired wpisywany przy polu korzysta z refleksji, która jest wolniejsza.
    Lepiej wrzucić @Autowired na konstruktor wtedy wszystkie parametry sobie tworzymy szybciej
     */

    @Autowired
    public UserService(UserRepository userRepository, GroupService groupService, PasswordEncryptionService passwordEncryptionService) {
        this.userRepository = userRepository;
        this.groupService = groupService;
        this.passwordEncryptionService = passwordEncryptionService;
    }

    public UserDto createUser(NewUserDto newUserDto) {
        UserEntity user = convertNewUserToEntity(newUserDto);
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
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDto.getId());
        userEntity.setName(userDto.getName());
        userEntity.setSurname(userDto.getSurname());
        userEntity.setEmail(userDto.getEmail());
        return userEntity;
    }

    private UserEntity convertNewUserToEntity(NewUserDto newUserDto) { //z wyswietlenia do bazy
        UserEntity userEntity = new UserEntity();
        userEntity.setId(newUserDto.getId());
        userEntity.setName(newUserDto.getName());
        userEntity.setSurname(newUserDto.getSurname());
        userEntity.setEmail(newUserDto.getEmail());
        userEntity.setPassword(passwordEncryptionService.encodePassword(newUserDto.getPassword()));
        return userEntity;
    }

    private UserDto convertUserToDTO(UserEntity userEntity) { //z bazy do wyswietlenia
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setName(userEntity.getName());
        userDto.setSurname(userEntity.getSurname());
        userDto.setEmail(userEntity.getEmail());
        if (userEntity.getGroupEntity() != null) {
            userDto.setGroup(userEntity.getGroupEntity().getName());
        }
        return userDto;
    }


    public UserDto updateUser(NewUserDto newUserDto) {
        UserEntity user = convertNewUserToEntity(newUserDto);
        UserEntity savedUser = userRepository.save(user); //wrzucenie usera do bazy, wraz ze zwrotką encji z bazy.
        UserDto resultUser = convertUserToDTO(savedUser);
        return resultUser;
//        return createUser(userDto);
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

    public UserEntity getUserByEmail(String email){
        return userRepository.findOneByEmail(email);
    }


}