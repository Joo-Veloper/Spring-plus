package io.joo.plus.post.dto;

import lombok.Getter;

@Getter
public class PostCreateResponseDto {
    private Long postId;

    public PostCreateResponseDto(Long postId) {
        this.postId=postId;
    }
}
