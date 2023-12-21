package io.joo.plus.postlike.service;

import io.joo.plus.member.entity.Member;
import io.joo.plus.post.entity.Post;
import io.joo.plus.post.repostitory.PostRepository;
import io.joo.plus.postlike.dto.CommonLikeResponseDto;
import io.joo.plus.postlike.entity.PostLike;
import io.joo.plus.postlike.repository.PostLikeRepository;
import io.joo.plus.security.MemberDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    public ResponseEntity<CommonLikeResponseDto> postLike(Long postId, MemberDetailsImpl memberDetails) {
        try {
            Post post = findPostById(postId);
            Member member = memberDetails.getMember();

            PostLike postLike = postLikeRepository.findByPostIdAndMemberId(post.getId(), member.getId());
            boolean isLiked = false;

            if (postLike == null) {
                postLike = new PostLike(post, member, true);
                postLikeRepository.save(postLike);
                isLiked = true;
            } else {
                if (!postLike.getMember().getUserId().equals(member.getUserId())) {
                    throw new IllegalArgumentException("다른 사용자가 이미 좋아요를 눌렀습니다.");
                }

                postLike.setIsLike(!postLike.getIsLike());
                postLikeRepository.save(postLike);
                isLiked = postLike.getIsLike();
            }

            Long likes = (long) postLikeRepository.countByPostIdAndIsLikeTrue(post.getId());
            String message = isLiked ? "좋아요 성공" : "좋아요 해제";

            return ResponseEntity.ok().body(new CommonLikeResponseDto(message, HttpStatus.OK.value(), likes));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new CommonLikeResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
    }

    private Post findPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 postId입니다."));
    }
}