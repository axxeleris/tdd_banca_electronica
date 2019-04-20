package com.pe.tdd;

import com.pe.tdd.domain.User;
import com.pe.tdd.exception.InvalidUserAndPasswordException;
import com.pe.tdd.repository.UserRepository;
import com.pe.tdd.repository.impl.UserRepositoryImpl;
import com.pe.tdd.service.impl.LoginServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginServiceTest {

    UserRepository userRepository;
    LoginServiceImpl loginService;

    User expectedUser = new User("user", "secret", false);
    User userWithWrongPassword = new User("user", "wrong password", false);

    @Before
    public void setUp() {
        userRepository = mock(UserRepositoryImpl.class);
        loginService = new LoginServiceImpl(userRepository);
    }

    @Test
    public void shouldLoginWithUserAndPassword() {

        // Stub
        when(userRepository.findUser(Mockito.anyString()))
                .thenReturn(expectedUser);

        User loggedUser = loginService.login("user", "secret");

        assertEquals(loggedUser, expectedUser);
    }

    @Test(expected = InvalidUserAndPasswordException.class)
    public void throwInvalidUserAndPasswordExceptionOnInvalidPassword() {

        // Stub
        when(userRepository.findUser(Mockito.anyString()))
                .thenReturn(userWithWrongPassword);

        LoginServiceImpl loginService = new LoginServiceImpl(userRepository);
        loginService.login("user", "wrong_password");
    }


}
