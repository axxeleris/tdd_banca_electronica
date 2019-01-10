package com.pe.tdd.service;

import com.pe.tdd.exception.BlockedUserException;
import com.pe.tdd.exception.InvalidUserAndPasswordException;
import com.pe.tdd.domain.User;
import com.pe.tdd.repository.UserRepository;

public class LoginService {

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String userName, String password) {
        User foundUser = userRepository.findUser(userName);
        if ("password".equals(password)) {
            checkBlockedUser(foundUser);
            return foundUser;
        } else {
            throw new InvalidUserAndPasswordException();
        }
    }


    private void checkBlockedUser(User user) {
        if (user.getBlocked()) {
            throw new BlockedUserException();
        }
    }

}
