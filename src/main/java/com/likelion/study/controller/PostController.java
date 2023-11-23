package com.likelion.study.controller;

import com.likelion.study.dto.PostReq;
import com.likelion.study.dto.PostRes;
import com.likelion.study.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@Slf4j
public class PostController {
    private final PostService postService;

    @PostMapping("")
    public ResponseEntity<?> registerPost(@RequestBody PostReq postReq) { // PostReq 정의
        log.info("Post register request: {}", postReq);
        PostRes postRes = postService.savePost(postReq.toEntity()); // toEntity() 정의, PostRes 받기

        return ResponseEntity.ok(postRes);
    }
}
