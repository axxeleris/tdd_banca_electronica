package com.pe.tdd;

import com.pe.tdd.domain.User;
import com.pe.tdd.exception.BlockedUserException;
import com.pe.tdd.exception.InvalidUserAndPasswordException;
import com.pe.tdd.repository.UserRepository;
import com.pe.tdd.repository.impl.FakeUserRepository;
import com.pe.tdd.repository.impl.UserRepositoryImpl;
import com.pe.tdd.service.LoginService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginServiceTest {

    UserRepository userRepository;
    LoginService loginService;

    User expectedUser = new User("user", "password", false);
    User userWithWrongPassword = new User("user", "wrong password", false);

    @Before
    public void setUp() throws Exception {
        userRepository = mock(UserRepositoryImpl.class);
        loginService = new LoginService(userRepository);
    }

    @Test
    public void shouldLoginWithUserAndPassword() {
        // Stub
        when(userRepository.findUser(Mockito.anyString()))
                .thenReturn(expectedUser);

        User loggedUser = loginService.login("user", "password");

        assertEquals(loggedUser, expectedUser);
    }

    @Test(expected = InvalidUserAndPasswordException.class)
    public void throwInvalidUserAndPasswordExceptionOnInvalidPassword() {


        // Stub
        when(userRepository.findUser(Mockito.anyString()))
                .thenReturn(userWithWrongPassword);

        LoginService loginService = new LoginService(userRepository);
        loginService.login("user", "wrong_password");
    }

    @Test(expected = BlockedUserException.class)
    public void throwBlockedUserExceptionOnBlockedUser() {
        User blockedUser = new User("user", "password", true);

        // Stub
        when(userRepository.findUser(Mockito.anyString()))
                .thenReturn(blockedUser);

        LoginService loginService = new LoginService(userRepository);
        loginService.login("blocked_user", "password");
    }

    @Test
    public void shouldLoginWithUserAndPasswordFake() {
        // Fake
        userRepository = new FakeUserRepository();
        loginService = new LoginService(userRepository);

        loginService.login("user", "password");
    }

    @Test
    public void shouldLoginWithUserAndPasswordMock() {
        // Fake
        LoginService mockLoginService = mock(LoginService.class);
        mockLoginService.login("user", "password");
    }


}
