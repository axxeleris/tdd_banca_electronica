package com.pe.tdd.repository.impl;

import com.pe.tdd.domain.User;
import com.pe.tdd.repository.UserRepository;

public class FakeUserRepository implements UserRepository {
    @Override
    public User findUser(String user) {
        return new User("user", "secret", false);
    }
}
