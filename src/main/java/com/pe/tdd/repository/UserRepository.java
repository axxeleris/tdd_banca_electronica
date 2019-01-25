package com.pe.tdd.repository;

import com.pe.tdd.domain.User;

public interface UserRepository {
    User findUser(String user);
}
