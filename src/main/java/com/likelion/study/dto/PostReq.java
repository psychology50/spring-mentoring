package com.likelion.study.dto;

import com.likelion.study.domain.Post;

public record PostReq(
        String title,
        String content,
        String imageUrl
) {
    public Post toEntity() {
        return Post.of(title, content, imageUrl);
    }
}
