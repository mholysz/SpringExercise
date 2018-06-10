package pl.springExercises.users;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.springExercises.users.entity.GroupEntity;
import pl.springExercises.users.entity.UserEntity;
import pl.springExercises.users.service.UserAuthService;
import pl.springExercises.users.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserAuthServiceTest {

    private UserAuthService userAuthService;

    @Mock
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        userAuthService = new UserAuthService(userService);
    }

    private UserEntity prepareAdmin(){
        UserEntity mockedUser = prepareUserWithoutGroup();
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setId(1L);
        groupEntity.setName("admin");
        mockedUser.setGroupEntity(groupEntity);

        return mockedUser;
    }


    private UserEntity prepareUser() {
        UserEntity mockedUser =  prepareUserWithoutGroup();
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setId(2L);
        groupEntity.setName("user");
        mockedUser.setGroupEntity(groupEntity);

        return mockedUser;
    }

    private UserEntity prepareUserWithoutGroup() {
        UserEntity mockedUser = new UserEntity();
        mockedUser.setId(1L);
        mockedUser.setName("Abelard");
        mockedUser.setSurname("Kurzanoga");
        mockedUser.setEmail("kurazanostra@abelard.com");
        mockedUser.setPassword("5828!");
        mockedUser.setGroupEntity(null);

        return mockedUser;
    }

    @Test
    public void shouldGetUser() {
        UserEntity mockedUser = prepareAdmin();

        when(userService.getUserByEmail(eq("kurazanostra@abelard.com"))).thenReturn(mockedUser);

        UserDetails result = userAuthService.loadUserByUsername("kurazanostra@abelard.com");

        Assert.assertEquals("kurazanostra@abelard.com", result.getUsername());
        Assert.assertEquals("5828!", result.getPassword());
    }


    @Test
    public void shouldReturnUserWithAdminRole() {
        UserEntity mockedUser = prepareAdmin();
        when(userService.getUserByEmail(any())).thenReturn(mockedUser);

        UserDetails result = userAuthService.loadUserByUsername("j.kowalski@wp.pl");

        Assert.assertEquals(1, result.getAuthorities().size());
        Assert.assertEquals("ROLE_ADMIN",result.getAuthorities().stream().findFirst().get().getAuthority());
    }

    @Test
    public void shouldReturnUSerWithoutAdminRole() {
        UserEntity mockedUser = prepareUser();

        when(userService.getUserByEmail(any())).thenReturn(mockedUser);

        UserDetails result = userAuthService.loadUserByUsername("j.kowalski@wp.pl");

        Assert.assertEquals(1, result.getAuthorities().size());
        Assert.assertEquals("ROLE_USER",result.getAuthorities().stream().findFirst().get().getAuthority());
    }


    @Test(expected = UsernameNotFoundException.class )
    public void shouldThrowExceptionWhenUserDoesnotExist() {
        UserDetails result = userAuthService.loadUserByUsername("j.kowalski@wp.pl");

    }


}
