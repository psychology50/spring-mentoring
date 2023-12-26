package com.likelion.study.dao;

import com.likelion.study.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryInterface {
    User save(User user);
    void delete(User user);
}
