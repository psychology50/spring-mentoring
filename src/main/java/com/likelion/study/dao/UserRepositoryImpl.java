package com.likelion.study.dao;

import com.likelion.study.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryInterface {
    private final EntityManager entityManager;

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            entityManager.persist(user);
            return user;
        }
        else {
            return entityManager.merge(user);
        }
    }
}
