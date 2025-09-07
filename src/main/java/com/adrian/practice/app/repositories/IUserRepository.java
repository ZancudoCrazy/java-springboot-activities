package com.adrian.practice.app.repositories;

import java.util.Optional;

import com.adrian.practice.app.entities.User;

public interface IUserRepository {
    Optional<User> findByUsername(String username);
}
