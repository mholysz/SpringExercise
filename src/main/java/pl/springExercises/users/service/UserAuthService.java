package pl.springExercises.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.springExercises.users.entity.GroupEntity;
import pl.springExercises.users.entity.UserEntity;

import java.util.Arrays;

@Service
public class UserAuthService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserAuthService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User userDetails = new User("user", "pass", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        UserEntity user = userService.getUserByEmail(username);
        User userDetails;
        try {
            userDetails = new User(user.getEmail(), user.getPassword(), Arrays.asList(generateRole(user.getGroupEntity())));
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not Found");
        }
        return userDetails;
    }

    private SimpleGrantedAuthority generateRole(GroupEntity groupEntity){
        if(groupEntity.getId() == 1) {
            return new SimpleGrantedAuthority("ROLE_ADMIN");
        }
        return new SimpleGrantedAuthority("ROLE_USER");
    }
}
