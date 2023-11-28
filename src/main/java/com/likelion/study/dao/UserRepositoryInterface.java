package com.likelion.study.dao;

import com.likelion.study.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryInterface {
    public User save(User user);
}
