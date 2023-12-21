package io.joo.plus.Comment.controller;

import io.joo.plus.Comment.dto.CommentRequestDto;
import io.joo.plus.Comment.dto.CommentResponseDto;
import io.joo.plus.Comment.service.CommentService;
import io.joo.plus.security.MemberDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}")
public class CommentController {
    private final CommentService commentService;

    // 작성
    @PostMapping("/comments")
    private String createComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal MemberDetailsImpl memberDetails, HttpServletResponse response) {
        if (memberDetails == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "error";
        } else {
            commentService.createComment(postId, requestDto, memberDetails);
            return "redirect:/api/posts/" + postId + "/comments";
        }
    }
    //조회
    @ResponseBody
    @GetMapping("/comments")  //no-auth
    private List<CommentResponseDto> getComments(@PathVariable Long postId) {
        return commentService.getComments(postId);
    }
    //수정
    @PostMapping("/comments/{commentId}")
    private String updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal MemberDetailsImpl memberDetails, HttpServletResponse response) {
        if (memberDetails == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "error";
        } else {
            commentService.updateComment(postId, commentId, requestDto, memberDetails);
            return "redirect:/api/posts/" + postId + "/comments";
        }
    }
    //삭제
    @DeleteMapping("/comments/{commentId}")
    private String deleteComment(@PathVariable Long postId, @PathVariable Long commentId, @AuthenticationPrincipal MemberDetailsImpl memberDetails, HttpServletResponse response) {
        if (memberDetails == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "error";
        } else {
            commentService.deleteComment(postId, commentId, memberDetails);
            return "redirect:/api/posts/" + postId + "/comments";
        }
    }
}
