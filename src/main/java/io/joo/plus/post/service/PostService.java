package io.joo.plus.post.service;

import io.joo.plus.member.dto.MemberRequestDto;
import io.joo.plus.member.entity.Member;
import io.joo.plus.post.dto.PostCreateResponseDto;
import io.joo.plus.post.dto.PostRequestDto;
import io.joo.plus.post.dto.PostResponseDto;
import io.joo.plus.post.entity.Post;
import io.joo.plus.post.repostitory.PostRepository;
import io.joo.plus.security.MemberDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostCreateResponseDto createPost(PostRequestDto requestDto, Member member) {
        Post post = postRepository.save(new Post(requestDto, member));
        return new PostCreateResponseDto(post.getId());
    }

    public PostResponseDto getPost(Long postId) {
        return new PostResponseDto(findPostById(postId));
    }


    @Transactional
    public String updatePost(Long postId, PostRequestDto requestDto, MemberDetailsImpl memberDetails) {
        Member member = memberDetails.getMember();

        Post post = verifyMember(member, postId);

        post.update(requestDto);

        return "수정 성공";
    }

    public String deletePost(Long postId, MemberDetailsImpl memberDetails) {
        Member member = memberDetails.getMember();
        Post post = verifyMember(member, postId);
        postRepository.delete(post);
        return "삭제 성공";
    }


    private Post verifyMember(Member member, Long postId) {
        Post post = findPostById(postId);
        if (!post.getMember().getUserId().equals(member.getUserId())) {
            throw new IllegalArgumentException("해당 사용자가 아닙니다.");
        }
        return post;
    }

    private Post findPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 postId입니다."));
    }

}
