package com.pe.tdd.service;

import com.pe.tdd.domain.User;

public interface LoginService {
    User login(String userName, String password);
}
