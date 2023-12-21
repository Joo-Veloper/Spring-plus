package io.joo.plus.post.controller;

import io.joo.plus.post.dto.PostResponseDto;
import io.joo.plus.post.entity.Post;
import io.joo.plus.post.repostitory.PostRepository;
import io.joo.plus.security.MemberDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    //게시물 페이징
    @GetMapping("/list/{listpage}")
    public List<PostResponseDto> getPostList(
            @PathVariable int listpage,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(listpage - 1, size, Sort.by("createdAt").descending());

            Page<Post> posts = postRepository.findAll(pageable);

            return convertToDtoList(posts.getContent());
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
