package io.joo.plus.postlike.controller;

import io.joo.plus.postlike.dto.CommonLikeResponseDto;
import io.joo.plus.postlike.entity.PostLike;
import io.joo.plus.postlike.service.PostLikeService;
import io.joo.plus.security.MemberDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostLikeController {
    private final PostLikeService postLikeService;

    //PostLike
    @PostMapping("/{postId}/like")
    public ResponseEntity<CommonLikeResponseDto> LikePost(@PathVariable Long postId, @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        if(memberDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CommonLikeResponseDto("로그인한 사용자만 이용가능합니다.", HttpStatus.UNAUTHORIZED.value(), null));
        }else {
            return postLikeService.postLike(postId, memberDetails);
        }
    }
}
