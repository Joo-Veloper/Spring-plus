package io.joo.plus.post.controller;

import io.joo.plus.post.dto.PostResponseDto;
import io.joo.plus.post.entity.Post;
import io.joo.plus.post.repostitory.PostRepository;
import io.joo.plus.security.MemberDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostListController {
    private final PostRepository postRepository;

    public PostListController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/myposts")
    public List<PostResponseDto> getOwnPostList(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        try {
            List<Post> ownPosts;
            if (memberDetails == null) {
                ownPosts = postRepository.findTop10ByOrderByCreatedAtDesc(); // Example: get the top 10 posts
            } else {
                ownPosts = postRepository.findAllByMemberIdOrderByCreatedAtDesc(memberDetails.getMember().getId());
            }
            return convertToDtoList(ownPosts);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    private List<PostResponseDto> convertToDtoList(List<Post> posts) {
        return posts.stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

}
