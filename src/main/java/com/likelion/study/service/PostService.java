package com.likelion.study.service;

import com.likelion.study.dao.PostRepository;
import com.likelion.study.dao.UserRepository;
import com.likelion.study.domain.Post;
import com.likelion.study.domain.User;
import com.likelion.study.dto.PostRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public PostRes savePost(Post post) {
        User user = userRepository.findById(2L) // 로그인 정보가 없으니, 임의로 호출
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        post.setUser(user);

        return PostRes.from(postRepository.save(post));
    }
}
