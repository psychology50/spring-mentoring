package com.likelion.study.dto;

import com.likelion.study.domain.Post;
import lombok.Builder;

@Builder
public record PostRes(
        Long id,
        String title,
        String content,
        String imageUrl
) {
    public static PostRes from(Post post) {
        return PostRes.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .build();
    }
}
