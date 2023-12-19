package io.joo.plus.post.controller;

import io.joo.plus.post.dto.PostCreateResponseDto;
import io.joo.plus.post.dto.PostRequestDto;
import io.joo.plus.post.dto.PostResponseDto;
import io.joo.plus.post.repostitory.PostRepository;
import io.joo.plus.post.service.PostService;
import io.joo.plus.security.MemberDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;


    @PostMapping("")
    public String createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal MemberDetailsImpl memberDetails, HttpServletResponse response) {
        if(memberDetails==null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "error";
        } else {
            PostCreateResponseDto createdPost = postService.createPost(requestDto, memberDetails.getMember());
            return "redirect:/api/posts/" + createdPost.getPostId();
        }
    }

    @ResponseBody
    @GetMapping("/{postId}")     //no-auth
    public PostResponseDto getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }


    @PatchMapping("/{postId}")
    public String updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal MemberDetailsImpl memberDetails, HttpServletResponse response) {
        if(memberDetails==null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "error";
        } else {
            postService.updatePost(postId, requestDto, memberDetails);
            return "redirect:/api/posts/" + postId;
        }
    }

    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable Long postId, @AuthenticationPrincipal MemberDetailsImpl memberDetails, HttpServletResponse response) {
        if (memberDetails == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "error";
        } else {
            postService.deletePost(postId, memberDetails);
            return "redirect:/api/posts";
        }
    }
}