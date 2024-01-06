package com.likelion.study.service;

import com.likelion.study.dao.PostRepository;
import com.likelion.study.dao.UserRepository;
import com.likelion.study.dao.UserRepositoryInterface;
import com.likelion.study.domain.Post;
import com.likelion.study.domain.User;
import com.likelion.study.dto.UserReq;
import com.likelion.study.dto.UserRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Transactional
    public UserRes save(UserReq request) {
        User user = request.toEntity(); // UserReq -> User
        userRepository.save(user); // User 저장 : transaction 안에서 실행
//        userRepository.saveAll();
        // user.getId()
        log.info("유저가 잘 등록되었습니다.: {}번 user", user);

        Post post = Post.of("환영", "합니다.", "");
        post.setUser(user);

        log.info("포스트가 잘 등록되었습니다.: {}번 post", post);
//        postRepository.save(post);

        return UserRes.from(user);
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
