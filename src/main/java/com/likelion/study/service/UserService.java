package com.likelion.study.service;

import com.likelion.study.dao.PostRepository;
import com.likelion.study.dao.UserRepository;
import com.likelion.study.dao.UserRepositoryInterface;
import com.likelion.study.domain.Post;
import com.likelion.study.domain.User;
import com.likelion.study.dto.Jwt;
import com.likelion.study.dto.UserReq;
import com.likelion.study.dto.UserRes;
import com.likelion.study.security.jwt.AccessJwtUtil;
import com.likelion.study.security.jwt.JwtUtil;
import com.likelion.study.security.jwt.RefreshJwtUtil;
import com.likelion.study.security.jwt.RefreshQualifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final UserRepositoryInterface userRepositoryInterface;

    private final JwtUtil accessJwtUtil;
//    @Qualifier("refreshJwtUtil")
    @RefreshQualifier
    private final JwtUtil refreshJwtUtil;

//    public UserService(
//            UserRepository userRepository,
//            PostRepository postRepository,
//            UserRepositoryInterface userRepositoryInterface,
//            JwtUtil accessJwtUtil,
//            @Qualifier("refreshJwtUtil") JwtUtil refreshJwtUtil
//    ) {
//        this.userRepository = userRepository;
//        this.postRepository = postRepository;
//        this.userRepositoryInterface = userRepositoryInterface;
//        this.accessJwtUtil = accessJwtUtil;
//        this.refreshJwtUtil = refreshJwtUtil;
//    }

    // 가능한데, 별로 권장 안 함
//    private final AccessJwtUtil accessJwtUtil;
//    private final RefreshJwtUtil refreshJwtUtil;

    @Transactional
    public Jwt save(UserReq request) {
        // (가정) 회원가입 시나리오

        // 1. 유저 정보 저장
        User user = request.toEntity(); // UserReq -> User
        userRepository.save(user); // User 저장 : transaction 안에서 실행

        // 2. AT, RT 발급
        String accessToken = accessJwtUtil.generateToken(user.getId());
        String refreshToken = refreshJwtUtil.generateToken(user.getId());

        return Jwt.of(accessToken, refreshToken);
    }

    @Transactional(readOnly = true)
    public UserRes findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 없습니다.")
        );

        // 있으면 `유저`에 instance 담고, 없으면 에러
//        User 유저 = user.orElseThrow(() -> new IllegalArgumentException("성윤님을 패러"));

        Optional<User> tmp = userRepository.findById(id);
        if (tmp.isEmpty())
            throw new IllegalArgumentException("해당 유저가 없습니다.");

        return UserRes.from(user);
    }

    @Transactional(readOnly = true)
    public List<UserRes> findAll() {
        List<User> users = userRepository.findAll();

//        List<UserRes> userResquests = new ArrayList<>();
//        for (User user : users) {
//            UserRes userRes = UserRes.from(user);
//            userResquests.add(userRes);
//        }
//
//        return userResquests;
        return users.stream().map(UserRes::from).toList();
    }
}
