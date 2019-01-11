package com.pe.tdd;

import com.pe.tdd.domain.User;
import com.pe.tdd.exception.BlockedUserException;
import com.pe.tdd.exception.InvalidUserAndPasswordException;
import com.pe.tdd.repository.UserRepository;
import com.pe.tdd.service.LoginService;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginServiceTest {

    @Test
    public void shouldLoginWithUserAndPassword() {

        User expectedUser = new User("user", "password", false);

        UserRepository mockUserRepository = mock(UserRepository.class);
        LoginService loginService = new LoginService(mockUserRepository);

        when(mockUserRepository.findUser(Mockito.anyString()))
                .thenReturn(expectedUser);

        User loggedUser = loginService.login("user", "password");

        assertEquals(loggedUser, expectedUser);
    }

    @Test(expected = InvalidUserAndPasswordException.class)
    public void throwInvalidUserAndPasswordExceptionOnInvalidPassword() {
        User userWithWrongPassword = new User("user", "wrong password", false);

        UserRepository mockUserRepository = mock(UserRepository.class);

        when(mockUserRepository.findUser(Mockito.anyString()))
                .thenReturn(userWithWrongPassword);

        LoginService loginService = new LoginService(mockUserRepository);
        loginService.login("user", "wrong_password");
    }

    @Test(expected = BlockedUserException.class)
    public void throwBlockedUserExceptionOnBlockedUser() {
        User blockedUser = new User("user", "password", true);

        UserRepository mockUserRepository = mock(UserRepository.class);

        when(mockUserRepository.findUser(Mockito.anyString()))
                .thenReturn(blockedUser);

        LoginService loginService = new LoginService(mockUserRepository);
        loginService.login("blocked_user", "password");
    }

}
