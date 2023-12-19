package io.joo.plus.post.dto;

import io.joo.plus.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListResponseDto {
    private Long postId;
    private String title;
    private LocalDateTime createdAt;

    public PostListResponseDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.createdAt = post.getCreatedAt();
    }
}
